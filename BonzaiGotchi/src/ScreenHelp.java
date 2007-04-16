import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class ScreenHelp extends Form{

	private StringItem tf_first;



	public ScreenHelp() {
		super("Help / Tutorial");
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}

	private void init() {
		tf_first = new StringItem(LangVars.TU_HELPTEXT_TITLE,LangVars.TU_HELPTEXT);
		append(tf_first);
		append("");

	}


}
