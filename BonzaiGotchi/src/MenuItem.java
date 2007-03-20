import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class MenuItem {

	private int menuId;
	private String title;
	private Image img;
	
	public MenuItem(int tmpMenuId, String tmpTitle, String imgPath) {
		menuId = tmpMenuId;
		title = tmpTitle;
		try {
			img = Image.createImage(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getMenuId() {
		return menuId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, g.getClipX(), g.getClipY(), Graphics.TOP|Graphics.LEFT);
	}
	
	
}
