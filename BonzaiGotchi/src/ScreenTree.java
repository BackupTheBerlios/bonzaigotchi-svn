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

	// Children
	private Element log;
	
	// Pot
	private int potSize;
	private int water;
	private int logWaterRequest;
	
	// Gauge
	private Can can;
	private int canValue;
	private int canSteps;
	
	// 0 = parent, 1 = EditElement, 2 = Gauge
	private int keyReceiver;
	
	// Thread
	private Thread threadInterval;
	private boolean threadRun = false;
	private boolean threadWaiting = false;
	
	private boolean animWatering = false;
	
	private Core parent;
	
	public ScreenTree(Core tmpParent) {
		super();
		parent = tmpParent;
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date();
		GlobalVars.COUNTERELEMENT = 0;
		GlobalVars.COUNTERINTERVAL = 0;
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();

		// Gauge
		potSize=0;
		can = new Can((short)0,(short)(GlobalVars.DISPLAY_X_WIDTH/2),(short)(GlobalVars.DISPLAY_Y_HEIGHT/2),(short)0);
		
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
				
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
		
		if (GlobalVars.APPSTATUS != 4) {
				
			GlobalVars.PAINTSTATUS = 1;
			log.draw(g);
			GlobalVars.PAINTSTATUS = 0;
				
			drawPot(g);

		} else {
			can.draw(g);	
		}
			
		if (GlobalVars.APPSTATUS == 3) {
			GlobalVars.PAINTSTATUS = 2;
			log.draw(g);
			GlobalVars.PAINTSTATUS = 0;
		}
		
		if (threadWaiting && GlobalVars.APPSTATUS == 2) {
			interval();
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
		
//		drawCan(g);
		//g.setColor(50,50,50);
		//g.fillRect(x/2+gauge-50,y/2-10,5,20);
		
	}


	private void drawPot(Graphics g){
		//		drawing a pot
		
		if (water >= GlobalVars.POT_SIZE[potSize]) {
						
			int overWater = (water - GlobalVars.POT_SIZE[potSize]) * 100 / GlobalVars.POT_SIZE[potSize];
			if (overWater > GlobalVars.POT_HEIGHT[potSize]) {
				overWater = GlobalVars.POT_HEIGHT[potSize];
			}
			
			g.setColor(GlobalVars.COLOR_POT_WATER);
			for(int i=0; i <= overWater; i++) {
				g.drawLine(
						GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSize] - i / 2,
						GlobalVars.DISPLAY_Y_HEIGHT - i,
						GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSize] + i / 2,
						GlobalVars.DISPLAY_Y_HEIGHT - i);
			}
		}
		
		g.setColor(100,50,50);
		for (int i=0; i <= GlobalVars.POT_THICKNESS; i++) {
			g.drawLine(
					GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSize] + i,
					GlobalVars.DISPLAY_Y_HEIGHT,
					GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSize] - GlobalVars.POT_HEIGHT[potSize] /2 + i,
					GlobalVars.DISPLAY_Y_HEIGHT - GlobalVars.POT_HEIGHT[potSize]);
			
			g.drawLine(
					GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSize] - i,
					GlobalVars.DISPLAY_Y_HEIGHT,
					GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSize] + GlobalVars.POT_HEIGHT[potSize] /2 - i,
					GlobalVars.DISPLAY_Y_HEIGHT - GlobalVars.POT_HEIGHT[potSize]);			
		}
		
	}


	
	public void watering(){
		keyReceiver = 2;
		canValue = 1;
		canSteps = (potSize + 1) * 3;	
		
		this.repaint();
	}

	public void wateringAction(){
		water += GlobalVars.POT_SIZE[potSize] * canValue * 110 / 100 / canSteps ;
		
			
		System.out.println("SOVIEL WURDE GEGOSSEN:  " + GlobalVars.POT_SIZE[potSize] * canValue * 110 / 100 / canSteps);
		parent.resetTreeMenu();
		this.repaint();
		//animWatering=false;
	}
	public void interval() {
		threadInterval = new Thread(this);
		threadWaiting = false;
		threadInterval.start();
	}

	public void run() {
		int counterDraw = 0;
		int supply;

		// anfangsstart
//		while (((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / 10000) > 0 && GlobalVars.APPSTATUS == 2) {
		for (int i = 0; GlobalVars.COUNTERINTERVAL <= 700 && threadRun; i++) {			
			System.out.println("--- INTERVAL: " + ++GlobalVars.COUNTERINTERVAL + " ---");
			
			supply = Math.min(water, logWaterRequest);
			water -= supply;
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
		
		if (!threadWaiting) {
			parent.resetTreeMenu();
		}
		
		
	}
	
	public void edit() {
		keyReceiver = 1;
		GlobalVars.ELEMENTEDIT = log;
		this.repaint();
	}
	
	protected void keyPressed (int keyCode){
		if (GlobalVars.APPSTATUS == 3 || GlobalVars.APPSTATUS == 4) {
			// System.out.println("--- Key Pressed: "+ keyCode +"---");
			Element tmpElementEdit;
			byte tmpRelative = 0;
			
			switch (keyReceiver) {
				case 1: 
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
					
					if (tmpRelative > 0) {
						if ((tmpElementEdit = GlobalVars.ELEMENTEDIT.getRelative(tmpRelative)) != null) {
							GlobalVars.ELEMENTEDIT = tmpElementEdit;
							this.repaint();
						}
					}
					
					// END CASE 1
					break;
				
				case 2:
						
					System.out.println("keyreceiver==2");
					switch (getGameAction(keyCode)) {
						case LEFT:
						case DOWN:
						
							canValue--;
							if (canValue < 1) {
								canValue = 1;
							}
							
							this.repaint();
							break;
							
						case RIGHT:
						case UP:
							
							canValue++;
							if (canValue > canSteps) {
								canValue = canSteps;
							}
							
							this.repaint();
							break;
						
						case FIRE:
							
							wateringAction();
							break;
					}
					// END CASE 2
					break;
					
			} // END SWITCH
			// System.out.println("--- Key Pressed: "+ tmpRelative +"---");
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
