import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class ScreenCredits extends Form {

	private StringItem tf_first;

	public ScreenCredits() {
		super("Credits");
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}

	private void init() {
		tf_first = new StringItem(LangVars.CR_TITLE,LangVars.CR_TEXT);
		append(tf_first);
		append("");

	}


}