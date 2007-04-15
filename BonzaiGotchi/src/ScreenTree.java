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
// 2007-04-10 updated by Sven
//			  added			whatTime() --> returns int[] (int[0]=hour, int[1]=minutes)
//			  added			drawDay()  --> draws position of the sun according to the time of the day + background colour changing
//     		  changed		menuShow()
//			  changed 		menuSelect()
//			  changed 		paint()
//			  added 		getRandom()
//			  added			randomizeStars()
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class ScreenTree extends Canvas implements Runnable {

	// Children
	private Element log;
	
	// Pot
	private byte potSize;
	private byte potSizeChange;
	private int water;
	private int logWaterRequest;
	private Image potChangeImgArrowLeft;
	private Image potChangeImgArrowRight;
	
	// Gauge
	private Can can;
	private int canValue;
	private int canSteps;
	
	private int menuId = -1;
	private MenuItem[] menu;
	private Image menuRand = null;
	Random random = new Random(System.currentTimeMillis());
	
	short[] starsx={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	short[] starsy={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	private int menuItemSelected = 0;
	
	// Thread
	private Thread threadInterval;
	private boolean threadWaiting = false;
	
	//	private boolean animWatering = false;
	
	private ReceiveFeedback parent;
	
	// TODO: When switching ScreenSize (eg: Iam flipping the Screen of my MDA) the Tree is somewhere and the BG is wrong aswell | very low priority
	public ScreenTree(ReceiveFeedback parent) {
		
		this.parent = parent;

		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date();
		GlobalVars.COUNTERINTERVAL = 0;

		water = GlobalVars.POT_WATER_INIT;
		potSize=0;
		
		log = new Element(null, (short)0, (short)(GlobalVars.DISPLAY_X_WIDTH / 2), GlobalVars.DISPLAY_Y_HEIGHT, GlobalVars.SPAWN_WATER_CHILD);	
		
		screenTreeInit();
	}
	
	public ScreenTree(ReceiveFeedback parent, FileIO data) {
		
		this.parent = parent;
				
		// setzten der GlobalVars
		GlobalVars.TIME_STAMP = new Date(data.readDataLong());
		GlobalVars.COUNTERINTERVAL = data.readDataInt();
		
		water = data.readDataInt();
		potSize = data.readDataByte();
		
		log = new Element(null, data);
		
		screenTreeInit();
	}
	
	private void screenTreeInit() {
		
		
		if (GlobalVars.DISPLAY_X_WIDTH == 0) {
			GlobalVars.DISPLAY_X_WIDTH = (short)super.getWidth();
			GlobalVars.DISPLAY_Y_HEIGHT = (short)super.getHeight();
		}
		
		try {
			menuRand = Image.createImage("/rand.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		randomizeStars();
		
		GlobalVars.COUNTERELEMENT = 0;
		GlobalVars.COUNTERCHEAT = 0;
		can = new Can((short)0,(short)(GlobalVars.DISPLAY_X_WIDTH/2),(short)(GlobalVars.DISPLAY_Y_HEIGHT/2),(short)0);
		
		try {
			potChangeImgArrowLeft = Image.createImage("/arrow20.png");
			potChangeImgArrowRight =  Image.createImage("/arrow20right.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logWaterRequest = log.getChildWaterRequest();
	}
	
	
	protected void paint(Graphics g) {
//		System.out.println("--- ScreenTree.paint BEGINN ---");
		boolean paintLeafs = true;
		g.setFont(Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_SMALL));		
		
		
		if (GlobalVars.APPSTATUS != GlobalVars.APPSTATUS_TREEDEAD) {
							
			drawBackground(g);
			
			drawPot(g);
			
			if (GlobalVars.APPSTATUS != GlobalVars.APPSTATUS_MAINMENU) {
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_NORMAL;
				log.draw(g);
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_VOID;
			}
		
			
			if (GlobalVars.ELEMENTEDIT != null) {
				paintLeafs = false;
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_EDIT;
				log.draw(g);
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_VOID;
			}
			
			if (GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_EDITEXACT) {
				paintLeafs = false;
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_SELECTBRANCH;
				log.draw(g);
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_VOID;
			}
					
			if (GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_MENU ||
				GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_EDITEXACT) {
				
				g.setClip(0, 0, GlobalVars.DISPLAY_X_WIDTH, 43);
				g.setColor(0xFFFFFF);
				g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
				g.setColor(0x000000);
				
					
				g.setColor(0x000000);
				g.drawLine(0, 42, GlobalVars.DISPLAY_X_WIDTH, 42);
							
				for (int i = 0; i < menu.length; i++) {			
					if (i == menuItemSelected) {						
						g.setClip(i * 24 + GlobalVars.DISPLAY_X_WIDTH / 2 - menu.length * 12, 2, 24, 24);
						g.drawImage(menuRand, g.getClipX(), g.getClipY(), Graphics.TOP|Graphics.LEFT);
						
						g.setClip(0, 30, GlobalVars.DISPLAY_X_WIDTH, 12);
						g.setColor(0x000000);
						g.drawString(menu[i].getTitle(), GlobalVars.DISPLAY_X_WIDTH / 2, g.getClipY(), Graphics.TOP|Graphics.HCENTER);
						
					}
					
					g.setClip(i * 24 + GlobalVars.DISPLAY_X_WIDTH / 2 - menu.length * 12 + 2, 4, 20, 20);
					menu[i].draw(g);

				}
				g.setClip(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
			}

			if (GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_WATERING) {
				can.draw(g);
			}
		
			if (threadWaiting && GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_RUNNING) {
				g.setColor(0x555555);
				g.drawString("calculating ...", 0, 0, Graphics.TOP|Graphics.LEFT);
				interval();
			}
			if (paintLeafs) {
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_LEAF;
				log.draw(g);
				GlobalVars.PAINTSTATUS = GlobalVars.PAINTSTATUS_VOID;
			}
		}
		else {
			drawBackground(g);
			g.setColor(0x555555);
			g.drawString(LangVars.DIE_MESSAGE, 0,0, Graphics.TOP|Graphics.LEFT);
		}
			
	}
	
	private void drawBackground(Graphics g) {
		
		
		// What a beautiful day...
		
		drawDay(g);
		
	}
	public void randomizeStars()
		{
//		 Randomize the stars
		for(int i=0;i<starsx.length;i++)
		{
		
		starsx[i]=(short)getRandom(GlobalVars.DISPLAY_X_WIDTH);
		starsy[i]=(short)getRandom(GlobalVars.DISPLAY_Y_HEIGHT);
		}
		//
		}
	public void drawDay(Graphics g){
		
		
		//int[] timeh = whatTime();
		int hour=0;//timeh[0];
		int minutes=0;//timeh[1];
		System.out.println(GlobalVars.TIME_STAMP);
		//Wed Apr 11 18:32:49 UTC 2007
		System.out.println(GlobalVars.TIME_STAMP.toString().indexOf(":"));
		hour=Integer.parseInt(GlobalVars.TIME_STAMP.toString().substring(GlobalVars.TIME_STAMP.toString().indexOf(":")-2,GlobalVars.TIME_STAMP.toString().indexOf(":")));
		minutes=Integer.parseInt(GlobalVars.TIME_STAMP.toString().substring(GlobalVars.TIME_STAMP.toString().indexOf(":")+1,GlobalVars.TIME_STAMP.toString().indexOf(":")+3));
		
		System.out.println("Time: "+hour+":"+minutes);
		if(hour>=GlobalVars.MIDDAY&&hour<=GlobalVars.AFTERNOON){
			g.setColor(0xFFFFFF);
			System.out.println("Mittag");
		}
		if(hour>GlobalVars.AFTERNOON&&hour<GlobalVars.DAWN){
		g.setColor(0xFADDE0);
		System.out.println("Nachmittag");
		}
		if(hour>=GlobalVars.DAWN&&hour<GlobalVars.EVENING)
		{
			g.setColor(0xFD5F72);
			System.out.println("D�mmerung");
			}
		if(hour>=GlobalVars.EVENING&&hour<GlobalVars.NIGHT||hour>=GlobalVars.OVERNIGHT&&hour<GlobalVars.DUSK)
		{
			
			g.setColor(0x000000);
			System.out.println("Nacht");
			
			}
		if(hour>=GlobalVars.DUSK&&hour<GlobalVars.MIDDAY)
		{
			g.setColor(0xFBF782);
			System.out.println("Morgen");
			}
		g.fillRect(0, 0, GlobalVars.DISPLAY_X_WIDTH, GlobalVars.DISPLAY_Y_HEIGHT);
		
		g.setColor(0xFFFFFF);
		
		
		for(int i=0;i<20;i++)
			{
			
			g.drawArc(starsx[i], starsy[i], 1, 1, 0, 360);
			}
		
		
	
		
		int xsun=0;
		int ysun=0;
		if(hour>6&&hour<19){
			if(hour<12){
				xsun=(GlobalVars.DISPLAY_X_WIDTH/72)*((hour-6)*60+minutes);
				ysun=(GlobalVars.DISPLAY_Y_HEIGHT-(GlobalVars.DISPLAY_X_WIDTH/72)*xsun);
					}
			if(hour>11){
				xsun=(GlobalVars.DISPLAY_X_WIDTH/72)*((hour-6)*60+minutes);
				ysun=(GlobalVars.DISPLAY_Y_HEIGHT-(GlobalVars.DISPLAY_X_WIDTH/72)*(GlobalVars.DISPLAY_X_WIDTH/72)*360+(GlobalVars.DISPLAY_X_WIDTH/72)*xsun);
				//ysun=(GlobalVars.DISPLAY_Y_HEIGHT-(GlobalVars.DISPLAY_X_WIDTH/72)*xsun);
			}
			//System.out.println("x_width "+GlobalVars.DISPLAY_X_WIDTH);
			//System.out.println("y_heigth "+GlobalVars.DISPLAY_Y_HEIGHT);
			//System.out.println("xsun: "+xsun);
			//System.out.println("ysun: "+ysun);
			g.setColor(0xFDF401);
			if(ysun<0)
				ysun=ysun*(-1);
			if(hour<12)
			g.fillArc(xsun/10,GlobalVars.DISPLAY_Y_HEIGHT-ysun/11, 30, 30, 0, 360);
			//+ysun/50
			if(hour>11)
				g.fillArc(xsun/10, ysun/11, 30, 30, 0, 360);
		}
	}
	public int getRandom(int bound)
		{
		return Math.abs(random.nextInt()%bound);
		
		}
	public int[] whatTime() { 
		java.util.Date date = new java.util.Date();
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		rightNow.setTime(date); 
		String hour = String.valueOf(rightNow.get(java.util.Calendar.HOUR_OF_DAY));
		String min = String.valueOf(rightNow.get(java.util.Calendar.MINUTE)); 
		String sec = String.valueOf(rightNow.get(java.util.Calendar.SECOND)); 
		String time2 = hour;  //+":"+min;//+":" +sec; 
		int[] timeh = {Integer.parseInt(hour),Integer.parseInt(min)};
		return timeh;
		}

//	private void drawWatering(Graphics g) {
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
		
//	}


	private void drawPot(Graphics g){
		//		drawing a pot
		
		byte potSizeDraw = potSize;
		
		if (GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_POTCHANGE) {
			potSizeDraw = potSizeChange;
		}
		
		if (water >= GlobalVars.POT_SIZE[potSizeDraw] && GlobalVars.APPSTATUS != GlobalVars.APPSTATUS_POTCHANGE) {
		
			int overWater = (water - GlobalVars.POT_SIZE[potSizeDraw]) * 100 / GlobalVars.POT_SIZE[potSizeDraw];
			if (overWater > GlobalVars.POT_HEIGHT[potSizeDraw]) {
				overWater = GlobalVars.POT_HEIGHT[potSizeDraw];
			}
			
			g.setColor(GlobalVars.COLOR_POT_WATER);
			for(int i=0; i <= overWater; i++) {
				g.drawLine(
					GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSizeDraw] - i / 2,
					GlobalVars.DISPLAY_Y_HEIGHT - i,
					GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSizeDraw] + i / 2,
					GlobalVars.DISPLAY_Y_HEIGHT - i);
			}
		}
		
		g.setColor(100,50,50);
		for (int i=0; i <= GlobalVars.POT_THICKNESS; i++) {
			g.drawLine(
				GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSizeDraw] + i,
				GlobalVars.DISPLAY_Y_HEIGHT,
				GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSizeDraw] - GlobalVars.POT_HEIGHT[potSizeDraw] /2 + i,
				GlobalVars.DISPLAY_Y_HEIGHT - GlobalVars.POT_HEIGHT[potSizeDraw]);
			
			g.drawLine(
				GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSizeDraw] - i,
				GlobalVars.DISPLAY_Y_HEIGHT,
				GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSizeDraw] + GlobalVars.POT_HEIGHT[potSizeDraw] /2 - i,
				GlobalVars.DISPLAY_Y_HEIGHT - GlobalVars.POT_HEIGHT[potSizeDraw]);			
		}
		
		if (GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_POTCHANGE) {
			g.drawImage(
				potChangeImgArrowLeft,
				GlobalVars.DISPLAY_X_WIDTH / 2 - GlobalVars.POT_WIDTH[potSizeDraw] - 40,
				GlobalVars.DISPLAY_Y_HEIGHT - 30,
				Graphics.TOP|Graphics.LEFT);
			
			g.drawImage(
				potChangeImgArrowRight,
				GlobalVars.DISPLAY_X_WIDTH / 2 + GlobalVars.POT_WIDTH[potSizeDraw] + 20,
				GlobalVars.DISPLAY_Y_HEIGHT - 30,
				Graphics.TOP|Graphics.LEFT);
		}
		
	}

	
	public void menuShow() {
		menuShow(0);
	}
	
	private void menuShow(int tmpMenuId) {
		menuId = tmpMenuId;
		menuItemSelected = 0;
		
		switch (menuId) {
			// MainMenu
			case 0:
				menu = new MenuItem[] {
					new MenuItem(1, LangVars.CMD_TREEMENU_WATER, GlobalVars.MENU_IMG_PATH_WATER),
					new MenuItem(2, LangVars.CMD_TREEMENU_EDIT,  GlobalVars.MENU_IMG_PATH_EDIT),
					new MenuItem(3, LangVars.CMD_TREEMENU_POT,   GlobalVars.MENU_IMG_PATH_POT),
					new MenuItem(4, LangVars.CMD_ALL_EXIT,       GlobalVars.MENU_IMG_PATH_EXIT)
					};
/*				menurand = new MenuItem[] {
						new MenuItem(1, LangVars.CMD_TREEMENU_WATER, GlobalVars.MENU_IMG_PATH_WATER_RAND),
						new MenuItem(2, LangVars.CMD_TREEMENU_EDIT,  GlobalVars.MENU_IMG_PATH_EDIT_RAND),
						new MenuItem(3, LangVars.CMD_TREEMENU_POT,   GlobalVars.MENU_IMG_PATH_POT_RAND),
						new MenuItem(4, LangVars.CMD_ALL_EXIT,       GlobalVars.MENU_IMG_PATH_EXIT_RAND)
						};
*/				break;		
				
			// Edit
			case 2:
				menu = new MenuItem[] {
					new MenuItem(21, LangVars.CMD_SELBRANCH_CUT,      GlobalVars.MENU_IMG_PATH_EDIT_CUT),
					new MenuItem(22, LangVars.CMD_SELBRANCH_EXACTCUT, GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT),
					new MenuItem(23, LangVars.CMD_SELBRANCH_COLOR,    GlobalVars.MENU_IMG_PATH_EDIT_COLOR),
					new MenuItem(24, LangVars.CMD_SELBRANCH_DUNG,     GlobalVars.MENU_IMG_PATH_EDIT_DUNG),
				};
/*				menurand = new MenuItem[] {
						new MenuItem(21, LangVars.CMD_SELBRANCH_CUT,      GlobalVars.MENU_IMG_PATH_EDIT_CUT_RAND),
						new MenuItem(22, LangVars.CMD_SELBRANCH_EXACTCUT, GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_RAND),
						new MenuItem(23, LangVars.CMD_SELBRANCH_COLOR,    GlobalVars.MENU_IMG_PATH_EDIT_COLOR_RAND),
						new MenuItem(24, LangVars.CMD_SELBRANCH_DUNG,     GlobalVars.MENU_IMG_PATH_EDIT_DUNG_RAND),
					};
*/				break;
			
			case 22:
				menu = new MenuItem[] {
					new MenuItem(221, LangVars.CMD_SELECTED_SEAL,     GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_SEAL),
					new MenuItem(222, LangVars.CMD_SELECTED_DONTSEAL, GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_DONTSEAL)
				};
/*				menurand = new MenuItem[] {
						new MenuItem(221, LangVars.CMD_SELECTED_SEAL,     GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_SEAL_RAND),
						new MenuItem(222, LangVars.CMD_SELECTED_DONTSEAL, GlobalVars.MENU_IMG_PATH_EDIT_EXACTCUT_DONTSEAL_RAND)
					};
*/				break;
		}
		parent.receiveFeedback(GlobalVars.APPSTATUS_MENU);
		repaint();
	}
	
	private void menuBack() {
		switch (GlobalVars.APPSTATUS) {
			case GlobalVars.APPSTATUS_MENU:
				switch (menuId) {
					case 0:
						menuKill();
						break;
					case 2:
						edit(true);
						break;
				}
				menuId /= 10;
				break;
			
			case GlobalVars.APPSTATUS_EDIT:
				GlobalVars.ELEMENTEDIT = null;
				menuShow(0);
				break;
			
			case GlobalVars.APPSTATUS_EDITEXACT:
				menuShow(menuId/10);
				
			default:
				menuShow(menuId);
				break;
		}
	}
	
	private void menuKill() {
		menuId = -1;
		menu = null;
		menuItemSelected = 0;
		parent.receiveFeedback(GlobalVars.APPSTATUS_STANDBY);
		repaint();
	}
	
	private void menuSelect() {
		switch (menu[menuItemSelected].getMenuId())
		{
			case 1:
				watering();
				break;
				
			case 2:
				edit(false);
				break;
				
			case 21:
				editKill();
				edit(true);
				break;
								
			case 22:
				menuShow(22);
				editExact();
				break;
				
			case 221:
				editCut(true);
				break;
				
			case 222:
				editCut(false);
				break;
								
			case 23:
				menuKill();
				// Coloring -- coming soon ...
				break;
				
			case 24:
				menuKill();
				// Dung -- coming soon ...
				break;
			case 3:
				potChange();
				break;
				
			case 4:
				parent.receiveFeedback(GlobalVars.APPSTATUS_MAINMENU);
				break;
		}
	}
	
	private void watering(){
		parent.receiveFeedback(GlobalVars.APPSTATUS_WATERING);
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
		parent.receiveFeedback((short)31);
		parent.receiveFeedback(GlobalVars.APPSTATUS_STANDBY);
		this.repaint();
		//animWatering=false;
	}
	
	private void potChange() {
		parent.receiveFeedback(GlobalVars.APPSTATUS_POTCHANGE);
		potSizeChange = potSize;
		this.repaint();
	}
	
	private void potChangeAction() {
		potSize = potSizeChange;
		if (water > GlobalVars.POT_SIZE[potSize] / 100 * (GlobalVars.POT_HEIGHT[potSize] + 100)) {
			water = GlobalVars.POT_SIZE[potSize] / 100 * (GlobalVars.POT_HEIGHT[potSize] + 100);
		}		
		parent.receiveFeedback((short)31);
		parent.receiveFeedback(GlobalVars.APPSTATUS_STANDBY);
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
		while ((log != null && GlobalVars.APPSTATUS == GlobalVars.APPSTATUS_RUNNING) && (GlobalVars.COUNTERCHEAT > 0 || ((new Date().getTime() - GlobalVars.TIME_STAMP.getTime()) / GlobalVars.GROWTH_INTERVAL) > 0)) {		
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
				parent.receiveFeedback(GlobalVars.APPSTATUS_TREEDEAD);
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
			parent.receiveFeedback(GlobalVars.APPSTATUS_STANDBY);
		}

		
	}
	
	
	private void edit(boolean resume) {
		parent.receiveFeedback(GlobalVars.APPSTATUS_EDIT);
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
		parent.receiveFeedback((short)31);
	}
	
	private void editExact() {
		parent.receiveFeedback(GlobalVars.APPSTATUS_EDITEXACT);
		GlobalVars.EDITEXACTPOS = GlobalVars.SPAWN_LENGTH_INIT/1000;
		repaint();
	}
	
	private void editCut(boolean seal) {
		GlobalVars.ELEMENTEDIT.cut(GlobalVars.EDITEXACTPOS, seal);
		parent.receiveFeedback((short)31);
		parent.receiveFeedback(GlobalVars.APPSTATUS_EDIT);
		repaint();
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
	
	protected void keyPressed (int keyCode) {
//		System.out.println("--- Key Pressed: "+ getKeyName(keyCode) +" ---");
		
		boolean checkBack = false;
		
		switch (GlobalVars.APPSTATUS) {
		
			// editExact
			case GlobalVars.APPSTATUS_EDITEXACT:
				checkBack = true;
				switch (getGameAction(keyCode)) {
					case UP:
						if (++GlobalVars.EDITEXACTPOS > GlobalVars.ELEMENTEDIT.getLength()) {
							GlobalVars.EDITEXACTPOS = GlobalVars.ELEMENTEDIT.getLength();
						}
						this.repaint();
						break;
					case DOWN:
						if (--GlobalVars.EDITEXACTPOS < GlobalVars.SPAWN_LENGTH_INIT / 1000) {
							GlobalVars.EDITEXACTPOS = GlobalVars.SPAWN_LENGTH_INIT / 1000;
						}
						this.repaint();
						break;
				}
			// kein Break -- läuft durch, da gleichzeitig menü aktiv sein muss
				
			// menu
			case GlobalVars.APPSTATUS_MENU:
				checkBack = true;
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
			// END CASE MENU
				
			// edit
			case GlobalVars.APPSTATUS_EDIT:
				checkBack = true;
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
			// END CASE EDIT
			
			// Watering
			case GlobalVars.APPSTATUS_WATERING:
				checkBack = true;
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
			// END CASE WATERING
				
			// Pot
			case GlobalVars.APPSTATUS_POTCHANGE:
				checkBack = true;
				switch (getGameAction(keyCode)) {
					case LEFT:
					case DOWN:
					
						potSizeChange--;
						if (potSizeChange < 0) {
							potSizeChange = 0;
						}
						
						this.repaint();
						break;
						
					case RIGHT:
					case UP:
						
						potSizeChange++;
						if (potSizeChange > GlobalVars.POT_SIZE.length - 1) {
							potSizeChange = (byte)(GlobalVars.POT_SIZE.length - 1);
						}
											
						this.repaint();
						break;
					
					case FIRE:
						
						potChangeAction();
						break;
				}
				break;
			// END CASE POT
		
			// Cheats
			case GlobalVars.APPSTATUS_STANDBY:
				
				if (getGameAction(keyCode) == FIRE) {
					menuShow();
				}
			
				else if (keyCode == KEY_NUM4) {
	//				System.out.println("--- Key Pressed: CHEATER ---");
					GlobalVars.COUNTERCHEAT = 25;
					parent.receiveFeedback(GlobalVars.APPSTATUS_RUNNING);
					interval();
				}
				else if (keyCode == KEY_NUM5) {
	//				System.out.println("--- Key Pressed: CHEATER ---");
					GlobalVars.COUNTERCHEAT = 50;
					parent.receiveFeedback(GlobalVars.APPSTATUS_RUNNING);
					interval();
				}
				else if (keyCode == KEY_NUM6) {
	//				System.out.println("--- Key Pressed: CHEATER ---");
					GlobalVars.COUNTERCHEAT = 100;
					parent.receiveFeedback(GlobalVars.APPSTATUS_RUNNING);
					interval();
				}
				break;
			// END CASE CHEATS
		}
		
		if (checkBack) {
			if (keyCode == KEY_POUND) {
				menuBack();
			}
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
