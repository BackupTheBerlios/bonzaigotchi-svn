import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;


public class ScreenAdmin extends Canvas {
	
	ReceiveFeedback parent;
	
	private FileIO data;
	
	private boolean treeActive = false;
	private String[] treeBackup = null;
	private int treeBackupSelected = 0;
	private int treeBackupNextId;
	
	private String[] menu = null;
	private int menuSelected = 0;
	
	private short adminStatus = 0;
	
	public ScreenAdmin(ReceiveFeedback parent) {
		
		this.parent = parent;

		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		}
		
		getData();
		
		showRecordList();	
	}
	
	private void getData() {
//		 check for active Tree
		data = new FileIO(GlobalVars.RECORDSTORE_NAME);
		short tmpVer = data.readDataInit();
		data.readDataFinalize();
		if (tmpVer == GlobalVars.SAVE_RECORDSTORE_VERSION) {
			treeActive = true;
		}
		
		
		// check for Backups
		String[] recordList = FileIO.getRecordList();
		
		int treeBackupCounter = 0;
		for (int i = 0; i < recordList.length; i++) {
			if (recordList[i].startsWith(GlobalVars.RECORDSTORE_NAME + "_")) {
				treeBackupCounter++;
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
	
	private void showTreeActiveOptions() {
		clearList();
		menuSelected = 0;
		menu = new String[] { "backup", "back" };
		adminStatus = 2;
	}
	
	private void showTreeBackupOptions() {
		clearList();
		menuSelected = 0;
		menu = new String[] { "make Active", "delete", "back" };
		adminStatus = 3;
	}
	
	private void clearList() {
		menu = null;
	}


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
								data.setRecordName(treeBackup[treeBackupSelected]);
								data.deleteRecord();
								getData();
								showRecordList();
								break;
								
							case 2:
								showRecordList();
								break;
						}
						
						break;
				}
				break;
			
		}
		repaint();
	}
		
		
		


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
