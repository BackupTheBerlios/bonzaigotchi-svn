import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

public class ScreenHelpButtonsShow extends Form{

	private TextField tf_first;
	private int index;

	public ScreenHelpButtonsShow() {
		super("Help / Tutorial");	
		index = GlobalVars.TU_ACTUAL;
		tf_first = new TextField(LangVars.TU_BUTTON_NAMES[index],LangVars.TU_BUTTONS[index],LangVars.TU_BUTTONS[index].length(),TextField.ANY);
		append(tf_first);
	}

}
