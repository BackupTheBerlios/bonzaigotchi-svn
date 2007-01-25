// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            altered constructor
//            altered run()
//            added writeData()

import java.util.Date;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;


public class ScreenTree extends Canvas implements Runnable {

	private Element log;
	private int logWaterRequest;
	private int water;
	
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
			log.draw(g, false, false);
		}
		draw(g);
		if (GlobalVars.APPSTATUS == 3) {
			log.draw(g, true, false);
		}
		if (threadWaiting && threadRun) {
			interval();
		}
	}
	
	private void draw(Graphics g) {
		/* graphic of pot */
		// while animation=overpainting set flag animWatering = true;
		/* animation for watering */
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
		GlobalVars.ELEMENTEDIT = log;
		this.repaint();
	}
	
	protected void keyPressed (int keyCode){
		if (GlobalVars.APPSTATUS == 3) {
			// System.out.println("--- Key Pressed: "+ keyCode +"---");
			Element tmpElementEdit;
			byte tmpRelative = 0;
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
