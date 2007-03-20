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

	// TODO: private ScreenMenu screenMenu;
	// TODO: private ScreenHelp screenHelp;

	// Hauptmenue ist List
	private List mainmenuList;

	private String[] mainElements;

	private Command cmdMSelect;

	private Command cmdMExit;

	private Command cmdTMenu;
	private Command cmdTBack;
	private Command cmdTExit;

	// Dead-Tree Menue Commando
	private Command cmdDTExit;

	private FileIO data;

	private Thread alarm;

	protected void startApp() throws MIDletStateChangeException {
		data = new FileIO("BonzaiGotchi");

		// ScreenMenu screeny = new ScreenMenu();
		// screeny.initialize();

		// TODO: Thread "Timer" starten, schlaeft bis GLOBALVARSirgendwas;
		// ueberprueft Appstatus und wenn 1 => resume() sonst schlaf;

		checkResume(); // Array bauen fuer Liste

		// Hauptmenue Commandos
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		cmdMExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		showMainMenu();

		cmdTMenu = new Command(LangVars.CMD_TREEMENU_MENU, Command.OK, 1);
		cmdTBack = new Command(LangVars.CMD_ALL_BACK, Command.OK, 1);
		cmdTExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);

		// Dead Tree Menue Commando
		cmdDTExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);

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

	protected void pauseApp() {

	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		System.out.println("SYSTEM EXIT! Program shut down!");
		this.notifyDestroyed();

	}

	public void receiveSelect() {

	}

	public void commandAction(Command c, Displayable d) {
		int cori = 1; // abfrage ob resume oder nicht....
		if (mainElements.length == GlobalVars.MAINMENU_LIST_MAX) {
			cori = 0;
		}
		;

		if (c == cmdMSelect) // APPSTATUS: 0 = init, 1 = standBy, 2 =
		// running, 3 = edit, 4 = watering
		{
			switch (mainmenuList.getSelectedIndex() + cori) {

			case 0: { // im Resume;
//				System.out.println("0 = Im Resume");
				loadTree();
				GlobalVars.APPSTATUS = 2;
				Display.getDisplay(this).setCurrent(screenTree);
				System.out.println("--- cmdBreak GlobalVars.APPSTATUS: "
						+ GlobalVars.APPSTATUS + " ---");
				// showTreeMenuCommand();
				clearCommands();
				screenTree.interval();
				break;
			}
			case 1: { // New Tree
//				System.out.println("1 = im New Tree");
				screenTree = new ScreenTree(this);
				// showTreeMenuCommand(); //TODO kommt weg... weil von
				// Screentree aufgerufen!
				Display.getDisplay(this).setCurrent(screenTree);
				GlobalVars.APPSTATUS = 2;
				clearCommands();
				screenTree.interval();
				break;
			}
			case 2: { // Im Tutorialauswahl
				System.out.println("2 = im Tutorialauswahl");
				break;
			}
			case 3: { // im Creditsauswahl
				System.out.println("3 = im creditauswahl");
				saveTree();
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

		else if (c.equals(cmdTExit)) {
			doExitToMain();
		}
		
		else if (c.equals(cmdTMenu)) {
			screenTree.menuShow();
			showTreeMenuMenuActive();
		}

		else if (c.equals(cmdTBack)) {
			GlobalVars.APPSTATUS = 1;
			screenTree.receiveBack();
			System.out.println("--- cmdBreak GlobalVars.APPSTATUS: "
					+ GlobalVars.APPSTATUS + " ---");

		} else if (c.equals(cmdDTExit)) {
			// screenTree.kill();
//			System.out.println("Im cmdDTExit!!");
			screenTree.kill();
			screenTree = null;
			checkResume();
			mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT,
					mainElements, null);
			showMainMenu();
			// doExitToMain();
		}

	}


	public void resetTreeMenu() {
		GlobalVars.APPSTATUS = 1;
		showTreeMenuCommand();
	}

	private void doExitToMain() {
		saveTree();
		screenTree.kill();
		screenTree = null;
		checkResume();
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT,
				mainElements, null);
		showMainMenu();
	}

	private void clearCommands() {
		screenTree.removeCommand(cmdMSelect);
		screenTree.removeCommand(cmdMExit);

		screenTree.removeCommand(cmdTMenu);
		screenTree.removeCommand(cmdTBack);
		screenTree.removeCommand(cmdTExit);
		screenTree.removeCommand(cmdDTExit);
	}

	private void showMainMenu() {
		GlobalVars.APPSTATUS = 1;
		// mainmenuList.setSelectCommand(cmdMSelect); //NICHT MIDP 1.0 f�hig
		mainmenuList.addCommand(cmdMSelect);
		mainmenuList.addCommand(cmdMExit);
		mainmenuList.setCommandListener(this);
		Display.getDisplay(this).setCurrent(mainmenuList);
		System.out.println("--- MAIN MENUE CREATED ---");

	}

	private void showTreeMenuCommand() {
		clearCommands();
		screenTree.addCommand(cmdTMenu);
		screenTree.addCommand(cmdTExit);

		screenTree.setCommandListener(this);
	}
	
	private void showTreeMenuMenuActive() {
		clearCommands();
		screenTree.addCommand(cmdTBack);
		screenTree.addCommand(cmdTExit);
	}


	private void showTreeDeadCommand() {
		clearCommands();
		screenTree.addCommand(cmdDTExit);

	}

	protected void loadTree() {

		if (screenTree != null) {
			screenTree.kill();
		}
//		System.out.println("Im load tree");
		GlobalVars.COUNTERELEMENT = 0;

		short tmpVer = data.readDataInit();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {
			System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer
					+ " ---");
			screenTree = new ScreenTree(this, data);

			clearCommands();
			// showTreeMenuCommand();

			Display.getDisplay(this).setCurrent(screenTree);
			data.readDataFinalize();
		} // TODO: ausgabe + delete Record bzw. spaeter recordStore konverter

	}

	protected void saveTree() {
		data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);
		screenTree.writeData(data);
		data.writeDataFinalize();
	}

	public void receiveFeedback(byte code) {
		// code: 1x Commands - 10 = clearMenu, 11 = treeMenu, 12 = treeDead, 13 =
		// screenInterval; 2x keyEvent - 21 = fireButton; 31 = saveTree();

		System.out.println("feedback: " + code);
		switch (code) {
		
		case 10:
			clearCommands();
			break;
		
		case 11:
			resetTreeMenu();
			System.out.println("APPSTATUS: "+GlobalVars.APPSTATUS);
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
		case 12: {
			GlobalVars.APPSTATUS = 19;
			data.deleteRecord();
			showTreeDeadCommand();
//			System.out.println("Im 12er drin!!!!");
			break;
		}
		case 13:

			break;

		case 31:
			saveTree();
			//GlobalVars.APPSTATUS=1;
			break;
		}

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
		if (GlobalVars.APPSTATUS == 1) {
			System.out.println("----Thread Interval Appstatus: 2 ----");
			if (screenTree != null) {
				GlobalVars.APPSTATUS=2;
				clearCommands();
				screenTree.interval();
			}

		}

	}

}
