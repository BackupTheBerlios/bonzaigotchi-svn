import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * The Class contains the Intro for the Bonzaigotchi.
 *  
 */
public class ScreenIntro extends Canvas implements Runnable {
	private Image introbild;
	private Image intro2bild;


	private ReceiveFeedback parent;

	private Thread introrun;

	private int count = 1;

	/**
	 * Constructor for ScreenIntro. Allocates the images and inits introrun thread.
	 * @param parent
	 */
	public ScreenIntro(ReceiveFeedback parent) {

		this.parent = parent;

		introbild = null;
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		try {
			introbild = Image.createImage(GlobalVars.INTRO_IMAGE_FIRST);
			intro2bild = Image.createImage(GlobalVars.INTRO_IMAGE_SECOND);
			// fixe Gr��e von 128x128 px bzw. 128x153
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		introrun = new Thread(this);

	}
	/**
	 * Starts the introrun thread.
	 */
	public void play() {
		introrun.start();
	}
	
	/**
	 * Paints the intro pics depending on count.
	 * @param g
	 */
	protected void paint(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH,
				GlobalVars.DISPLAY_Y_HEIGHT);
		switch (count) {
		case 1: {
			int x = getXCenterImage(128);
			int y = getYCenterImage(153);
			g.drawImage(introbild, x, y, Graphics.TOP | Graphics.LEFT);
			break;
		}
		case 2: {
			int x = getXCenterImage(128);
			int y = getYCenterImage(128);
			g.drawImage(intro2bild, x, y, Graphics.TOP | Graphics.LEFT);
			break;

		}
		}
		//g.setColor(0x000000);
		// g.drawString("Credits", 0, 0, Graphics.TOP|Graphics.LEFT);

		

	}
	/**
	 * Gives back the relative Y position of the image on a given value i.
	 * @param i
	 * @return GlobalVars.DISPLAY_Y_HEIGHT - i
	 */
	private int getYCenterImage(int i) {
		int value = 0;
		value = (GlobalVars.DISPLAY_Y_HEIGHT - i) / 2;
		return value;
	}

	/**
	 * Gives back the relative X position of the image on a given value i.
	 * @param i
	 * @return GlobalVars.DISPLAY_X_WIDTH - i
	 */
	private int getXCenterImage(int i) {
		int value = 0;
		value = (GlobalVars.DISPLAY_X_WIDTH - i) / 2;
		return value;
	}

	/**
	 * The Thread sleeps for GlobalVars.INTRO_TIMEOUT the increments count.
	 * Then it responds to his parent APPSTATUS_MAINMENU.
	 */
	public void run() {
		for (int i = 0; i < 2; i++) {
			repaint();
			try {
				Thread.sleep(GlobalVars.INTRO_TIMEOUT);
				count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
	}
}
