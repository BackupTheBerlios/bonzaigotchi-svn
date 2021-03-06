import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * The class ScreenAdmin contains the MenuItems for the Admin screen.
 * It also contains a draw function.
 */
public class ScreenAdmin extends Canvas {
	
	ReceiveFeedback parent;
	
	private FileIO data;
	private NetIO netIO;
	
	private boolean treeActive = false;
	private String[] treeBackup = null;
	private int treeBackupSelected = 0;
	private int treeBackupNextId;
	
	private String[] menu = null;
	private int menuSelected = 0;
	
	private short adminStatus = 0;
	
	/**
	 * Constructs the Admin Backups menu.
	 * @param parent
	 */
	public ScreenAdmin(ReceiveFeedback parent) {
		
		this.parent = parent;
		netIO = new NetIO(parent);

		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		}
		
		getData();
		
		showRecordList();	
	}
	/**
	 * Checks for active Tree(s) and for their backups.
	 * Global Variables treeActive and treeBackup are set.
	 */
	private void getData() {

		data = new FileIO(GlobalVars.RECORDSTORE_NAME);
		short tmpVer = data.readDataInit();
		data.readDataFinalize();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {
			treeActive = true;
		}
		
		
		
		String[] recordList = FileIO.getRecordList();
		int treeBackupCounter = 0;
		if (recordList != null) {
			for (int i = 0; i < recordList.length; i++) {
				if (recordList[i].startsWith(GlobalVars.RECORDSTORE_NAME + "_")) {
					treeBackupCounter++;
				}
			}
		}
		
		treeBackup = null;
		treeBackupNextId = 0;
		if (treeBackupCounter > 0) {
			treeBackup = new String[treeBackupCounter];
			int n = 0;
			for (int i = 0; i < recordList.length; i++) {
				if (recordList[i].startsWith(GlobalVars.RECORDSTORE_NAME + "_")) {
//					System.out.println("--- ScreenAdmin.treeBackup["+n+"] = " + recordList[i] + " ---");
					if (recordList[i].endsWith(""+treeBackupNextId)) treeBackupNextId++;
					treeBackup[n] = recordList[i];
					n++;
				}
			}
		}
	}
	/**
	 * Builds up the menu based on how much trees/backups were found.
	 * sets adminStatus to 1.
	 *
	 */
	private void showRecordList() {
		clearList();
		menuSelected = 0;
		
		if (treeBackup != null) menu = new String[treeBackup.length + 2];
		else menu = new String[2];
		
		if (treeActive) menu[0] = "active Tree";
		else menu[0] = "no active Tree";
		
		if (treeBackup != null) {
			for (int i=0; i<treeBackup.length; i++) {
				menu[i+1] = "saved Tree #" + i;
			}
		}
		
		menu[menu.length-1] = "Exit";
		
		adminStatus = 1;
	}
	
	/**
	 * Builds up the menu within the menupoint "active Tree".
	 *
	 */
	private void showTreeActiveOptions() {
		clearList();
		menuSelected = 0;
		menu = new String[] { "backup", "back" };
		adminStatus = 2;
	}
	
	/**
	 * Builds up the menu when a tree is selected.
	 *
	 */
	private void showTreeBackupOptions() {
		clearList();
		menuSelected = 0;
		menu = new String[] { "make Active", "send", "delete", "back" };
		adminStatus = 3;
	}
	
	/**
	 * Clears the menu list.
	 *
	 */
	private void clearList() {
		menu = null;
	}

	/**
	 * Determines the pressed key and selects the menu point.
	 * adminStatus tells in which menu we are right now.
	 * Then repaints the menu.
	 * 
	 * @param keyCode
	 */
	protected void keyPressed (int keyCode) {
		
		switch (getGameAction(keyCode)) {
		
			case UP:
				if (--menuSelected < 0) menuSelected = 0;
				break;
				
			case DOWN:
				if (++menuSelected > menu.length-1) menuSelected = menu.length-1;
				break;
				
			case FIRE:
				
				switch (adminStatus) {
				// Main List
					case 1: 
						if (menuSelected == 0) {
							if (treeActive) {
								showTreeActiveOptions();
							}
						}
						else if (menuSelected == menu.length-1) {
							parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
						}
						
						else {
							for (int i = 0; i<treeBackup.length; i++) {
								if (menuSelected == i+1) {
									treeBackupSelected = i;
									showTreeBackupOptions();
									break;
								}
							}
						}
						break;
					
					case 2:
						switch (menuSelected) {
							case 0:
								data.setRecordName(GlobalVars.RECORDSTORE_NAME);
								data.copyRecord(GlobalVars.RECORDSTORE_NAME + "_" + treeBackupNextId);
								getData();
								showRecordList();
								break;
							case 1:
								showRecordList();
								break;
						}
						break;
						
					case 3:
						switch (menuSelected) {
							case 0:
								data.setRecordName(treeBackup[treeBackupSelected]);
								data.copyRecord(GlobalVars.RECORDSTORE_NAME);
								getData();
								showRecordList();
								break;
							
							case 1:
								netIO.sendData(new FileIO(treeBackup[treeBackupSelected]));
								break;
								
							case 2:
								data.setRecordName(treeBackup[treeBackupSelected]);
								data.deleteRecord();
								getData();
								showRecordList();
								break;
								
							case 3:
								showRecordList();
								break;
						}
						
						break;
				}
				break;
			
		}
		repaint();
	}
		
		
		

	/**
	 * Paints the ScreenAdmin menu.
	 * 
	 * @param g
	 */
	protected void paint(Graphics g) {
		Font f = g.getFont();
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
		
		g.setColor(0x000000);
		for (int i=0; i<menu.length; i++) {
			if (i == menuSelected) {
				g.drawLine(0, 20*i + f.getHeight()+2, f.stringWidth(menu[i]), 20*i + f.getHeight()+2);
			}
			g.drawString(menu[i], 0, 20*i, Graphics.TOP|Graphics.LEFT);
		}
	}

}
