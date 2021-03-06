// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            added screenTree
// 2007-01-30 new constructed.... by fiips 
// 2007-02-28 ausgemistet & fertiggebaut  ..... by fiips

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


/** 
 * Core is the main Midlet Class.
 * From here the application starts.  
 */ 
public class Core extends MIDlet implements CommandListener, ReceiveFeedback, Runnable {

	private ScreenTree screenTree;
	private ScreenHelp screenHelp;
	private ScreenHelpButtons screenHelpButtons;
	private ScreenCredits screenCredits;
	private ScreenIntro screenIntro;
	private ScreenHelpButtonsShow screenHBS;
	private ScreenAdmin screenAdmin;
	private Alert screenAlert;

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
	private Command cmdHButtons;
	
	// HelpScreenButtons Commandos
	private Command cmdHBack;
	
	// HelpScreenButtonsShow Commandos
	private Command cmdHSBack;
	
	// CreditsScreen Commandos
	private Command cmdCExit;
	private FileIO data;

	private Thread alarm;

	 
	/** 
	 * Entry point in Core.
	 * 
	 * 	@throws MIDletStateChangeException
	 */ 
	protected void startApp() throws MIDletStateChangeException {	
	
		data = new FileIO(GlobalVars.RECORDSTORE_NAME);
		screenIntro = new ScreenIntro(this);
		screenHelp = new ScreenHelp();
		screenCredits = new ScreenCredits();
		screenHelpButtons = new ScreenHelpButtons(this);
		screenAlert = new Alert("Tree Upload");
		screenAlert.setTimeout(Alert.FOREVER);

		// ScreenMenu screeny = new ScreenMenu();
		// screeny.initialize();

		// Hauptmenue Commandos
		cmdMSelect = new Command(LangVars.CMD_ALL_SELECT, Command.ITEM, 2);
		cmdMExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);

		// Dead Tree Menue Commando
		cmdDTExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		
		// ScreenHelp Commandos
		cmdHExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		cmdHButtons = new Command("Buttons", Command.OK, 1);
		
		// ScreenHelpButtons Commandos
		cmdHBack = new  Command(LangVars.CMD_ALL_BACK, Command.CANCEL, 1);
		
		// ScreenHelpButtonsShow Commandos
		cmdHSBack = new  Command(LangVars.CMD_ALL_BACK, Command.CANCEL, 1);
		
		// ScreenCredits Comandos
		cmdCExit = new Command(LangVars.CMD_ALL_EXIT, Command.EXIT, 1);
		screenCredits.addCommand(cmdCExit);
		
		
		GlobalVars.APPSTATUS=GlobalVars.APPSTATUS_INTRO;
		Display.getDisplay(this).setCurrent(screenIntro);
		screenIntro.play();

	}
	 
	/** 
	 * Builds up an array for the menu.
	 */
	private void checkResume() {
		// Hier wird der Array aufgebaut fuer das Menue

		int i = 0;
		int check = -1; // wenn resume nicht dabei ist, maximal
		// menueintruegeanzahl minus 1

		short tmpVer = data.readDataInit();
		data.readDataFinalize();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) { // abfrage
			// RecordStore
//			System.out.println("--- check ---");
			check = 0; // Maximalmenueeintruege wieder hergestellt!
		}

		mainElements = new String[GlobalVars.MAINMENU_LIST_MAX + check]; // anlegen
		// mit
		// oder
		// ohne
		// resume

		if (check == 0) {
//			System.out.println("--- addEntry ---");
			mainElements[i++] = LangVars.CMD_STARTM_RESUME_TREE;
		}
		mainElements[i++] = LangVars.CMD_STARTM_NEW_TREE;
		mainElements[i++] = LangVars.CMD_STARTM_TUTORIAL;
		mainElements[i++] = LangVars.CMD_STARTM_CREDITS;
		mainElements[i++] = LangVars.CMD_STARTM_ADMIN;

	}
	/** 
	 * Pauses application
	 *  @throws MIDletStateChangeException
	 */
	protected void pauseApp() {}
	 
	/** 
	 * Destroys application
	 *  @throws MIDletStateChangeException
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
//		System.out.println("SYSTEM EXIT! Program shut down!");
		this.notifyDestroyed();

	}
	/** 
	 * Links to the content of the Menu points.
	 * 
	 * @param c  gives us the destination of the menu. d  contains a menulist
	 */
	public void commandAction(Command c, Displayable d) {
		int cori = 1; // abfrage ob resume oder nicht....
		if (mainElements.length == GlobalVars.MAINMENU_LIST_MAX) {
			cori = 0;
		}

		if (c == cmdMSelect || (d.equals(mainmenuList) && c == List.SELECT_COMMAND))
		{
			switch (mainmenuList.getSelectedIndex() + cori) {

			case 0: { // im Resume;
//				System.out.println("0 = Im Resume");
				if (loadTree()) {
					Display.getDisplay(this).setCurrent(screenTree);
					screenTree.setCommandListener(this);
					
					GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_RUNNING;
//					System.out.println("--- cmdBreak GlobalVars.APPSTATUS: " + GlobalVars.APPSTATUS + " ---");
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
				//clearCommands();
				GlobalVars.APPSTATUS=GlobalVars.APPSTATUS_HELP;
				screenHelp.addCommand(cmdHExit);
				screenHelp.addCommand(cmdHButtons);
				screenHelp.setCommandListener(this);
				Display.getDisplay(this).setCurrent(screenHelp);
				
				//screenHelp.repaint();
				break;
			}
			case 3: { // im Creditsauswahl
				Display.getDisplay(this).setCurrent(screenCredits);
				screenCredits.setCommandListener(this);
				break;
			}
			case 4:
				screenAdmin = new ScreenAdmin(this);
				GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_ADMIN;
				Display.getDisplay(this).setCurrent(screenAdmin);
				break;
			default: { // im default;
				System.out.println("Wie hast du das geschafft? Im Default....");
				break;
			}

			}
		} else if (c.equals(cmdMExit)) {
//			System.out.println("SYSTEM EXIT! Program shut down!");
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
		else if (c.equals(cmdHBack)) {
			Display.getDisplay(this).setCurrent(screenHelp);
			
		}
		else if (c.equals(cmdCExit)) {
			showMainMenu();
		}
		else if (c.equals(cmdHButtons)) {
			//clearCommands();
			//screenHelpButtons.addCommand(cmdHExit);
			//screenHelpButtons.addCommand(cmdHBack);
			//screenHelpButtons.setCommandListener(screenHelpButtons);
			Display.getDisplay(this).setCurrent(screenHelpButtons);
			
		}
		else if (c.equals(cmdHSBack)) {
			Display.getDisplay(this).setCurrent(screenHelpButtons);
			
		}
	}
	/** 
	 * Exits from Tree to MainMenu. 
	 * Tree will be saved in case GlobalVars.APPSTATUS is not set to APPSTATUS_TREEDEAD. 
	 *  
	 */
	private void doExitToMain() {
		if (GlobalVars.APPSTATUS != GlobalVars.APPSTATUS_TREEDEAD) {
			saveTree();
		}
		screenTree.kill();
		screenTree = null;
		showMainMenu();
	}

	/** 
	 * Removes cmdDTExit from screenTree. 
	 */
	 //* @deprecated obsolete since there is only one button at the moment (ich lass es mal auskommentiert weil die funktion �fter aufgerufen wird)
	 //*/
	private void clearCommands() {
		screenTree.removeCommand(cmdDTExit);
		/*screenTree.removeCommand(cmdMSelect);
		screenTree.removeCommand(cmdMExit);

		screenHelp.removeCommand(cmdHExit);
		screenHelp.removeCommand(cmdHButtons);		
		screenHelpButtons.removeCommand(cmdHExit);
		screenHelpButtons.removeCommand(cmdHBack);
		screenCredits.removeCommand(cmdCExit); */
	}
	
	/** 
	 * Displays the Main Menu.
	 * Sets the APPSTATUS to APPSTATUS_MAINMENU. 
	 */
	private void showMainMenu() {		
		// mainmenuList.setSelectCommand(cmdMSelect); //NICHT MIDP 1.0 f�hig
		checkResume(); // Array bauen fuer Liste
		mainmenuList = new List(LangVars.MENU_NAME, List.IMPLICIT, mainElements, null);
		
		mainmenuList.addCommand(cmdMSelect);
		mainmenuList.addCommand(cmdMExit);
				
		mainmenuList.setCommandListener(this);
		Display.getDisplay(this).setCurrent(mainmenuList);
		
		GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_MAINMENU;
//		System.out.println("--- MAIN MENUE CREATED ---");
	}
	/** 
	 * Displays an Alert Message defined in screenAlert. 
	 */
	private void showAlert() {
		Display.getDisplay(this).setCurrent(screenAlert, Display.getDisplay(this).getCurrent());
	}
	
	/** 
	 * Clears all commands and adds an exit Command.
	 */
	private void showTreeDeadCommand() {
		clearCommands();
		screenTree.addCommand(cmdDTExit);

	}
	
	/** 
	 * Loads a new tree from a record store.
	 * Kills an existing tree if availible.
	 * 
	 * @return true if Version from record store equals GlobalVars.SAVE_RECORDSTORE_VERSION.
	 */
	protected boolean loadTree() {

		if (screenTree != null) {
			screenTree.kill();
		}
//		System.out.println("Im load tree");

		short tmpVer = data.readDataInit();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {
//			System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer	+ " ---");
			screenTree = new ScreenTree(this, data);
			data.readDataFinalize();
			
			return true;
		}
		else {
			return false;
		}
		
		// TODO: ausgabe + delete Record bzw. spaeter recordStore konverter

	}
	/** 
	 * Writes tree data in record store.
	 * calls screenTree.writeData()
	 * 
	 */
	private void saveTree() {
		data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);
		screenTree.writeData(data);
		data.writeDataFinalize();
	}

	
	/** 
	 * Receives an APPSTATUS which determines what Menu will be displayed.
	 * 
	 * @param code code: 1x Commands - 10 = clearMenu, 11 = treeMenu, 12 = treeDead, 13 =
	 * screenInterval; 2x keyEvent - 21 = fireButton; 31 = saveTree();
	 */
	public void receiveFeedback(short code) {
		
//		System.out.println("feedback: " + code);
				
		switch (code) {
		
			case GlobalVars.APPSTATUS_MAINMENU:
				
				switch (GlobalVars.APPSTATUS) {
				
					case GlobalVars.APPSTATUS_INTRO:
						GlobalVars.APPSTATUS = code;
						screenIntro=null;
						showMainMenu();
						break;
						
					case GlobalVars.APPSTATUS_HELP:
						GlobalVars.APPSTATUS = code;
						showMainMenu();
						break;
						
					case GlobalVars.APPSTATUS_ADMIN:
						screenAdmin = null;
						GlobalVars.APPSTATUS = code;
						showMainMenu();
						break;						

					default:
						GlobalVars.APPSTATUS = code;
						doExitToMain();
						break;
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
				
			case GlobalVars.FEEDBACK_SAVE:
				saveTree();
				break;
				
			case GlobalVars.APPSTATUS_HELP_WORK:
				screenHBS = new ScreenHelpButtonsShow();
				screenHBS.addCommand(cmdHSBack);
				screenHBS.setCommandListener(this);
				Display.getDisplay(this).setCurrent(screenHBS);
				break;
				
			case GlobalVars.FEEDBACK_UPLOAD_SUCCESS:
				screenAlert.setString("Tree upload was successful");
				showAlert();
				break;
			case GlobalVars.FEEDBACK_UPLOAD_NOSUCCESS:
				screenAlert.setString("Tree upload was NOT successful");
				showAlert();
				break;
				
			default:
				GlobalVars.APPSTATUS = code;
				break;
		}
//		System.out.println("APPSTATUS: "+GlobalVars.APPSTATUS);
	}
	/** 
	 * sleeps for GlobalVars.GROWTH_INTERVAL and if APPSTATUS equals GlobalVars.APPSTATUS_STANDBY
	 * and screenTree not null, then it marks APPSTATUS as running.
	 * 
	 */
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
//			System.out.println("----Thread Interval Appstatus: " + GlobalVars.APPSTATUS + " ----");
			if (screenTree != null) {
				GlobalVars.APPSTATUS = GlobalVars.APPSTATUS_RUNNING;
				clearCommands();
				screenTree.interval();
			}

		}

	}

}
