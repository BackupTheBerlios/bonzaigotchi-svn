import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

public class ScreenHelp extends Form{

	private TextField tf_first;

	
	private ReceiveFeedback parent;


	public ScreenHelp(ReceiveFeedback parent) {
		super("Help / Tutorial");
		this.parent = parent;
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}

	private void init() {
		tf_first = new TextField(LangVars.TU_HELPTEXT_TITLE,LangVars.TU_HELPTEXT,LangVars.TU_HELPTEXT.length(),TextField.ANY);
		append(tf_first);
		append("");

	}


}
