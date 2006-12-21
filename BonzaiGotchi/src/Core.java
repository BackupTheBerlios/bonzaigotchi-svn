// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            added screenTree


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

	

public class Core extends MIDlet implements CommandListener {

	private ScreenTree screenTree;
//	private ScreenMenu screenMenu;
//	private ScreenHelp screenHelp;
	
	private Command cmdSave;
	private Command cmdLoad;
	private Command cmdBreak;
	private Command cmdResume;
	
	private FileIO data;


	protected void startApp() throws MIDletStateChangeException {
		cmdSave = new Command("Save", Command.OK, 2);
		cmdLoad = new Command("Load", Command.OK, 2);
		cmdBreak = new Command("Break", Command.OK, 1);
		cmdResume = new Command("Resume", Command.OK, 1);
		
		
		data = new FileIO("BonzaiGotchi");
		
		screenTree = new ScreenTree();
		screenTree.addCommand(cmdSave);
		screenTree.addCommand(cmdLoad);
		screenTree.addCommand(cmdBreak);
		
		Display.getDisplay(this).setCurrent(screenTree);
		screenTree.interval();
/*		data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);
		screenTree.writeData(data);
		data.writeDataFinalize();
		GlobalVars.COUNTERELEMENT = 0;
		short tmpVer = data.readDataInit();
		if (tmpVer > 0) {
			System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer + " ---");
			screenTree = new ScreenTree(data);
			data.readDataFinalize();
			screenTree.interval();
		}
		else {
			System.out.println("--- Core: DATAINIT No Record Found: " + tmpVer + " ---");
		}
*/
	}
	
	protected void pauseApp() {
		

	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		

	}

	public void commandAction(Command c, Displayable d) {
		if (c.equals(cmdSave)) {
			
		}
		if (c.equals(cmdLoad)) {
			
		}
		if (c.equals(cmdBreak)) {
			screenTree.stopThread();
			screenTree.removeCommand(cmdBreak);
			screenTree.addCommand(cmdResume);
		}
		if (c.equals(cmdResume)) {
			screenTree.removeCommand(cmdResume);
			screenTree.addCommand(cmdBreak);
		}
	}

}
