import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

public class ScreenHelp extends Form implements CommandListener {

	private TextField tf_first;


	private Command cmdItems;
	private Command cmdBack;
	
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

		cmdBack = new Command(LangVars.CMD_ALL_BACK, Command.EXIT, 1);
		this.addCommand(cmdBack);
		this.setCommandListener(this);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub
		System.out.println("Button pressed");
		if (arg0==cmdBack)

		{
			System.out.println("Back pressed");
			parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
			
		}
	}
}
