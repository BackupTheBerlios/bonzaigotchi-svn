import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 * The class ScreenCredits contains Credits for the Credit screen.
 * It displays them with a StringItem.
 */
public class ScreenCredits extends Form {

	private StringItem tf_first;
	
	/**
	 * Constructs the Credit menu.
	 * 
	 */
	public ScreenCredits() {
		super("Project BonzaiGotchi");
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}
	/**
	 * Initialise the title and text of the Credits.
	 */
	private void init() {
		tf_first = new StringItem(LangVars.CR_TITLE,LangVars.CR_TEXT);
		append(tf_first);
		append("");

	}


}