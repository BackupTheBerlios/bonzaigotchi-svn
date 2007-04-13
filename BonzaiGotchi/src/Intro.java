import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class Intro extends Canvas implements Runnable{
	private Image introbild;
	private ReceiveFeedback parent;
	private Thread introrun;
	
	public Intro(ReceiveFeedback parent) {
		
		this.parent = parent;
		
		introbild=null;
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		}
		try {
			introbild = Image.createImage("/intro_screen.png");
			//fixe Gr��e von 128x128 px
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		introrun = new Thread(this);

	}
	public void play(){
		introrun.start();
	}
	protected void paint(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
		int x=getXCenterImage(128);
		int y=getYCenterImage(128);
		g.drawImage(introbild, x, y, Graphics.TOP|Graphics.LEFT);
		g.setColor(0x000000);
		//g.drawString("Credits", 0, 0, Graphics.TOP|Graphics.LEFT);
		
		
		
	}
	
	private int getYCenterImage(int i) {
		int value=0;
		value=(GlobalVars.DISPLAY_Y_HEIGHT-i)/2;
		return value;
	}

	private int getXCenterImage(int i) {
		int value=0;
		value=(GlobalVars.DISPLAY_X_WIDTH-i)/2;
		return value;
	}

	protected void keyPressed (int keyCode) {
		switch (getGameAction(keyCode)) {
			case LEFT:
				repaint();
				break;	
			
			case RIGHT:
				repaint();
				break;
			
			case UP:
				repaint();
				break;

			case DOWN:
				repaint();
				break;
		}
	}

	public void run() {
		//forschleife anzahl der Bilder;
		repaint();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
	}
}
