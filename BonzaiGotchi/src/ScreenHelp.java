import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 * The class ScreenHelp contains the Help and Tutorial menu.
 * It displays them with a StringItem.
 */
public class ScreenHelp extends Form{

	private StringItem tf_first;


	/**
	 * Constructs the Help menu.
	 * 
	 */
	public ScreenHelp() {
		super("Help / Tutorial");
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}
	/**
	 * Initialise the title and text of the Tutorial.
	 */
	private void init() {
		tf_first = new StringItem(LangVars.TU_HELPTEXT_TITLE,LangVars.TU_HELPTEXT);
		append(tf_first);
		append("");

	}


}
