// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-17 updated by chappy
//            added static to all variables
// 2007-01-30 changed names because of consistency and various languages by fiips

public class LangVars {
	

	
	public final static String CMD_ALL_BACK = "Back";
	public final static String CMD_ALL_EXIT = "Exit";
	public final static String CMD_ALL_SELECT = "Select";
	
	public final static String MENU_NAME = "Main Menu";
	public final static String CMD_STARTM_RESUME_TREE = "Resume Tree";
	public final static String CMD_STARTM_NEW_TREE = "New Tree";
	public final static String CMD_STARTM_TUTORIAL = "Help / Tutorial";
	public final static String CMD_STARTM_CREDITS = "Show Credits";	
	// if adding one Menu entry increment MAINMENU_LIST_MAX in GlobalVars!!
	
	public final static String CMD_TREEMENU_MENU = "Menu";
	public final static String CMD_TREEMENU_RETURN = "Resume";
	public final static String CMD_TREEMENU_WATER = "Water";
	public final static String CMD_TREEMENU_EDIT  = "Edit";
	public final static String CMD_TREEMENU_POT  = "Change Pot";


	public final static String CMD_SELBRANCH_CUT = "Cut Branch";
	public final static String CMD_SELBRANCH_EXACTCUT = "Exact Cut";
	public final static String CMD_SELBRANCH_COLOR = "Color Branch";
	public final static String CMD_SELBRANCH_DUNG = "Dung Branch";
	
	public final static String CMD_SELECTED_SEAL = "Seal";
	public final static String CMD_SELECTED_DONTSEAL = "Don't Seal";
	
	public final static String CMD_BACKBUTTON = "Hash Sign";
	public static final String DIE_MESSAGE = "Your tree died a slowly and painfull death";
	
	public static final String QUES_SURE = "Are your sure?";
	public static final String ANSWER_YES = "Yes";
	public static final String ANSWER_NO = "No";
	
	//Tutorial
	public static final String TU_HELPTEXT_TITLE = "Hilfe";
	public static final String TU_HELPTEXT = "Herzlich Willkommen im Tutorial von BonzaiGotchi.\n\n"
		+"Hier lernen Sie wie sie ihren Baum anlegen und pflegen k�nnen. \n \n"
		+"Der �Select� Knopf (Mitte der Kreis oder Taste 5) w�hlt immer das Men� aus.\n"
		+"Mit der # - Taste kommen sie immer einen Schritt bzw. Men� zur�ck.\n"
		+"Der Baum w�chst wie jede nat�rliche Pflanze nur wenn man ihn gen�gend gie�t. "
		+"Allerdings kann zuviel gie�en dem BonzaiGotchi Schaden zuf�gen. "
		+"Diesen kann man an den braunen �sten erkennen.\nGespeichert wird der Baum "
		+"automatisch nach jeder getanen Aktion. Es ist somit nicht m�glich T�tigkeiten "
		+"r�ckg�ngig zu machen.";
	
	public static final String TU_LIST_TITLE = "Buttonsliste";
	public final static String[] TU_BUTTONS = {"Gie�t den Baum. Die Gr��e der Kanne kann mit den Pfeiltasten ver�ndert werden.",
		"Im Edit Modus, kann ein spezifischer Ast mit den Navigationstasten ausgew�hlt werden. Der rot eingef�rbte Ast, ist die aktuelle Auswahl die bearbeitet werden kann. Die anders gef�rbten �ste zeigt seine direkten Kinder an. ",
		"Hier kann die Topfgr��e ver�ndert werden. Ein gr��erer Topf kann nat�rlich mehr Wasser fassen. Allerdings wenn einmal Wurzeln gebildet sind, ist es nicht m�glich den Topf zu verkleinern.",
		"Geht zur�ck ins Anfangsmen�, wo diese Hilfe als Men�punkt aufscheint.",
		"Hier wird der komplette Ast mitsamt seinen Kindern abgeschnitten.",
		"Ein roter Strich markiert die exakte Position wo dieser Ast abgeschnitten wird. Dieser kann mit den Navigationstasten ver�ndert werden.",
		"Der abgeschnittene Ast kann danach versiegelt werden und somit keine neuen Kinder bekommen.",
		"K�rzt nur den Ast und l�sst neue Kinder zu.",
		"(not implementet yet). Diese Funktion erm�glicht das Einf�rben des Astes. ",
		"(not implementet yet). Wenn ein Ast einmal krank wird bzw. kein Wasser mehr bekommt, kann mit dieser Auswahl dem Ast zus�tzliche St�rke/Wasser gegeben werden.",
		"Navigiert immer einen Schritt zur�ck. Sowohl in den Men�s als auch au�erhalb davon. (Vom Edit- ins Hauptmen�)"};
	
	public final static String[] TU_BUTTON_NAMES = {"Water","Edit","Change Pot","Exit","Cut Branch","Exact Cut","Seal","Don't Seal","Color Branch","Dung Branch","Hash Sign"};
	
	/* keine Verwendung! Nur Stringarray testing! Hier kommt noch die Funktion zum Auslesen
	 * des lang.conf Datei wegen verschiedener Sprachen
	 * 
	 * public final static String LANG_STARTMENUd[][] = new String[][] {
		new String[] {"cut" -> "schneiden"},
		new String[] {}
	};

	public final static String[] array = new String[] {
			new String[] {"wert", "wert"},
			new String[] {"wert", "wert"}
			};

	 public final static String[][] LANG_STARTMENU=new String[][] {
		 {"gh"		,"sdf"},
		 { "sfsdf"	,"sfsdf"}};*/

}
