import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Contains Id, title and image of a menu Item.
 * 
 * @author Chappy
 *
 */
public class MenuItem {

	private int menuId;
	private String title;
	private Image img;
	
	/**
	 * Constructor of MenuItem.
	 * 
	 * @param tmpMenuId unique Id of MenuId
	 * @param tmpTitle Title of the Item
	 * @param imgPath Image Path of the Item
	 */
	public MenuItem(int tmpMenuId, String tmpTitle, String imgPath) {
		menuId = tmpMenuId;
		title = tmpTitle;
		try {
			img = Image.createImage(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the MenuId.
	 * @return menuId
	 */
	public int getMenuId() {
		return menuId;
	}
	/**
	 * Returns title of the MenuItem.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Paints the Image of the MenuItem.
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawImage(img, g.getClipX(), g.getClipY(), Graphics.TOP|Graphics.LEFT);
	}
	
	
}
