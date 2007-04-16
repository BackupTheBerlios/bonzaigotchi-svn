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

public class Core extends MIDlet implements CommandListener, ReceiveFeedback, Runnable {

	private ScreenTree screenTree;
	private ScreenHelp screenHelp;
	private ScreenCredits screenCredits;
	private ScreenIntro screenIntro;

	// TODO: private ScreenMenu screenMenu;
	// TODO: private ScreenHelp screenHelp;

	// Hauptmenue ist List
	private List mainmenuList;

	private String[] mainElements;

	private Command cmdMSelect;

	private Command cmdMExit;

	// Dead-Tree Menue Commando
	private Command cmdDTExit;

	// HelpScreen Commandos
	private Command cmdHExit;
	
	// CreditsScreen Commandos
	private Command cmdCExit;
	private FileIO data;

	private Thread alarm;

	protected void startApp() throws MIDletStateChangeException {	


		
		data = new FileIO("BonzaiGotchi");
		screenIntro = new ScreenIntro(this);
		screenHelp = new ScreenHelp(this);
		screenCredits = new ScreenCredits();

		// ScreenMenu screeny = new ScreenMenu();
		// screeny.initialize();

		// Hauptmenue Commandos
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		cmdMExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);

		// Dead Tree Menue Commando
		cmdDTExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		
		// HelpScreen Comandos
		cmdHExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		//screenHelp.addCommand(cmdHExit);
		//screenHelp.setCommandListener(this);
		
		// HelpCredits Comandos
		cmdCExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		//screenCredits.addCommand(cmdCExit);
		
		
		GlobalVars.APPSTATUS=GlobalVars.APPSTATUS_INTRO;
		Display.getDisplay(this).setCurrent(screenIntro);
		screenIntro.play();

	}

	private void checkResume() {
		// Hier wird der Array aufgebaut fuer das Menue

		int i = 0;
		int check = -1; // wenn resume nicht dabei ist, maximal
		// menueintruegeanzahl minus 1

		short tmpVer = data.readDataInit();
		data.readDataFinalize();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) { // abfrage
			// RecordStore
			System.out.println("--- check ---");
			check = 0; // Maximalmenueeintruege wieder hergestellt!
		}

		mainElements = new String[GlobalVars.MAINMENU_LIST_MAX + check]; // anlegen
		// mit
		// oder
		// ohne
		// resume

		if (check == 0) {
			System.out.println("--- addEntry ---");
			mainElements[i++] = LangVars.CMD_STARTM_RESUME_TREE;
		}
		mainElements[i++] = LangVars.CMD_STARTM_NEW_TREE;
		mainElements[i++] = LangVars.CMD_STARTM_TUTORIAL;
		mainElements[i++] = LangVars.CMD_STARTM_CREDITS;

	}

	protected void pauseApp() {}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		System.out.println("SYSTEM EXIT! Program shut down!");
		this.notifyDestroyed();

	}

	public void commandAction(Command c, Displayable d) {
		int cori = 1; // abfrage ob resume oder nicht....
		if (mainElements.length == GlobalVars.MAINMENU_LIST_MAX) {
			cori = 0;
		}

		if (c == cmdMSelect)
		{
			switch (mainmenuList.getSelectedIndex() + cori) {

			case 0: { // im Resume;
//				System.out.println("0 = Im Resume");
				if (loadTree()) {
					Display.getDisplay(this).setCurrent(screenTree);
					screenTree.setCommandListener(this);
					
					GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_RUNNING;
					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
					screenTree.interval();
				}
				else {
					showMainMenu();
				}
				break;
			}
			case 1: { // New Tree
//				System.out.println("1 = im New Tree");
				screenTree = new ScreenTree(this);

				Display.getDisplay(this).setCurrent(screenTree);
				screenTree.setCommandListener(this);
				
				GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_RUNNING;
				screenTree.interval();
				break;
			}
			case 2: { // Im Tutorialauswahl
//				System.out.println("2 = im Tutorialauswahl");
				screenHelp.setCommandListener(this);
				Display.getDisplay(this).setCurrent(screenHelp);
				
				//screenHelp.repaint();
				break;
			}
			case 3: { // im Creditsauswahl
				Display.getDisplay(this).setCurrent(screenCredits);
				screenCredits.setCommandListener(this);
				screenCredits.repaint();
				break;
			}
			default: { // im default;
				System.out.println("Wie hast du das geschafft? Im Default....");
				break;
			}

			}
		} else if (c.equals(cmdMExit)) {
			System.out.println("SYSTEM EXIT! Program shut down!");
			this.notifyDestroyed();
		}

		// BaumMenue

		else if (c.equals(cmdDTExit)) {
//			System.out.println("Im cmdDTExit!!");
			doExitToMain();
		}
		
		else if (c.equals(cmdHExit)) {
			showMainMenu();
		}
		else if (c.equals(cmdCExit)) {
			showMainMenu();
		}
	}

	private void doExitToMain() {
		if (GlobalVars.APPSTATUS != GlobalVars.APPSTATUS_TREEDEAD) {
			saveTree();
		}
		screenTree.kill();
		screenTree = null;
		showMainMenu();
	}

	// obsolete since there is only one button at the moment
	private void clearCommands() {
		screenTree.removeCommand(cmdDTExit);
	}

	public void showMainMenu() {		
		// mainmenuList.setSelectCommand(cmdMSelect); //NICHT MIDP 1.0 f�hig
		checkResume(); // Array bauen fuer Liste
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		
		mainmenuList.addCommand(cmdMSelect);
		mainmenuList.addCommand(cmdMExit);
				
		mainmenuList.setCommandListener(this);
		Display.getDisplay(this).setCurrent(mainmenuList);
		
		GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_MAINMENU;
		System.out.println("--- MAIN MENUE CREATED ---");

	}

	private void showTreeDeadCommand() {
		clearCommands();
		screenTree.addCommand(cmdDTExit);

	}

	protected boolean loadTree() {

		if (screenTree != null) {
			screenTree.kill();
		}
//		System.out.println("Im load tree");

		short tmpVer = data.readDataInit();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {
			System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer	+ " ---");
			screenTree = new ScreenTree(this, data);
			data.readDataFinalize();
			
			return true;
		}
		else {
			return false;
		}
		
		// TODO: ausgabe + delete Record bzw. spaeter recordStore konverter

	}

	private void saveTree() {
		data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);
		screenTree.writeData(data);
		data.writeDataFinalize();
	}

	public void receiveFeedback(short code) {
		// code: 1x Commands - 10 = clearMenu, 11 = treeMenu, 12 = treeDead, 13 =
		// screenInterval; 2x keyEvent - 21 = fireButton; 31 = saveTree();

		System.out.println("feedback: " + code);
				
		switch (code) {
		
			case GlobalVars.APPSTATUS_MAINMENU:
				if (GlobalVars.APPSTATUS==GlobalVars.APPSTATUS_INTRO){
					GlobalVars.APPSTATUS = code;
					screenIntro=null;
					showMainMenu();
					
				}
				else {
					GlobalVars.APPSTATUS = code;
					doExitToMain();
				}
				break;				
				
			case GlobalVars.APPSTATUS_RUNNING:
				GlobalVars.APPSTATUS = code;
				clearCommands();
				break;
			
			case GlobalVars.APPSTATUS_STANDBY:
				GlobalVars.APPSTATUS = code;
				/*
				 * if (screenTree.threadInterval.isAlive())
				 * {System.out.println("Thread Interval is running");} else
				 * {System.out.println("Thread Interval is not running");}
				 */
				/*while (screenTree.threadInterval.isAlive()) {
					System.out.println("warten auf interval ende");
				}*/
				if (alarm == null || (alarm != null && !alarm.isAlive())) {
	//				System.out.println("Hallo Thread wird gestartet");
					alarm = new Thread(this);
					alarm.start();
				}
				break;
			case GlobalVars.APPSTATUS_TREEDEAD:
				GlobalVars.APPSTATUS = code;
				data.deleteRecord();
				showTreeDeadCommand();
				break;
				
			case 31:
				saveTree();
				break;
				
			default:
				GlobalVars.APPSTATUS = code;
				break;
		}
		System.out.println("APPSTATUS: "+GlobalVars.APPSTATUS);
	}

	public void run() {

//		System.out.println("!!----Im Thread----");

//		System.out.println("im while");
		try {
			Thread.sleep(GlobalVars.GROWTH_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			System.out.println("im caught");
			e.printStackTrace();
		}

//		System.out.println("----Thread Interval----");
		if (GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_STANDBY) {
			System.out.println("----Thread Interval Appstatus: " + GlobalVars.APPSTATUS + " ----");
			if (screenTree != null) {
				GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_RUNNING;
				clearCommands();
				screenTree.interval();
			}

		}

	}

}
