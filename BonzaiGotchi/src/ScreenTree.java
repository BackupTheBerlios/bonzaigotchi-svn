// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            altered 		constructor
//            altered 		run()
//            added 		writeData()
// 2006-01-30  updated by Sven
//			  altered 		paint()
//			  altered 		run()
//			  altered 	  	edit()
//			  altered 	  	keyPressed()
//			  altered     	screenTree()
//			  added 	  	watering()
//			  added 	  	drawCan()
//			  added 	  	drawWater()
//			  added 	  	drawWatering()
//			  added 	  	drawPot()
//			  added 	  	actionWatering()

import java.util.Date;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;


public class ScreenTree extends Canvas implements Runnable {

	private Element log;
	private Can can;
	
	private int logWaterRequest;
	private int water;
	
	private int gaugevalue;
	private int lastposgauge;
	private int gauge;
	private int gaugeadd;
	private int keyreceiver;
	private int potsize;
	
	private Thread threadInterval;
	private boolean threadRun = false;
	private boolean threadWaiting = false;
	
	private boolean animWatering = false;
	
	private Core parent;
	
	private int y = super.getHeight();
	private int x = super.getWidth();
	
	public ScreenTree(Core tmpParent) {
		super();
		potsize=2;
		gaugeadd=10;
		parent = tmpParent;
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date();
		GlobalVars.COUNTERELEMENT = 0;
		GlobalVars.COUNTERINTERVAL = 0;
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = GlobalVars.POT_WATER_INIT;
		log = new Element(null, (short)0, (short)(GlobalVars.DISPLAY_X_WIDTH / 2), GlobalVars.DISPLAY_Y_HEIGHT, 10000);
		logWaterRequest = log.getChildWaterRequest();
	}
	
	public ScreenTree(Core tmpParent, FileIO data) {
		
		parent = tmpParent;
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date(data.readDataLong());
		GlobalVars.COUNTERELEMENT = 0;
		GlobalVars.COUNTERINTERVAL = data.readDataInt();
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = data.readDataInt();		
		log = new Element(null, data);
		logWaterRequest = log.getChildWaterRequest();
	}
	
	protected void paint(Graphics g) {
		if (!animWatering) {
			g.setColor(0xFFFFFF);
			g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
			GlobalVars.PAINTSTATUS = 1;
			log.draw(g);
			GlobalVars.PAINTSTATUS = 0;
			
			drawWater(g);
			drawPot(g);
			}
		if(keyreceiver==2)
			drawWatering(g);
		
		if (GlobalVars.APPSTATUS == 3) {
			GlobalVars.PAINTSTATUS = 2;
			log.draw(g);
			GlobalVars.PAINTSTATUS = 0;
		}
		if (threadWaiting && threadRun) {
			interval();
		}
	}
	private void drawWater(Graphics g) {
		if(true)//water<=GlobalVars.POT_SIZE[potsize])
			{
			g.setColor(0,0,100);
			for(int i=x/2-19;i<x/2+5;i++)
				g.drawLine( i,  y, i-5,  y-10);
			
			for(int i=x/2+19;i>x/2-5;i--)
				g.drawLine( i,  y, i+5,  y-10);
			
			
			}
	}
	private void drawWatering(Graphics g) {
		/* graphic of pot */
		// while animation=overpainting set flag animWatering = true;
		/* animation for watering */
		
		
		
		//g.setColor(255,255,255);
		//g.fillRect(x/2+lastposgauge-50,y/2-10,5,20);
		
		//g.setColor(100,100,100);
		/*switch(potsize){
			case 0:
				g.fillRect(x/2-30,y/2,60,5);
				break;
			case 1:
				g.fillRect(x/2-30,y/2,60,5);
				break;
			case 2:
				g.fillRect(x/2-30,y/2,60,5);
				break;
		}*/
		
		drawCan(g);
		//g.setColor(50,50,50);
		//g.fillRect(x/2+gauge-50,y/2-10,5,20);
		
	}
	
	public void watering(){
		keyreceiver=2;
		animWatering=true;
		gaugevalue=5;
		gauge=50;
		
	
		
		this.repaint();
		
		System.out.println("watering-end");
	}
	private void drawPot(Graphics g){
		//		drawing a pot
		
		switch(potsize){
		case 0:
			g.setColor(100,50,50);
			g.drawLine( x/2-10,  y, x/2-15,  y-5);
			g.drawLine( x/2+10,  y, x/2+15,  y-5);
			
			gaugeadd=30;
			break;
		case 1:
			g.setColor(100,50,50);
			g.drawLine( x/2-15,  y, x/2-20,  y-7);
			g.drawLine( x/2+15,  y, x/2+20,  y-7);
			gaugeadd=15;
			break;
			
		case 2:
			g.setColor(100,50,50);
			g.drawLine( x/2-20,  y, x/2-25,  y-10);
			g.drawLine( x/2+20,  y, x/2+25,  y-10);
			gaugeadd=10;
			break;
		}
		
	}
	private void drawCan(Graphics g){
		g.setColor(255,255,255);
		g.fillRect(0,0,x,y);
		Can can = new Can((short)0,(short)(x/2),(short)(y/2),(short)gauge);
		can.draw(g,false,false);
	}
	public void actionWatering(){
		//max reinschütten
		if(gauge==100)
			gauge=99;
		water+=GlobalVars.POT_SIZE[potsize]/100*(gauge+1);
				
			
		System.out.println("SOVIEL WURDE GEGOSSEN:  "+GlobalVars.POT_SIZE[potsize]/100*(gauge+1));
		this.repaint();
		//animWatering=false;
	}
	public void interval() {
		threadInterval = new Thread(this);
		threadRun = true;
		threadWaiting = false;
		threadInterval.start();
//		run();
	}

	public void run() {
		int counterDraw = 0;
		// lets give him more to do ;-)
//		while (((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / 600000) > 0 && threadRun) {
		for (int i = 0; i <= 700 && threadRun; i++) {			
			System.out.println("--- INTERVAL: " + ++GlobalVars.COUNTERINTERVAL + " ---");
			
			int supply = Math.min(water, logWaterRequest);
	//		water -= supply;
			log.grow(supply);
			logWaterRequest = log.getChildWaterRequest();
			System.out.println("--- INTERVAL: " + ++GlobalVars.COUNTERINTERVAL + " | " +logWaterRequest+ "---");
			if (++counterDraw == 12) {
					threadWaiting = true;
					break;
			}

		}
		System.out.println("--- INTERVAL END LOOP ---");
		this.repaint();
		
	}
	
	public void stopThread(){
		threadRun = false;
	}
	
	public void edit() {
		keyreceiver=1;
		GlobalVars.ELEMENTEDIT = log;
		this.repaint();
	}
	
	protected void keyPressed (int keyCode){
		if (GlobalVars.APPSTATUS == 3||GlobalVars.APPSTATUS == 4) {
			// System.out.println("--- Key Pressed: "+ keyCode +"---");
			Element tmpElementEdit;
			byte tmpRelative = 0;
			
			if(keyreceiver==1){
				System.out.println("keyreceiver==1");
			switch (getGameAction(keyCode)) {
			
				case LEFT:
					tmpRelative = (byte)1;
					break;
				case UP:
					tmpRelative = (byte)2;
					break;
				case RIGHT:
					tmpRelative = (byte)3;
					break;
				case DOWN:
					tmpRelative = (byte)4;
					break;
				case FIRE:
					parent.receiveSelect();
					break;
			}
			}
			else if(keyreceiver==2){
				
				System.out.println("keyreceiver==2");
				switch (getGameAction(keyCode)) {
				case LEFT:
					
					lastposgauge=gauge;
					if(potsize==0){
						if(gauge>=50){
							gauge-=gaugeadd;
						}
					}else if(potsize==1){
						if(gauge>=35)
							gauge-=gaugeadd;
					}else if(potsize==2){
						if(gauge>=30)
							gauge-=gaugeadd;
					}
					
					this.repaint();
					break;
				case RIGHT:
					lastposgauge=gauge;
					
					lastposgauge=gauge;
					if(potsize==0){
						if(gauge<=50){
							gauge+=gaugeadd;
						}
					}else if(potsize==1){
						if(gauge<=65)
							gauge+=gaugeadd;
					}else if(potsize==2){
						if(gauge<=70)
							gauge+=gaugeadd;
					}
					
					
					this.repaint();
					break;
				}
				
			}
			// System.out.println("--- Key Pressed: "+ tmpRelative +"---");
			if (tmpRelative > 0) {
				if ((tmpElementEdit = GlobalVars.ELEMENTEDIT.getRelative(tmpRelative)) != null) {
					GlobalVars.ELEMENTEDIT = tmpElementEdit;
					this.repaint();
				}
			}
		}
	}
	
	public void kill() {
		log.childKill();
		log = null;
	}
	
	public void writeData(FileIO data) {
		data.writeData(GlobalVars.TIME_STAMP.getTime());
		data.writeData(GlobalVars.COUNTERINTERVAL);
		data.writeData(water);
		log.writeData(data);
	}
	
}
