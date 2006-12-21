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
	
	private FileIO data;


	protected void startApp() throws MIDletStateChangeException {
		data = new FileIO("BonzaiGotchi");
		
		screenTree = new ScreenTree();
		Display.getDisplay(this).setCurrent(screenTree);
		screenTree.interval();
/*		data.writeDataInit(GlobalVars.SAVE_RECORDSTORE_VERSION);
		screenTree.writeData(data);
		data.writeDataFinalize();
		short tmpVer = data.readDataInit();
		System.out.println("--- Core: DATAINIT FINISHED: " + tmpVer + " ---");
		screenTree = new ScreenTree(data);
		data.readDataFinalize();
		screenTree.interval();
*/
	}
	
	protected void pauseApp() {
		

	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		

	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		
	}

}
