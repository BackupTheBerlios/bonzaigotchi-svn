import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ScreenIntro extends Canvas implements Runnable {
	private Image introbild;
	private Image intro2bild;


	private ReceiveFeedback parent;

	private Thread introrun;

	private int count = 1;

	public ScreenIntro(ReceiveFeedback parent) {

		this.parent = parent;

		introbild = null;
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		try {
			introbild = Image.createImage("/intro_screen.png");
			intro2bild = Image.createImage("/intro2_screen.png");
			// fixe Gr��e von 128x128 px bzw. 128x153
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		introrun = new Thread(this);

	}

	public void play() {
		introrun.start();
	}

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

	private int getYCenterImage(int i) {
		int value = 0;
		value = (GlobalVars.DISPLAY_Y_HEIGHT - i) / 2;
		return value;
	}

	private int getXCenterImage(int i) {
		int value = 0;
		value = (GlobalVars.DISPLAY_X_WIDTH - i) / 2;
		return value;
	}


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
