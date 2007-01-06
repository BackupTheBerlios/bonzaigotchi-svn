// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-17 updated by chappy
//            added static to all variables
//            added COSINUS_TABLE
// 2006-12-18 updated by Sven
//            altered GROWTH_LENGTH_INC, GROWTH_THICKNESS_INC
// 2006-12-19 updated by chappy
//            altered GROWTH_WATER* TO REQUEST_WATER*

import java.util.Date;
import java.util.Random;


public class GlobalVars {
	
	// 0 = init, 1 = standBy, 2 = running, 3 = edit
	public static int APPSTATUS = 0;
	public static Element ELEMENTEDIT;
	
	public static int COUNTERELEMENT = 0;
	public static int COUNTERINTERVAL = 0;
	
	public final static int POT_WATER_INIT = Integer.MAX_VALUE;
	
	public final static short[] HEALTH_WATER_THRESHOLD = { 10,30,50,60,90,100};
	public final static short[] HEALTH_WATER_INC =       {-10, 1, 3, 5, 6,  7};

	public final static short WATER_SOCIAL_THRESHOLD = 75; //Prozent drüber

	public final static short GROWTH_WATER_MIN = 40;
	public final static short GROWTH_HEALTH_MIN = 70;

	public final static MathFloat GROWTH_LENGTH_INC = new MathFloat(33);
	public final static MathFloat GROWTH_THICKNESS_INC = new MathFloat(2);
	public final static MathFloat GROWTH_THICKNESS_ONLY_INC = new MathFloat(2);

	public final static short SPAWN_LENGTH_MIN = 15;
	public final static int SPAWN_WATER_MIN = 30000;
	public final static short SPAWN_CHANCE = 30;
	public final static int SPAWN_WATER_CHILD = 10000;
	
	public final static short[] REQUEST_WATER_THRESHOLD = {25,50,75,95,100};
	public final static MathFloat[] REQUEST_WATER_FACTOR = {new MathFloat(3000), new MathFloat (2000), new MathFloat(1500), new MathFloat(1100), new MathFloat(1000)};

	public static short DISPLAY_Y_HEIGHT;
	public static short DISPLAY_X_WIDTH;

	public final static int COLOR_ELEMENT_INNER = 0x76E273;
	public final static int COLOR_ELEMENT_OUTER = 0x007809;
	public final static int COLOR_ELEMENT_DRY = 0xE2C48D;
	public final static int COLOR_ELEMENT_DEAD = 0x000000;
	public final static int COLOR_ELEMENT_EDIT = 0xFF0000;
	public final static int COLOR_ELEMENT_EDIT_CHILD = 0xFF9900;
	public final static int COLOR_ELEMENT_WATER_LOW = 0x99BBFF;
	public final static int COLOR_ELEMENT_WATER_HIGH = 0x0000AA;

	public static Date TIME_STAMP;
	public final static short VERSION_ID = 001;
	public final static short SAVE_RECORDSTORE_VERSION = 002;

	// COSINUS_TABLE [-6] = SINUS_TABLE
	public final static MathFloat[] COSINUS_TABLE = { 
			new MathFloat( 1000),
			new MathFloat(  966),
			new MathFloat(  866),
			new MathFloat(  707),
			new MathFloat(  500),
			new MathFloat(  259),
			new MathFloat(    0),
			new MathFloat(- 259),
			new MathFloat(- 500),
			new MathFloat(- 707),
			new MathFloat(- 866),
			new MathFloat(- 966),
			new MathFloat(-1000),
			new MathFloat(- 966),
			new MathFloat(- 866),
			new MathFloat(- 707),
			new MathFloat(- 500),
			new MathFloat(- 259),
			new MathFloat(    0),
			new MathFloat(  259),
			new MathFloat(  500),
			new MathFloat(  707),
			new MathFloat(  866),
			new MathFloat(  966)
	};
	
	public static Random RANDOMGENERATOR = new Random();
}
