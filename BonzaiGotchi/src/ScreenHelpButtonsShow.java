import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

public class ScreenHelpButtonsShow extends Form{

	private TextField tf_first;
	private int index;
	private ReceiveFeedback parent;

	public ScreenHelpButtonsShow(int i) {
		super("Help / Tutorial");
		
		index=i;
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}

	private void init() {
		tf_first = new TextField(LangVars.TU_BUTTON_NAMES[index],LangVars.TU_BUTTONS[index],LangVars.TU_BUTTONS[index].length(),TextField.ANY);
		append(tf_first);

	}


}
