// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            added screenTree
// 2007-01-30 new constructed.... by fiips 
// 2007-02-28 ausgemistet & fertiggebaut  ..... by fiips


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

	

public class Core extends MIDlet implements CommandListener, ScreenTreeFeedback {
	
	private ScreenTree screenTree;
//TODO:	private ScreenMenu screenMenu;
//TODO:	private ScreenHelp screenHelp;


	// Hauptmen� ist List
	private List mainmenuList;
	private String[] mainElements; 
	private Command cmdMSelect;
	private Command cmdMExit;


	// BaumMen� Commandos
	private Command cmdTResume;
	private Command cmdTWater;	
	private Command cmdTEdit;
	private Command cmdTPot;
	private Command cmdTExit_Menu;
	
	private Command cmdTExit;

	// Water Commandos
	private Command cmdWSelect;
	private Command cmdWBack;

	// Edit Commandos
	private Command cmdECut;
	private Command cmdEExact;
	private Command cmdEColor;
	private Command cmdEDung;
	private Command cmdEBack_Menu;
	
	private Command cmdEBack;
	
	// Pot Commandos
	private Command cmdPSelect;
	private Command cmdPBack;
	
	// Seal-Men� Commandos
	private Command cmdSSeal;
	private Command cmdSDontSeal;


	private FileIO data;


	
	protected void startApp() throws MIDletStateChangeException {
		data = new FileIO("BonzaiGotchi");
		
		//TODO: Thread "Timer" starten, schl�ft bis GLOBALVARSirgendwas; �berpr�ft Appstatus und wenn 1 => resume() sonst schlaf; 
		

		checkResume(); //Array bauen f�r Liste

		// Hauptmen� Commandos
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		cmdMExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		showMainMenu();
		

		// BaumMen� Commandos
		cmdTResume = new Command(LangVars.CMD_TREEMENU_RETURN, Command.OK, 1);
		cmdTWater = new Command(LangVars.CMD_TREEMENU_WATER, Command.OK, 1);
		cmdTEdit = new Command(LangVars.CMD_TREEMENU_EDIT, Command.OK, 1);
		cmdTPot = new Command(LangVars.CMD_TREEMENU_POT, Command.OK, 1);
		cmdTExit_Menu = new Command(LangVars.CMD_ALL_EXIT, Command.OK, 2);
		cmdTExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);		//au�erhalb Men�
		

		// Water Commandos
		cmdWSelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);
		cmdWBack = new Command(LangVars.CMD_ALL_BACK, Command.EXIT, 1);		
		
		// Edit Commandos
		cmdECut = new Command(LangVars.CMD_SELBRANCH_CUT, Command.OK, 2);
		cmdEExact = new Command(LangVars.CMD_SELBRANCH_EXACTCUT, Command.OK, 3);
		cmdEColor = new Command(LangVars.CMD_SELBRANCH_COLOR, Command.OK, 4);
		cmdEDung = new Command(LangVars.CMD_SELBRANCH_DUNG, Command.OK, 5);
		cmdEBack_Menu = new Command(LangVars.CMD_ALL_BACK, Command.OK, 6);
		
		cmdEBack = new Command(LangVars.CMD_ALL_BACK, Command.EXIT, 1);
		
		
		// Pot Commandos
		cmdPSelect = new Command(LangVars.CMD_ALL_SELECT, Command.OK, 1);
		cmdPBack = new Command(LangVars.CMD_ALL_BACK, Command.EXIT, 1);		
		
		// Seal-Men� Commandos
		cmdSSeal = new Command(LangVars.CMD_SELECTED_SEAL, Command.OK, 1);
		cmdSDontSeal = new Command(LangVars.CMD_SELECTED_DONTSEAL, Command.EXIT, 1);
			
	}
	


	private void checkResume() {
		// Hier wird der Array aufgebaut f�r das Men�
		
		int i=0;
		int check=-1; //wenn resume nicht dabei ist, maximal menueintr�geanzahl minus 1

		short tmpVer=data.readDataInit();
		System.out.println("!!!! FEHLER HIER ??????");
		data.readDataFinalize();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {  //abfrage RecordStore
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
		
		// BaumMen�
		else if (c.equals(cmdTResume)) {
				// TODO code wenn auf Return im TreeMen� gedr�ckt wurde
			GlobalVars.APPSTATUS = 1;

				
			 
		}
		else if (c.equals(cmdTWater)) {
			GlobalVars.APPSTATUS = 4;
			showWaterCommand();
			screenTree.watering();		
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
			
		} 
		
		else if (c.equals(cmdTEdit)) {
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
		
		else if (c.equals(cmdWSelect)) {
			screenTree.wateringAction();	
			GlobalVars.APPSTATUS = 1;
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");			
		} 
		
		else if (c.equals(cmdWBack)) {
			GlobalVars.APPSTATUS = 1;
			showTreeMenuCommand();
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");			
		} 
		
		else if (c.equals(cmdECut)) {
			GlobalVars.APPSTATUS = 3;
			screenTree.editKill();
			screenTree.edit(true);
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");			
		} 

		else if (c.equals(cmdEExact)) {
			GlobalVars.APPSTATUS = 3;
			screenTree.editExact();
			showSealMenu();
		
		} 
		

		else if (c.equals(cmdEColor)) {
			// TODO: The Coloring
			System.out.println("Hier kommt das Coloring herein!!");
		
		} 
		
		else if (c.equals(cmdEDung)) {
			// TODO: The Dung
			System.out.println("Hier kommt das D�ngen herein!!");
		
		}
		
		
		//HIER CUTBRANCH USW
		else if (c.equals(cmdEBack_Menu)) {
			//showEditCommand();
			//screenTree.edit(true);
		}
		
		else if (c.equals(cmdEBack)) {
			resetTreeMenu();
			screenTree.repaint();
		} 
		
		
		
		else if (c.equals(cmdPSelect)) {
			screenTree.potChangeAction();	
			GlobalVars.APPSTATUS = 1;
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");			
		} 
		
		else if (c.equals(cmdPBack)) {
			GlobalVars.APPSTATUS = 1;
			showTreeMenuCommand();
			screenTree.setCommandListener(this);
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");			
		} 
		
		else if (c.equals(cmdSSeal)) {
			GlobalVars.APPSTATUS = 3;
			screenTree.editCut(true);
			showEditCommand();
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");			
		} 
		else if (c.equals(cmdSDontSeal)) {
			GlobalVars.APPSTATUS = 3;
			screenTree.editCut(false);
			showEditCommand();
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");	
		
		}

	}

	private void showSealMenu() {
		clearCommands();
		screenTree.addCommand(cmdSSeal);
		screenTree.addCommand(cmdSDontSeal);		
	}

	public void resetTreeMenu() {
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

		screenTree.removeCommand(cmdWSelect);
		screenTree.removeCommand(cmdWBack);

		screenTree.removeCommand(cmdECut);
		screenTree.removeCommand(cmdEExact);
		screenTree.removeCommand(cmdEColor);
		screenTree.removeCommand(cmdEDung);
		screenTree.removeCommand(cmdEBack_Menu);
		screenTree.removeCommand(cmdEBack);
		
		screenTree.removeCommand(cmdPSelect);
		screenTree.removeCommand(cmdPBack);
		
		screenTree.removeCommand(cmdSSeal);
		screenTree.removeCommand(cmdSDontSeal);
		
	}
	
	private void showMainMenu() {
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
		System.out.println("Im show Water command");
		screenTree.addCommand(cmdWSelect);
		screenTree.addCommand(cmdWBack);
	
	}
	
	private void showEditCommand() {
		clearCommands();
		screenTree.addCommand(cmdECut);
		screenTree.addCommand(cmdEExact);
		screenTree.addCommand(cmdEColor);
		screenTree.addCommand(cmdEDung);
		screenTree.addCommand(cmdEBack_Menu);		
		
		screenTree.addCommand(cmdEBack);
		
	}
	
	private void showPotCommand() {
		clearCommands();
		screenTree.addCommand(cmdPSelect);
		screenTree.addCommand(cmdPBack);
		
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



	public void receiveFeedback(byte code) {
		// code: 1x Commands - 11 = treeMenu, 12 = treeDead, 13 = screenInterval; 2x keyEvent - 21 = fireButton
		
		switch (code) {
			case 11:
				resetTreeMenu();
				break;
			case 12:
				break;
			case 13:
				break;
			
			case 21:
				/* Wird von aufgerufen wenn "Fire" bet�tigt 
				 * Ruft das Edit Men� auf....
				 * */
				showEditCommand();
				break;
		}
	}
	

}
