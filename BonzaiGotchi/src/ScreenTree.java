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
	
	public ScreenTree() {
		super();
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date();
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = Integer.MAX_VALUE;
		log = new Element((short)0, (short)(GlobalVars.DISPLAY_X_WIDTH / 2), GlobalVars.DISPLAY_Y_HEIGHT, 10000);
		logWaterRequest = log.getChildWaterRequest();
	
		this.repaint();		
	}
	
	public ScreenTree(FileIO data) {
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date(data.readDataLong());
		GlobalVars.COUNTERINTERVAL = data.readDataInt();
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = data.readDataInt();		
		log = new Element(data);
		logWaterRequest = log.getChildWaterRequest();
		
		this.repaint();
	}
	
	protected void paint(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
		log.draw(g);
		if (threadWaiting) {
			interval();
		}
	}

	protected void keyPressed (int keyCode){
		
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
//		while (((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / 600000) > 0) {
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
		this.repaint();
		
	}
	
	public void stopThread(){
		threadRun = false;
	}
	
	public void writeData(FileIO data) {
		data.writeData(GlobalVars.TIME_STAMP.getTime());
		data.writeData(GlobalVars.COUNTERINTERVAL);
		data.writeData(water);
		log.writeData(data);
	}
}
