import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

public class ScreenHelpButtons extends List implements CommandListener {
	private List buttonsList;

	private TextField tf_first;

	private String[] mainElements;

	private Command cmdItems;

	private Command cmdBack;
	private ReceiveFeedback parent;
	private Image imageArray[];
	


	public ScreenHelpButtons(ReceiveFeedback parent) {
		super("Test",List.IMPLICIT);
		//super("Help / Tutorial");
		this.parent = parent;
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short) super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short) super.getHeight();
		}
		init();

	}

	private void init() {
		tf_first = new TextField(LangVars.TU_HELPTEXT_TITLE,LangVars.TU_HELPTEXT,LangVars.TU_HELPTEXT.length(),TextField.ANY);
		//append(tf_first);
		//append("");

		cmdBack = new Command(LangVars.CMD_ALL_BACK, Command.EXIT, 1);
		//this.addCommand(cmdBack);
		buildImages();
		//append(buttonsList);
		
	}

	private void buildImages() {
		// Hier wird der Array aufgebaut f�r die Items

	imageArray = new Image[10];
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		mainElements = new String[10];

		mainElements[0]=LangVars.CMD_TREEMENU_WATER;
		mainElements[1]=LangVars.CMD_TREEMENU_EDIT;
		mainElements[2]=LangVars.CMD_TREEMENU_POT;
		mainElements[3]=LangVars.CMD_ALL_EXIT;
		mainElements[4]=LangVars.CMD_SELBRANCH_CUT;
		mainElements[5]=LangVars.CMD_SELBRANCH_EXACTCUT;
		mainElements[6]=LangVars.CMD_SELECTED_SEAL;
		mainElements[7]=LangVars.CMD_SELECTED_DONTSEAL;
		mainElements[8]=LangVars.CMD_SELBRANCH_COLOR;
		mainElements[9]=LangVars.CMD_SELBRANCH_DUNG;


		for (int i=0;i<mainElements.length;i++) {
			super.append(mainElements[i], imageArray[i]);
			
		}
		buttonsList = new List(LangVars.TU_LIST_TITLE, List.IMPLICIT, mainElements, imageArray);
		
		//mainElements = new String[GlobalVars.MAINMENU_LIST_MAX + check]; // anlegen

//		mainElements[i++] = LangVars.CMD_STARTM_NEW_TREE;
//		mainElements[i++] = LangVars.CMD_STARTM_TUTORIAL;
//		mainElements[i++] = LangVars.CMD_STARTM_CREDITS;

	}

	/*
	 * protected void paint(Graphics g) { g.setColor(0xFF0000); g.fillRect(0, 0,
	 * GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
	 * g.setColor(0x000000); g.drawString("Help", 0, 0,
	 * Graphics.TOP|Graphics.LEFT); }
	 * 
	 * protected void keyPressed (int keyCode) { switch (getGameAction(keyCode)) {
	 * case LEFT: repaint(); break;
	 * 
	 * case RIGHT: repaint(); break;
	 * 
	 * case UP: repaint(); break;
	 * 
	 * case DOWN: repaint(); break; } }
	 */

	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub
		if (arg0==cmdBack)

		{
			
			parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
			
		}
	}
}