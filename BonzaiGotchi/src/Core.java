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
	

	// Hauptmen� ist List
	private List mainmenuList;

	private String[] mainElements; 
	private Command cmdMSelect;


	// BaumMen� Commandos
	private Command cmdTReturn;
	private Command cmdTWater;
	private Command cmdTEdit;
	private Command cmdSExit;

	// Ausgew�hlter Ast Commandos
	private Command cmdSBCut;
	private Command cmdSBExact;
	private Command cmdSBColor;
	private Command cmdSBDung;
	
	// Seal-Men� Commandos
	private Command cmdSSeal;
	private Command cmdSDontSeal;

	private FileIO data;


	
	
	protected void startApp() throws MIDletStateChangeException {
		//Globale Commandos
		cmdBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);
		cmdExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		cmdSelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);

		checkResume(); //Array bauen f�r Liste

		// Hauptmen� Commandos
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		showMainMenu();
		

		// BaumMen� Commandos
		cmdTReturn = new Command(LangVars.CMD_TREEMENU_RETURN, Command.OK, 1);
		cmdTWater = new Command(LangVars.CMD_TREEMENU_WATER, Command.OK, 1);
		cmdTEdit = new Command(LangVars.CMD_TREEMENU_EDIT, Command.OK, 1);
		cmdSExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		
		

		// Ausgew�hlter Ast Commandos
		cmdSBCut = new Command(LangVars.CMD_SELBRANCH_CUT, Command.OK, 2);
		cmdSBExact = new Command(LangVars.CMD_SELBRANCH_EXACTCUT, Command.OK, 3);
		cmdSBColor = new Command(LangVars.CMD_SELBRANCH_COLOR, Command.OK, 4);
		cmdSBDung = new Command(LangVars.CMD_SELBRANCH_DUNG, Command.OK, 5);
		
		// Seal-Men� Commandos
		cmdSSeal = new Command(LangVars.CMD_SELECTED_SEAL, Command.OK, 2);
		cmdSDontSeal = new Command(LangVars.CMD_SELECTED_DONTSEAL, Command.OK, 3);
		
		
		
		
		Display.getDisplay(this).setCurrent(mainmenuList);
		GlobalVars.APPSTATUS=1;
	}
	


	private void checkResume() {
		// Hier wird der Array aufgebaut f�r das Men�
		
		int i=0;
		int check=-1; //wenn resume nicht dabei ist, maximal menueintr�geanzahl minus 1

		
		if (true){  //abfrage RecordStore fehlt
			check=0; //Maximalmen�eintr�ge wieder hergestellt!
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
		/* Wird von aufgerufen wenn "Fire" bet�tigt */
	}

	public void commandAction(Command c, Displayable d) {
		int cori=1;   //abfrage ob resume oder nicht....
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
								showTreeMenuCommand();
								screenTree.interval();
								break;
							}	
				case 1: 	{	//New Tree
								System.out.println("1");
								screenTree = new ScreenTree(this);
								Display.getDisplay(this).setCurrent(screenTree);
								showTreeMenuCommand();
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
								System.out.println("Wie hast du das geschafft? Im Default....");
								break;
				}
			
			
			}
		}else if (c.equals(cmdTWater)) {
			screenTree.stopThread();
			GlobalVars.APPSTATUS = 4;
			showSelectBreakCommand();
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			screenTree.watering();
		} 
		
		else if (c.equals(cmdTEdit)) {
			// code wenn auf edit im TreeMen� gedr�ckt wurde
			screenTree.stopThread();
			showSelectBreakCommand();
			GlobalVars.APPSTATUS = 3;
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			screenTree.edit();
		}

		else if (c.equals(cmdTReturn)) {
			// TODO code wenn auf Return im TreeMen� gedr�ckt wurde
			screenTree.stopThread();
			screenTree.interval();
			
		}
		else if (c.equals(cmdTEdit)) {
			// TODO code wenn auf Return im TreeMen� gedr�ckt wurde
			
			
		}
		else if (c.equals(cmdTEdit)) {
			// code wenn aufReturn im TreeMen� gedr�ckt wurde
			
			
		}

		else if (c.equals(cmdSelect)) {
				if (GlobalVars.APPSTATUS == 2) {
					screenTree.stopThread();
					showTreeMenuCommand();
					GlobalVars.APPSTATUS = 1;
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
				}
				else if (GlobalVars.APPSTATUS == 3) {
					screenTree.stopThread();
					showTreeMenuCommand();
					GlobalVars.APPSTATUS = 1;
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
					screenTree.repaint();
				}
				else if (GlobalVars.APPSTATUS == 4) {
					screenTree.stopThread();
					showTreeMenuCommand();
					GlobalVars.APPSTATUS = 1;
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
					screenTree.interval();
					//screenTree.repaint();
				}
			}
			/*else if (c.equals(cmdResume)) {
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
			showMainMenu();
			Display.getDisplay(this).setCurrent(mainmenuList);
			//this.notifyDestroyed();
	
		}
		else if (c.equals(cmdExit)) {
			this.notifyDestroyed();
	
		}
	}

	private void clearCommands() {
		screenTree.removeCommand(cmdBack);
		screenTree.removeCommand(cmdExit);
		screenTree.removeCommand(cmdMSelect);
		screenTree.removeCommand(cmdSBColor);
		screenTree.removeCommand(cmdSBCut);
		screenTree.removeCommand(cmdSBDung);
		screenTree.removeCommand(cmdSBExact);
		screenTree.removeCommand(cmdSDontSeal);
		screenTree.removeCommand(cmdSelect);
		screenTree.removeCommand(cmdSExit);
		screenTree.removeCommand(cmdSSeal);
		screenTree.removeCommand(cmdTEdit);
		screenTree.removeCommand(cmdTReturn);
		screenTree.removeCommand(cmdTWater);		
		
	}
	
	
	private void showTreeMenuCommand() {
		clearCommands();
		screenTree.addCommand(cmdTReturn);
		screenTree.addCommand(cmdTWater);
		screenTree.addCommand(cmdTEdit);
		screenTree.addCommand(cmdSExit);
		screenTree.addCommand(cmdSExit);
		screenTree.setCommandListener(this);
	}
	
	private void showMainMenu() {
		// TODO Auto-generated method stub
		//clearCommands();
		mainmenuList.setSelectCommand(cmdMSelect);
		mainmenuList.addCommand(cmdExit);
		mainmenuList.setCommandListener(this);
		
	}
	

	private void showSelectBreakCommand() {
		// Abfrage �ber APPSTATUS!!
		clearCommands();
		screenTree.addCommand(cmdSelect);
		screenTree.addCommand(cmdBack);
	}

	protected void loadTree() {
		data.readDataInit();
		screenTree.kill();
		GlobalVars.COUNTERELEMENT = 0;
		
		short tmpVer = data.readDataInit();
		if (tmpVer > 0) {
			System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer + " ---");
			screenTree = new ScreenTree(this, data);

			showTreeMenuCommand();


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
