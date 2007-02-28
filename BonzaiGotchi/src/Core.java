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
	//private Command cmdExit;
	//private Command cmdSelect;
	

	// Hauptmenü ist List
	private List mainmenuList;
	private String[] mainElements; 
	private Command cmdMSelect;
	private Command cmdMExit;


	// BaumMenü Commandos
	private Command cmdTResume;
	private Command cmdTWater;	
	private Command cmdTEdit;
	private Command cmdTPot;
	private Command cmdTExit_Menu;
	
	private Command cmdTExit;



	// Edit Commandos
	private Command cmdESelect;
	private Command cmdEBack;
	
	// Water Commandos
	private Command cmdWSelect;
	private Command cmdWBack;
	
	// Ausgewählter Ast Commandos
	private Command cmdSBCut;
	private Command cmdSBExact;
	private Command cmdSBColor;
	private Command cmdSBDung;
	private Command cmdSBBack;

	
	// Seal-Menü Commandos
	private Command cmdSSeal;
	private Command cmdSDontSeal;
	private Command cmdSBack;

	private FileIO data;

	private Command cmdSExit;

	
	
	protected void startApp() throws MIDletStateChangeException {
		data = new FileIO("BonzaiGotchi");
		
		//TODO: Thread "Timer" starten, schläft bis GLOBALVARSirgendwas; überprüft Appstatus und wenn 1 => resume() sonst schlaf; 
		
		//Globale Commandos
		//cmdExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		//cmdSelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);

		checkResume(); //Array bauen für Liste

		// Hauptmenü Commandos
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		cmdMExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		showMainMenu();
		

		// BaumMenü Commandos
		cmdTResume = new Command(LangVars.CMD_TREEMENU_RETURN, Command.OK, 1);
		cmdTWater = new Command(LangVars.CMD_TREEMENU_WATER, Command.OK, 1);
		cmdTEdit = new Command(LangVars.CMD_TREEMENU_EDIT, Command.OK, 1);
		cmdTPot = new Command(LangVars.CMD_TREEMENU_POT, Command.OK, 1);
		cmdTExit_Menu = new Command(LangVars.CMD_ALL_EXIT, Command.OK, 2);
		cmdTExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);		//außerhalb Menü
		

		// Ausgewählter Ast Commandos
		cmdSBCut = new Command(LangVars.CMD_SELBRANCH_CUT, Command.OK, 2);
		cmdSBExact = new Command(LangVars.CMD_SELBRANCH_EXACTCUT, Command.OK, 3);
		cmdSBColor = new Command(LangVars.CMD_SELBRANCH_COLOR, Command.OK, 4);
		cmdSBDung = new Command(LangVars.CMD_SELBRANCH_DUNG, Command.OK, 5);
		cmdSBBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);
		
		// Water Commandos
		cmdWSelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);
		cmdWBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);		
		
		// Edit Commandos
		cmdESelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);
		cmdEBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);
		
		// Seal-Menü Commandos
		cmdSSeal = new Command(LangVars.CMD_SELECTED_SEAL, Command.OK, 2);
		cmdSDontSeal = new Command(LangVars.CMD_SELECTED_DONTSEAL, Command.OK, 3);
		
		cmdSBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);
		

		
	}
	


	private void checkResume() {
		// Hier wird der Array aufgebaut für das Menü
		
		int i=0;
		int check=-1; //wenn resume nicht dabei ist, maximal menueinträgeanzahl minus 1

		short tmpVer=data.readDataInit();
		System.out.println("!!!! FEHLER HIER ??????");
		data.readDataFinalize();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {  //abfrage RecordStore
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
		/* Wird von aufgerufen wenn "Fire" betätigt 
		 * Ruft das Edit Menü auf....
		 * */
		showEditCommand();
	}

	public void commandAction(Command c, Displayable d) {
		int cori=1;   //abfrage ob resume oder nicht....
		if (mainElements.length==GlobalVars.MAINMENU_LIST_MAX) {
			cori=0;
		};
		
		if (c==cmdMSelect) //	 APPSTATUS:    0 = init, 1 = standBy, 2 = running, 3 = edit, 4 = watering
		{
			switch (mainmenuList.getSelectedIndex()+cori) {
			
				case 0: 	{	//im Resume;
								System.out.println("0 = Im Resume");
								loadTree();
								GlobalVars.APPSTATUS = 2;
								Display.getDisplay(this).setCurrent(screenTree);
								System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
								//showTreeMenuCommand();
								screenTree.interval();
								break;
							}	
				case 1: 	{	//New Tree
								System.out.println("1 = im New Tree");
								screenTree = new ScreenTree(this);
								showTreeMenuCommand(); //TODO kommt weg... weil von Screentree aufgerufen!
								Display.getDisplay(this).setCurrent(screenTree);
								GlobalVars.APPSTATUS = 2;
								screenTree.interval();
								break;
							}				
				case 2: 	{	// Im Tutorialauswahl
								System.out.println("2 = im Tutorialauswahl");
								break;
							}				
				case 3: 	{	// im Creditsauswahl
								System.out.println("3 = im creditauswahl");
								saveTree();
								break;
							}				
				default: 	{	//im default;
								System.out.println("Wie hast du das geschafft? Im Default....");
								break;
				}
			
			
			}
		}
		else if (c.equals(cmdMExit)) {
			System.out.println("SYSTEM EXIT! Program shut down!");
			this.notifyDestroyed();
		} 
		
		// BaumMenü
		else if (c.equals(cmdTResume)) {
				// TODO code wenn auf Return im TreeMenü gedrückt wurde
//				screenTree.stopThread();
			GlobalVars.APPSTATUS = 1;
				//screenTree.interval();
				
			 
		}
		else if (c.equals(cmdTWater)) {
			GlobalVars.APPSTATUS = 4;
			showWaterCommand();
			screenTree.watering();		
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			
		} 
		
		else if (c.equals(cmdTEdit)) {
			// code wenn auf edit im TreeMenü gedrückt wurde
			GlobalVars.APPSTATUS = 3;
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			showEditCommand();
			screenTree.edit(false);

		}


		else if (c.equals(cmdTPot)) {
			GlobalVars.APPSTATUS = 5;
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			showPotCommand();
			screenTree.potChange();

			
		}
		
		else if (c.equals(cmdTExit_Menu)) {
			doExitToMain();
		}
		
		else if (c.equals(cmdTExit)) {
			doExitToMain();
		}
		
		else if (c.equals(cmdSBBack)) {
			// code wenn auf Back gedrückt wurde
			if (GlobalVars.APPSTATUS==3) { //Back im Editmodus
				
				
			}
			
			
		}

		/*else if (c.equals(cmdMSelect)) {
			//	 APPSTATUS:    0 = init, 1 = standBy, 2 = running, 3 = edit, 4 = watering
				if (GlobalVars.APPSTATUS == 2) { //TODO Löschen wenn fertig, da der Appstatus niemals 2 sein kann (berechnet gerade)
//					screenTree.stopThread();
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
				}
				else if (GlobalVars.APPSTATUS == 3) {
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
					showSelectedBranchMenuCommand();
				}
				else if (GlobalVars.APPSTATUS == 4) {
					GlobalVars.APPSTATUS = 1;
					//screenTree.wateringAction();
					screenTree.watering();
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
					//screenTree.interval();
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
			doExitToMain();
	
		}

	}







	public void resetTreeMenu() {
		//System.out.println("Appstatus: "+GlobalVars.APPSTATUS);
		saveTree();
		GlobalVars.APPSTATUS = 1;
		showTreeMenuCommand();
	}
	
	private void doExitToMain() {
		saveTree();
		screenTree.kill();
		screenTree = null;
		showMainMenu();		
	}

	private void clearCommands() {
		screenTree.removeCommand(cmdMSelect);
		screenTree.removeCommand(cmdMExit);
		
		screenTree.removeCommand(cmdTEdit);
		screenTree.removeCommand(cmdTResume);
		screenTree.removeCommand(cmdTWater);
		screenTree.removeCommand(cmdTPot);
		screenTree.removeCommand(cmdTExit_Menu);
		screenTree.removeCommand(cmdTExit);

		
		screenTree.removeCommand(cmdSBBack);
		screenTree.removeCommand(cmdSBack);
		screenTree.removeCommand(cmdSBColor);
		screenTree.removeCommand(cmdSBCut);
		screenTree.removeCommand(cmdSBDung);
		screenTree.removeCommand(cmdSBExact);
		screenTree.removeCommand(cmdSDontSeal);
		//screenTree.removeCommand(cmdSelect);
		screenTree.removeCommand(cmdSExit);
		screenTree.removeCommand(cmdSSeal);
	
		
	}
	
	private void showMainMenu() {

		//clearCommands();
		//screenTree.stopThread();
		
		GlobalVars.APPSTATUS = 1;
		mainmenuList.setSelectCommand(cmdMSelect);
		mainmenuList.addCommand(cmdMExit);
		mainmenuList.setCommandListener(this);
		Display.getDisplay(this).setCurrent(mainmenuList);
		System.out.println("--- MAIN MENUE CREATED ---");
		
	}
	
	private void showTreeMenuCommand() {
		clearCommands();
		screenTree.addCommand(cmdTResume);
		screenTree.addCommand(cmdTWater);
		screenTree.addCommand(cmdTEdit);
		screenTree.addCommand(cmdTPot);
		screenTree.addCommand(cmdTExit_Menu);
		screenTree.addCommand(cmdTExit);


		screenTree.setCommandListener(this);
	}
	
	
	private void showWaterCommand() {
		clearCommands();
		screenTree.addCommand(cmdWSelect);
		screenTree.addCommand(cmdWBack);
		screenTree.setCommandListener(this);
		
		
	}
	
	private void showEditCommand() {
		// TODO Auto-generated method stub
		
	}
	
	private void showPotCommand() {
		//clearCommands();
		
	}

	private void showSelectedBranchMenuCommand() {
		clearCommands();
		screenTree.addCommand(cmdSBCut);
		screenTree.addCommand(cmdSBExact);
		screenTree.addCommand(cmdSBColor);
		screenTree.addCommand(cmdSBDung);
		screenTree.addCommand(cmdSBBack);
		
		//screenTree.addCommand(cmdSExit); nicht erlaubt beim editieren eines astes
		screenTree.setCommandListener(this);
	}
	
	
	private void showSelectEditCommand() {
	
		clearCommands();
		screenTree.addCommand(cmdESelect);
		screenTree.addCommand(cmdEBack);
	}
	private void showSelectBranchCommand() {
	
		clearCommands();
		//screenTree.addCommand(cmdSelect);
		screenTree.addCommand(cmdSBBack);
	}

	protected void loadTree() {
		
		if (screenTree != null) {
			screenTree.kill();
		}
		System.out.println("Im load tree");
		GlobalVars.COUNTERELEMENT = 0;
		
		short tmpVer = data.readDataInit();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {
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
		
	}
	

}
