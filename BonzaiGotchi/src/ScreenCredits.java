import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;


public class ScreenCredits extends Canvas {

	public ScreenCredits() {
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		}
	}
	
	protected void paint(Graphics g) {
		g.setColor(0x00FF00);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
		g.setColor(0x000000);
		g.drawString("Credits", 0, 0, Graphics.TOP|Graphics.LEFT);
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
}
