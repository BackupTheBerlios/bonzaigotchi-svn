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
	
	public ScreenTree() {
		super();
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date();
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = Integer.MAX_VALUE;
		log = new Element((short)0, (short)(GlobalVars.DISPLAY_X_WIDTH / 2), GlobalVars.DISPLAY_Y_HEIGHT, 10000);
		logWaterRequest = log.getChildWaterRequest();
		System.out.println("--- LogWaterRequest: " + logWaterRequest + "---");
		
		this.repaint();
		
	}
	
	public ScreenTree(FileIO data) {
		
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date(data.readDataLong());
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = data.readDataInt();
		
		log = new Element(data);
		logWaterRequest = log.getChildWaterRequest();
	}
	
	protected void paint(Graphics g) {
		log.draw(g);
	}

	protected void keyPressed (int keyCode){
		
	}
	
	public void interval() {
		threadInterval = new Thread(this);
		threadInterval.run();
	}

	public void run() {
		int n = 0;
		int counter = 0;
		// lets give him more to do ;-)
//		while (((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / 600000) > 0) {
		for (int i = 0; i <= 5000; i++) {
			
			System.out.println("--- INTERVAL: " + ++n + " ---");
			
			int supply = Math.min(water, logWaterRequest);
	//		water -= supply;
			log.grow(supply);
			logWaterRequest = log.getChildWaterRequest();
			
			if (++counter == 6) {
				this.repaint();
				try {
					Thread.sleep(70);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				counter = 0;
			}

		}
		this.repaint();
	}
	
	public void writeData(FileIO data) {
		data.writeData(GlobalVars.TIME_STAMP.getTime());
		data.writeData(water);
		log.writeData(data);
	}
}
