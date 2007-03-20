// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19 updated by chappy
//            altered 		constructor
//            altered 		run()
//            added 		writeData()
// 2006-01-30  updated by Sven
//			  altered 		paint()
//			  altered 		run()
//			  altered 	  	edit()
//			  altered 	  	keyPressed()
//			  altered     	screenTree()
//			  added 	  	watering()
//			  added 	  	drawCan()
//			  added 	  	drawWater()
//			  added 	  	drawWatering()
//			  added 	  	drawPot()
//			  added 	  	actionWatering()

import java.util.Date;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;


public class ScreenTree extends Canvas implements Runnable {

	// Children
	private Element log;
	
	// Pot
	private byte potSize;
	private int water;
	private int logWaterRequest;
	
	// Gauge
	private Can can;
	private int canValue;
	private int canSteps;
	
	// 0 = parent, 1 = EditElement, 2 = Can, 3 = Pot, 4 = editExact, 5 = menu
	private int keyReceiver;
	
	private int menuId = -1;
	private MenuItem[] menu;
	private int menuItemSelected = 0;
	
	// Thread
	public Thread threadInterval;
	private boolean threadWaiting = false;
	
	//	private boolean animWatering = false;
	
	private ReceiveFeedback parent;
	
	public ScreenTree(ReceiveFeedback tmpParent) {
		super();
		parent = tmpParent;

		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date();
		GlobalVars.COUNTERELEMENT = 0;
		GlobalVars.COUNTERINTERVAL = 0;
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();

		// Gauge
		potSize=0;
		can = new Can((short)0,(short)(GlobalVars.DISPLAY_X_WIDTH/2),(short)(GlobalVars.DISPLAY_Y_HEIGHT/2),(short)0);
		
		water = GlobalVars.POT_WATER_INIT;
		log = new Element(null, (short)0, (short)(GlobalVars.DISPLAY_X_WIDTH / 2), GlobalVars.DISPLAY_Y_HEIGHT, 10000);
		logWaterRequest = log.getChildWaterRequest();
	}
	
	public ScreenTree(ReceiveFeedback tmpParent, FileIO data) {
		
		parent = tmpParent;
				
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date(data.readDataLong());
		GlobalVars.COUNTERELEMENT = 0;
		GlobalVars.COUNTERINTERVAL = data.readDataInt();
		GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
		GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		
		water = data.readDataInt();
		potSize = data.readDataByte();
		log = new Element(null, data);
		logWaterRequest = log.getChildWaterRequest();
		can = new Can((short)0,(short)(GlobalVars.DISPLAY_X_WIDTH/2),(short)(GlobalVars.DISPLAY_Y_HEIGHT/2),(short)0);
	}
	
	
	protected void paint(Graphics g) {
//		System.out.println("--- ScreenTree.paint BEGINN ---");

		g.setFont(Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_SMALL));		
		drawBackground(g);
		
		switch (GlobalVars.APPSTATUS) {
		
			case GlobalVars.APPSTATUS_EDITEXACT:
			// los ma wieder durchlafn ;)
			case GlobalVars.APPSTATUS_MENU:
				for (int i = 0; i < menu.length; i++) {
					g.setClip(i * 20, 0, 20, 20);
					menu[i].draw(g);
					if (i == menuItemSelected) {
						g.setColor(0xFF0000);
						g.fillRect(i * 20, 18, 20, 2);
						g.setClip(0, 20, 100, 20);
						g.setColor(0x000000);
						g.drawString(menu[i].getTitle(), 2, 24, Graphics.TOP|Graphics.LEFT);
					}
				}
				break;
		
		}
		
		g.setClip(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);

		
		if (log != null) {
		
			drawPot(g);
			
			GlobalVars.PAINTSTATUS = 1;
			log.draw(g);
			GlobalVars.PAINTSTATUS = 0;					
			
			if (GlobalVars.APPSTATUS == 3 || GlobalVars.APPSTATUS == 31) {
				GlobalVars.PAINTSTATUS = 2;
				log.draw(g);
				GlobalVars.PAINTSTATUS = 0;
			}
			
			if (GlobalVars.APPSTATUS == 31) {
				GlobalVars.PAINTSTATUS = 3;
				log.draw(g);
				GlobalVars.PAINTSTATUS = 0;
			}
			
			if (GlobalVars.APPSTATUS == 4) {
				can.draw(g);
			}
			
			if (threadWaiting && GlobalVars.APPSTATUS == 2) {
				interval();
			}
		}
		else {
			g.setColor(0x555555);
			g.drawString(LangVars.DIE_MESSAGE, 0,0,0);
		}
			
	}
	
	private void drawBackground(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
	}

	private void drawWatering(Graphics g) {
		/* graphic of pot */
		// while animation=overpainting set flag animWatering = true;
		/* animation for watering */
		
		
		
		//g.setColor(255,255,255);
		//g.fillRect(x/2+lastposgauge-50,y/2-10,5,20);
		
		//g.setColor(100,100,100);
		/*switch(potsize){
			case 0:
				g.fillRect(x/2-30,y/2,60,5);
				break;
			case 1:
				g.fillRect(x/2-30,y/2,60,5);
				break;
			case 2:
				g.fillRect(x/2-30,y/2,60,5);
				break;
		}*/
		
//		drawCan(g);
		//g.setColor(50,50,50);
		//g.fillRect(x/2+gauge-50,y/2-10,5,20);
		
	}

	private void drawPot(Graphics g){
		//		drawing a pot
		
		if (water >= GlobalVars.POT_SIZE[potSize]) {
						
			int overWater = (water - GlobalVars.POT_SIZE[potSize]) * 100 / GlobalVars.POT_SIZE[potSize];
			if (overWater > GlobalVars.POT_HEIGHT[potSize]) {
				overWater = GlobalVars.POT_HEIGHT[potSize];
			}
			
			g.setColor(GlobalVars.COLOR_POT_WATER);
			for(int i=0; i <= overWater; i++) {
				g.drawLine(
						GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSize] - i / 2,
						GlobalVars.DISPLAY_Y_HEIGHT - i,
						GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSize] + i / 2,
						GlobalVars.DISPLAY_Y_HEIGHT - i);
			}
		}
		
		g.setColor(100,50,50);
		for (int i=0; i <= GlobalVars.POT_THICKNESS; i++) {
			g.drawLine(
					GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSize] + i,
					GlobalVars.DISPLAY_Y_HEIGHT,
					GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSize] - GlobalVars.POT_HEIGHT[potSize] /2 + i,
					GlobalVars.DISPLAY_Y_HEIGHT - GlobalVars.POT_HEIGHT[potSize]);
			
			g.drawLine(
					GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSize] - i,
					GlobalVars.DISPLAY_Y_HEIGHT,
					GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSize] + GlobalVars.POT_HEIGHT[potSize] /2 - i,
					GlobalVars.DISPLAY_Y_HEIGHT - GlobalVars.POT_HEIGHT[potSize]);			
		}
		
	}

	
	public void menuShow() {
		menuShow(0);
	}
	
	private void menuShow(int tmpMenuId) {
		GlobalVars.APPSTATUS = 11;
		menuId = tmpMenuId;
		menuItemSelected = 0;
		
		switch (menuId) {
			// MainMenu
			case 0:
				menu = new MenuItem[] {
					new MenuItem(1, LangVars.CMD_TREEMENU_WATER, GlobalVars.MENU_IMG_PATH_WATER),
					new MenuItem(2, LangVars.CMD_TREEMENU_EDIT,  GlobalVars.MENU_IMG_PATH_EDIT),
					new MenuItem(3, LangVars.CMD_TREEMENU_POT,   GlobalVars.MENU_IMG_PATH_POT)
				};
				break;		
				
			// Edit
			case 2:
				menu = new MenuItem[] {
					new MenuItem(21, LangVars.CMD_SELBRANCH_CUT,      GlobalVars.MENU_IMG_PATH_EDIT_CUT),
					new MenuItem(22, LangVars.CMD_SELBRANCH_EXACTCUT, GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT),
					new MenuItem(23, LangVars.CMD_SELBRANCH_COLOR,    GlobalVars.MENU_IMG_PATH_EDIT_COLOR),
					new MenuItem(24, LangVars.CMD_SELBRANCH_DUNG,     GlobalVars.MENU_IMG_PATH_EDIT_DUNG)
				};
				break;
			
			case 22:
				menu = new MenuItem[] {
						new MenuItem(221, LangVars.CMD_SELECTED_SEAL,     GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_SEAL),
						new MenuItem(222, LangVars.CMD_SELECTED_DONTSEAL, GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_DONTSEAL)
					};
				break;
		}
		repaint();
	}
	
	public void receiveBack() {
		switch (GlobalVars.APPSTATUS) {
			case 1:
				if (menuId == 0) {
					menuHide();
					parent.receiveFeedback((byte)11);
				} else {
					menuShow(menuId / 10);
				}
				repaint();
				break;
				
			case 4:
				GlobalVars.APPSTATUS = 1;
				repaint();
				break;
		}

	}
	
	private void menuHide() {
		GlobalVars.APPSTATUS = 1;
		menuId = -1;
		menu = null;
		menuItemSelected = 0;
	}
	
	private void menuSelect() {
		switch (menu[menuItemSelected].getMenuId())
		{
			case 1:
				menuHide();
				GlobalVars.APPSTATUS = 4;
				watering();
				break;
				
			case 2:
				menuHide();
				GlobalVars.APPSTATUS = 3;
				edit(false);
				break;
				
			case 21:
				menuHide();
				GlobalVars.APPSTATUS = 3;
				editKill();
				edit(true);
				break;
								
			case 22:
				// keyreceiver????
				menuShow(22);
				GlobalVars.APPSTATUS = 31;
				editExact();
				break;
				
			case 221:
				menuHide();
				GlobalVars.APPSTATUS = 3;
				editCut(true);
				break;
				
			case 222:
				menuHide();
				GlobalVars.APPSTATUS = 3;
				editCut(false);
				break;
								
			case 23:
				menuHide();
				// Coloring -- coming soon ...
				break;
				
			case 24:
				menuHide();
				// Dung -- coming soon ...
				break;
				
			case 3:
				menuHide();
				GlobalVars.APPSTATUS = 5;
				potChange();
				break;
		}
	}
	
	private void watering(){
		keyReceiver = 2;
		canValue = 1;
		canSteps = (potSize + 1) * 3;	
		
		can.setSize((short)canValue);
		
		this.repaint();
	}

	private void wateringAction(){
		water += GlobalVars.POT_SIZE[potSize] * canValue / 100 * 110 / canSteps ;

		if (water > GlobalVars.POT_SIZE[potSize] / 100 * (GlobalVars.POT_HEIGHT[potSize] + 100)) {
			water = GlobalVars.POT_SIZE[potSize] / 100 * (GlobalVars.POT_HEIGHT[potSize] + 100);
		}		
			
//		System.out.println("--- WATERING|WATER: " + GlobalVars.POT_SIZE[potSize] * canValue / 100 * 110 / canSteps + " | " + water + " ---");
		parent.receiveFeedback((byte)11);
		this.repaint();
		//animWatering=false;
	}
	
	private void potChange() {
		keyReceiver = 3;
		this.repaint();
	}
	
	private void potChangeAction() {
		if (water > GlobalVars.POT_SIZE[potSize] / 100 * (GlobalVars.POT_HEIGHT[potSize] + 100)) {
			water = GlobalVars.POT_SIZE[potSize] / 100 * (GlobalVars.POT_HEIGHT[potSize] + 100);
		}		
		parent.receiveFeedback((byte)11);
		this.repaint();
	}
	
	
	public void interval() {
		threadInterval = new Thread(this);
		threadWaiting = false;
		threadInterval.start();
	}

	public void run() {
		
		//TODO: Wurzeln; Krankheit durch Wasser; 
		int counterDraw = 0;
		int supply;

		// anfangsstart
		while ((log != null && GlobalVars.APPSTATUS == 2) && (GlobalVars.COUNTERCHEAT > 0 || ((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / 10000) > 0)) {		
			System.out.println("--- INTERVAL|CHEATER: " + ++GlobalVars.COUNTERINTERVAL + " | " + GlobalVars.COUNTERCHEAT + " ---");
//			System.out.println("--- TIME:" + ((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / 10000) + " ---");
			--GlobalVars.COUNTERCHEAT;
			
			GlobalVars.TIME_STAMP.setTime(GlobalVars.TIME_STAMP.getTime() + GlobalVars.GROWTH_INTERVAL);
			
			supply = Math.min(water, logWaterRequest);
			water -= supply;
			if (!log.grow(supply)) {
				// System.out.println("--- ScreenTree.run: Log_Kill ---");
				log.childKill();
				log = null;
				parent.receiveFeedback((byte)12);
//				System.out.println("--- ScreenTree.run: Log_Killed ---");
			}
			else {
				logWaterRequest = log.getChildWaterRequest();
				System.out.println("--- WATER|REQUEST: " + water + " | " + logWaterRequest + " ---");
			}
			if (++counterDraw == 8) {
					threadWaiting = true;
					break;
			}

		}
//		System.out.println("--- INTERVAL END LOOP ---");
		if (log != null) {
			parent.receiveFeedback((byte)31);
		}
		this.repaint();
		
		if (!threadWaiting && log != null) {
			parent.receiveFeedback((byte)11);
		}

		
	}
	
	
	private void edit(boolean resume) {
		keyReceiver = 1;
		if (!resume) { GlobalVars.ELEMENTEDIT = log; }
		this.repaint();
	}
	
	private void editKill() {
		if (GlobalVars.ELEMENTEDIT == log) {
			if (log != null) {
				log.childKill();
				log = null;
			}
			GlobalVars.COUNTERELEMENT = 0;
			
			int tmpSupply = 10000;
			if (water < tmpSupply) {
				tmpSupply = water;
			}
			water -= tmpSupply;
			log = new Element(null, (short)0, (short)(GlobalVars.DISPLAY_X_WIDTH / 2), GlobalVars.DISPLAY_Y_HEIGHT, tmpSupply);
			logWaterRequest = log.getChildWaterRequest();
		}
		else  {
			Element tmpParent = GlobalVars.ELEMENTEDIT.getRelative((byte)4);
			tmpParent.childKill(GlobalVars.ELEMENTEDIT);
			GlobalVars.ELEMENTEDIT = tmpParent; 
		}
	}
	
	private void editExact() {
		System.out.println("EditExact");
		keyReceiver = 4;
		GlobalVars.EDITEXACTPOS = GlobalVars.SPAWN_LENGTH_INIT/1000;
		repaint();
	}
	
	private void editCut(boolean seal) {
		GlobalVars.ELEMENTEDIT.childKill();
//		GlobalVars.ELEMENTEDIT.setSize();
		// kill children and set to new length
		// set growthStop = seal
	}
	
	public void kill() {
		if (log != null) {
			log.childKill();
			log = null;
		}
	}
	
	// appStatus
	// 0 = init, 1 = standBy, 11 = menuAktiv, 2 = running, 3 = edit, 31 = edit exact, 4 = watering, 5 = potChange
	// keyreceiver
	// 0 = parent, 1 = EditElement, 2 = Can, 3 = Pot, 4 = editExact, 5 = menu
	protected void keyPressed (int keyCode){
//		System.out.println("--- Key Pressed: "+ getKeyName(keyCode) +" ---");
		
		switch (GlobalVars.APPSTATUS) {
		
			// editExact
			case 31:
				switch (getGameAction(keyCode)) {
					case UP:
						GlobalVars.EDITEXACTPOS++;
						this.repaint();
						break;
					case DOWN:
						GlobalVars.EDITEXACTPOS--;
						this.repaint();
						break;
				}
			// kein Break -- läuft durch, da gleichzeitig menü aktiv sein muss
				
			// menu
			case 11:
				switch (getGameAction(keyCode)) {
				
					case LEFT:
						menuItemSelected--;
						if (menuItemSelected < 0) { menuItemSelected = 0; }
						repaint();
						break;
					case RIGHT:
						menuItemSelected++;
						if (menuItemSelected > menu.length - 1) { menuItemSelected = menu.length - 1; }
						repaint();
						break;
					case FIRE:
						menuSelect();
						break;
				}
				System.out.println("menuItemSelected: " + menuItemSelected);
				break;
			// END CASE 11
				
			// edit
			case 3:
				Element tmpElementEdit;
				byte tmpRelative = 0;
				
				switch (getGameAction(keyCode)) {
				
					case LEFT:
						tmpRelative = (byte)1;
						break;
					case UP:
						tmpRelative = (byte)2;
						break;
					case RIGHT:
						tmpRelative = (byte)3;
						break;
					case DOWN:
						tmpRelative = (byte)4;
						break;
					case FIRE:
						menuShow(2);
						break;
						
				}
			
				if (tmpRelative > 0) {
					if ((tmpElementEdit = GlobalVars.ELEMENTEDIT.getRelative(tmpRelative)) != null) {
						GlobalVars.ELEMENTEDIT = tmpElementEdit;
						this.repaint();
					}
				}
				
				break;
			// END CASE 3
			
			// Watering
			case 4:
				
				switch (getGameAction(keyCode)) {
					case LEFT:
					case DOWN:
					
						canValue--;
						if (canValue < 1) {
							canValue = 1;
						}
						
						can.setSize((short)canValue);
						
						this.repaint();
						break;
						
					case RIGHT:
					case UP:
						
						canValue++;
						if (canValue > canSteps) {
							canValue = canSteps;
						}
						
						can.setSize((short)canValue);
						
						this.repaint();
						break;
					
					case FIRE:
						
						wateringAction();
						break;
				}
				break;
			// END CASE 4
				
			// Pot
			case 5:

				switch (getGameAction(keyCode)) {
					case LEFT:
					case DOWN:
					
						potSize--;
						if (potSize < 0) {
							potSize = 0;
						}
						
						this.repaint();
						break;
						
					case RIGHT:
					case UP:
						
						potSize++;
						if (potSize > GlobalVars.POT_SIZE.length - 1) {
							potSize = (byte)(GlobalVars.POT_SIZE.length - 1);
						}
											
						this.repaint();
						break;
					
					case FIRE:
						
						potChangeAction();
						break;
				}
				break;
			// END CASE 5
		
			// Cheats
			case 1:
			
				if (getKeyName(keyCode).equals("4")) {
	//				System.out.println("--- Key Pressed: CHEATER ---");
					GlobalVars.COUNTERCHEAT = 25;
					GlobalVars.APPSTATUS = 2;
					parent.receiveFeedback((byte)10);
					interval();
				}
				else if (getKeyName(keyCode).equals("5")) {
	//				System.out.println("--- Key Pressed: CHEATER ---");
					GlobalVars.COUNTERCHEAT = 50;
					GlobalVars.APPSTATUS = 2;
					parent.receiveFeedback((byte)10);
					interval();
				}
				else if (getKeyName(keyCode).equals("6")) {
	//				System.out.println("--- Key Pressed: CHEATER ---");
					GlobalVars.COUNTERCHEAT = 100;
					GlobalVars.APPSTATUS = 2;
					parent.receiveFeedback((byte)10);
					interval();
				}
				break;
			// END CASE 1
		}
	}
	

	public void writeData(FileIO data) {
		data.writeData(GlobalVars.TIME_STAMP.getTime());
		data.writeData(GlobalVars.COUNTERINTERVAL);
		data.writeData(water);
		data.writeData(potSize);
		if (log != null) { log.writeData(data); }
	}
	
}
