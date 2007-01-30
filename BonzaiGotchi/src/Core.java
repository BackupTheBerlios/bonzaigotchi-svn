// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            added screenTree
// 2007-01-30 new constructed.... by fiips 


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

	

public class Core extends MIDlet implements CommandListener {
	
	private ScreenTree screenTree;
//	private ScreenMenu screenMenu;
//	private ScreenHelp screenHelp;
	
	//Globale Commandos
	private Command cmdBack;
	private Command cmdExit;
	private Command cmdSelect;
	

	// Hauptmenü ist List
	private List mainmenuList;

	private String[] mainElements; 
	private Command cmdMSelect;


	// BaumMenü Commandos
	private Command cmdTReturn;
	private Command cmdTWater;
	private Command cmdTEdit;
	private Command cmdSExit;

	// Ausgewählter Ast Commandos
	private Command cmdSBCut;
	private Command cmdSBExact;
	private Command cmdSBColor;
	private Command cmdSBDung;
	
	// Seal-Menü Commandos
	private Command cmdSSeal;
	private Command cmdSDontSeal;

	private FileIO data;


	
	
	protected void startApp() throws MIDletStateChangeException {
		//Globale Commandos
		cmdBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);
		cmdExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		cmdSelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);

		checkResume(); //Array bauen für Liste

		// Hauptmenü Commandos
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		mainmenuList.setSelectCommand(cmdMSelect);
		mainmenuList.addCommand(cmdExit);
		mainmenuList.setCommandListener(this);
		

		// BaumMenü Commandos
		cmdTReturn = new Command(LangVars.CMD_TREEMENU_RETURN, Command.OK, 1);
		cmdTWater = new Command(LangVars.CMD_TREEMENU_WATER, Command.OK, 1);
		cmdTEdit = new Command(LangVars.CMD_TREEMENU_EDIT, Command.OK, 1);
		cmdSExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		
		

		// Ausgewählter Ast Commandos
		cmdSBCut = new Command(LangVars.CMD_SELBRANCH_CUT, Command.OK, 2);
		cmdSBExact = new Command(LangVars.CMD_SELBRANCH_EXACTCUT, Command.OK, 3);
		cmdSBColor = new Command(LangVars.CMD_SELBRANCH_COLOR, Command.OK, 4);
		cmdSBDung = new Command(LangVars.CMD_SELBRANCH_DUNG, Command.OK, 5);
		
		// Seal-Menü Commandos
		cmdSSeal = new Command(LangVars.CMD_SELECTED_SEAL, Command.OK, 2);
		cmdSDontSeal = new Command(LangVars.CMD_SELECTED_DONTSEAL, Command.OK, 3);
		
		
		
		
		Display.getDisplay(this).setCurrent(mainmenuList);
		GlobalVars.APPSTATUS=1;
	}
	
	private void checkResume() {
		// Hier wird der Array aufgebaut für das Menü
		
		int i=0;
		int check=-1; //wenn resume nicht dabei ist, maximal menueinträgeanzahl minus 1

		
		if (true){  //abfrage RecordStore fehlt
			check=0; //Maximalmenüeinträge wieder hergestellt!
		}
		
		mainElements = new String[GlobalVars.MAINMENU_LIST_MAX+check]; //anlegen mit oder ohne resume
		
		if (check==0) mainElements[i++] = LangVars.CMD_STARTM_RESUME_TREE;
		
		mainElements[i++] = LangVars.CMD_STARTM_NEW_TREE;
		mainElements[i++] = LangVars.CMD_STARTM_TUTORIAL;
		mainElements[i++] = LangVars.CMD_STARTM_CREDITS;		
	}

	protected void pauseApp() {
		

	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		

	}
	
	public void receiveSelect() {
		/* Wird von aufgerufen wenn "Fire" betätigt */
	}

	public void commandAction(Command c, Displayable d) {
		int cori=1; 
		if (mainElements.length==GlobalVars.MAINMENU_LIST_MAX) {
			cori=0;
		};
		
		if (c==cmdMSelect)
		{
			switch (mainmenuList.getSelectedIndex()+cori) {
			
				case 0: 	{	//im Resume;
								System.out.println("0");
								loadTree();
								GlobalVars.APPSTATUS = 2;
								Display.getDisplay(this).setCurrent(screenTree);
								System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
								addTreeMenuCommand();
								screenTree.interval();
								break;
							}	
				case 1: 	{	//New Tree
								System.out.println("1");
								screenTree = new ScreenTree(this);
								Display.getDisplay(this).setCurrent(screenTree);
								addTreeMenuCommand();
								GlobalVars.APPSTATUS = 2;
								screenTree.interval();
								break;
							}				
				case 2: 	{	// Im Tutorialauswahl
								System.out.println("2");
								break;
							}				
				case 3: 	{	// im Creditsauswahl
								System.out.println("3");
								saveTree();
								break;
							}				
				default: 	{	//im default;
								System.out.println("default");
								
								break;
				}
			
			
			}
		}else if (c.equals(cmdTWater)) {
			screenTree.stopThread();
			GlobalVars.APPSTATUS = 4;
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			screenTree.watering();
		} 
		/*else if (c.equals(cmdBreak)) {
				if (GlobalVars.APPSTATUS == 2) {
					screenTree.stopThread();
					screenTree.removeCommand(cmdBreak);
					screenTree.addCommand(cmdResume);
					screenTree.addCommand(cmdSave);
					screenTree.addCommand(cmdLoad);
					screenTree.addCommand(cmdEdit);
					GlobalVars.APPSTATUS = 1;
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
				}
				else if (GlobalVars.APPSTATUS == 3) {
					screenTree.stopThread();
					screenTree.removeCommand(cmdSelect);
					screenTree.removeCommand(cmdBreak);
					screenTree.addCommand(cmdResume);
					screenTree.addCommand(cmdSave);
					screenTree.addCommand(cmdLoad);
					screenTree.addCommand(cmdEdit);
					screenTree.addCommand(cmdExit);
					GlobalVars.APPSTATUS = 1;
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
					screenTree.repaint();
				}
			}
			else if (c.equals(cmdResume)) {
				screenTree.removeCommand(cmdResume);
				screenTree.removeCommand(cmdSave);
				screenTree.removeCommand(cmdLoad);
				screenTree.removeCommand(cmdEdit);
				screenTree.addCommand(cmdBreak);
				GlobalVars.APPSTATUS = 2;
				System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
				screenTree.interval();
			}
			else if (c.equals(cmdEdit)) {
				screenTree.removeCommand(cmdResume);
				screenTree.removeCommand(cmdSave);
				screenTree.removeCommand(cmdLoad);
				screenTree.removeCommand(cmdEdit);
				screenTree.removeCommand(cmdExit);
				screenTree.addCommand(cmdSelect);
				screenTree.addCommand(cmdBreak);
				GlobalVars.APPSTATUS = 3;
				System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
				screenTree.edit();
			}
			else if (c.equals(cmdWatering)) {
				screenTree.removeCommand(cmdResume);
				screenTree.removeCommand(cmdSave);
				screenTree.removeCommand(cmdLoad);
				screenTree.removeCommand(cmdEdit);
				screenTree.removeCommand(cmdExit);
				screenTree.addCommand(cmdBreak);
				GlobalVars.APPSTATUS = 4;
				System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
				screenTree.watering();
			} 
			
	
		
		
		if (c.equals(cmdSave)) {

		}
		else if (c.equals(cmdLoad)) {

			
			*/
		else if (c.equals(cmdSExit)) {
			screenTree.stopThread();
			screenTree.kill();
			this.notifyDestroyed();
	
		}
		else if (c.equals(cmdExit)) {
			this.notifyDestroyed();
	
		}
	}

	private void addTreeMenuCommand() {
		screenTree.addCommand(cmdTReturn);
		screenTree.addCommand(cmdTWater);
		screenTree.addCommand(cmdTEdit);
		screenTree.addCommand(cmdSExit);
		screenTree.setCommandListener(this);

	}

	protected void loadTree() {
		data.readDataInit();
		screenTree.kill();
		GlobalVars.COUNTERELEMENT = 0;
		
		short tmpVer = data.readDataInit();
		if (tmpVer > 0) {
			System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer + " ---");
			screenTree = new ScreenTree(this, data);
			screenTree.addCommand(cmdTReturn);
			screenTree.addCommand(cmdTWater);
			screenTree.addCommand(cmdTEdit);

			screenTree.setCommandListener(this);
			Display.getDisplay(this).setCurrent(screenTree);
			data.readDataFinalize();
		}
	
	
		
	}

	protected void saveTree() {
		data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);	
		screenTree.writeData(data);
		data.writeDataFinalize();
		// TODO Auto-generated method stub
		
	}
	

}
