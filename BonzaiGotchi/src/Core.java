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
	private Command cmdEdit;
	private Command cmdSelect;
	private Command cmdExit;
	
	private FileIO data;


	protected void startApp() throws MIDletStateChangeException {
		cmdSave = new Command("Save", Command.OK, 2);
		cmdLoad = new Command("Load", Command.OK, 2);
		cmdBreak = new Command("Break", Command.CANCEL, 1);
		cmdResume = new Command("Resume", Command.OK, 1);
		cmdEdit = new Command("Edit", Command.OK, 2);
		cmdSelect = new Command("Select", Command.OK, 1);
		cmdExit = new Command("Exit", Command.EXIT,2);
		
		data = new FileIO("BonzaiGotchi");
		
		screenTree = new ScreenTree();
		screenTree.addCommand(cmdResume);
		screenTree.addCommand(cmdSave);
		screenTree.addCommand(cmdLoad);
		screenTree.addCommand(cmdExit);
		screenTree.setCommandListener(this);
		
		Display.getDisplay(this).setCurrent(screenTree);
		GlobalVars.APPSTATUS=1;
	}
	
	protected void pauseApp() {
		

	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		

	}

	public void commandAction(Command c, Displayable d) {
		if (c.equals(cmdSave)) {
			data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);	
			screenTree.writeData(data);
			data.writeDataFinalize();
		}
		else if (c.equals(cmdLoad)) {
			screenTree.kill();
			GlobalVars.COUNTERELEMENT = 0;
			
			short tmpVer = data.readDataInit();
			if (tmpVer > 0) {
				System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer + " ---");
				screenTree = new ScreenTree(data);
				screenTree.addCommand(cmdSave);
				screenTree.addCommand(cmdLoad);
				screenTree.addCommand(cmdResume);
				screenTree.addCommand(cmdEdit);
				screenTree.addCommand(cmdExit);
				screenTree.setCommandListener(this);
				Display.getDisplay(this).setCurrent(screenTree);
				data.readDataFinalize();
			}
		}
		else if (c.equals(cmdBreak)) {
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
		else if (c.equals(cmdExit)) {
			screenTree.stopThread();
			screenTree.kill();
			this.notifyDestroyed();
		}
	}

}
