import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;


public class ScreenHelpButtons extends List implements CommandListener{

	private String[] mainElements;

	private Command cmdItems;

	private Command cmdBack;

	private Image imageArray[];
	private ReceiveFeedback parent;

	
	public ScreenHelpButtons(ReceiveFeedback parent) {
		super("Buttons List",List.IMPLICIT);

		this.parent = parent;

		init();

	}

	private void init() {
		setCommandListener(this);
		cmdItems = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		cmdBack = new Command(LangVars.CMD_ALL_BACK, Command.EXIT, 1);
		this.addCommand(cmdItems);
		this.addCommand(cmdBack);
		buildImages();


		
	}

	private void buildImages() {
		// Hier wird der Array aufgebaut für die Items

	imageArray = new Image[11];
		try {
			imageArray[0]=Image.createImage(GlobalVars.MENU_IMG_PATH_WATER);
			imageArray[1]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT);
			imageArray[2]=Image.createImage(GlobalVars.MENU_IMG_PATH_POT);
			imageArray[3]=Image.createImage(GlobalVars.MENU_IMG_PATH_EXIT);		
			imageArray[4]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT_CUT);
			imageArray[5]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT);
			imageArray[6]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_SEAL);
			imageArray[7]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_DONTSEAL);
			imageArray[8]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT_COLOR);
			imageArray[9]=Image.createImage(GlobalVars.MENU_IMG_PATH_EDIT_DUNG);
			imageArray[10]=Image.createImage(GlobalVars.MENU_IMG_PATH_BACKBUTTON);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		mainElements = new String[]{
			LangVars.CMD_TREEMENU_WATER,
			LangVars.CMD_TREEMENU_EDIT,
			LangVars.CMD_TREEMENU_POT,
			LangVars.CMD_ALL_EXIT,
			LangVars.CMD_SELBRANCH_CUT,
			LangVars.CMD_SELBRANCH_EXACTCUT,
			LangVars.CMD_SELECTED_SEAL,
			LangVars.CMD_SELECTED_DONTSEAL,
			LangVars.CMD_SELBRANCH_COLOR,
			LangVars.CMD_SELBRANCH_DUNG,
			LangVars.CMD_BACKBUTTON
		};


		for (int i=0;i<mainElements.length;i++) {
			append(mainElements[i], imageArray[i]);
			
		}
	}

	public void commandAction(Command c, Displayable arg1) {
		if (c==cmdBack)

		{
			parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
		}
		else { //Any other Button pressed (incl. SELECT)

			GlobalVars.TU_ACTUAL=(short)this.getSelectedIndex();
			parent.receiveFeedback(GlobalVars.APPSTATUS_HELP_WORK);

		}
	}
}
