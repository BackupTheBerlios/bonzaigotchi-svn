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
