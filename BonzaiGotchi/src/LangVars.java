// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-17 updated by chappy
//            added static to all variables
// 2007-01-30 changed names because of consistency and various languages by fiips

public class LangVars {
	

	
	public final static String CMD_ALL_BACK = "Back";
	public final static String CMD_ALL_EXIT = "Exit";
	public final static String CMD_ALL_SELECT = "Select";
	public final static String CMD_ALL_NA = " - not avaible";
	
	public final static String MENU_NAME = "Main Menu";
	public final static String CMD_STARTM_RESUME_TREE = "Resume Tree";
	public final static String CMD_STARTM_NEW_TREE = "New Tree";
	public final static String CMD_STARTM_TUTORIAL = "Help / Tutorial";
	public final static String CMD_STARTM_CREDITS = "Credits";	
	// if adding one Menu entry increment MAINMENU_LIST_MAX in GlobalVars!!
	
	public final static String CMD_TREEMENU_MENU = "Menu";
	public final static String CMD_TREEMENU_RETURN = "Resume";
	public final static String CMD_TREEMENU_WATER = "Water";
	public final static String CMD_TREEMENU_EDIT  = "Edit";
	public final static String CMD_TREEMENU_POT  = "Change Pot";
	public final static String CMD_TREEMENU_DUNG = "Dung Tree";

	public final static String CMD_SELBRANCH_CUT = "Cut Branch";
	public final static String CMD_SELBRANCH_EXACTCUT = "Exact Cut";
	public final static String CMD_SELBRANCH_COLOR = "Color Branch";
		
	public final static String CMD_SELECTED_SEAL = "Seal";
	public final static String CMD_SELECTED_DONTSEAL = "Don't Seal";
	
	public final static String CMD_BACKBUTTON = "Hash Sign Button";
	public final static String DIE_MESSAGE = "Your tree died a slowly and painfull death";
	
	public final static String QUES_SURE = "Are your sure?";
	public final static String ANSWER_YES = "Yes";
	public final static String ANSWER_NO = "No";
	
	
	//Credita
	public final static String CR_TITLE = "Credits";
	public final static String CR_TEXT =
		 "\nPhilipp Losbichler:\n"
		+"   - Project Lead\n"
		+"   - Programmer\n\n"
		+"Andreas Tschabuschnig:\n"
		+"   - Programm Lead\n\n"
		+"Johannes Greschitz:\n"
		+"   - Programmer\n"
		+"   - Graphics\n\n"
		+"Special Thanks to:\n"
		+"Michael Cramer:\n"
		+"- Idea / Bugfixing";
	
	//Tutorial
	public final static String TU_HELPTEXT_TITLE = "Hilfe";
	public final static String TU_HELPTEXT = "Herzlich Willkommen im Tutorial von BonzaiGotchi.\n\n"
		+"Hier lernen Sie wie sie ihren Baum anlegen und pflegen koennen.\n\n"
		+"Der �Select� Knopf (Mitte der Kreis oder Taste 5) waehlt immer das Menue aus.\n"
		+"Mit der # - Taste kommen sie immer einen Schritt bzw. Menue zurueck.\n"
		+"Der Baum waechst wie jede natuerliche Pflanze nur wenn man ihn genuegend gieszt. "
		+"Allerdings kann zuviel gieszen dem BonzaiGotchi Schaden zufuegen. Diesen kann man "
		+"an den braunen Aesten erkennen. \n"
		+"Gespeichert wird der Baum automatisch nach jeder getanen Aktion. Es ist somit "
		+"nicht moeglich Taetigkeiten rueckgaengig zu machen.";
	
	public final static String TU_LIST_TITLE = "Buttonsliste";
	public final static String[] TU_BUTTONS = {"Gieszt den Baum. Die Groesze der Kanne kann mit den Pfeiltasten veraendert werden.",
		"Im Edit Modus, kann ein spezifischer Ast mit den Navigationstasten ausgewaehlt werden. Der rot eingefaerbte Ast, ist die aktuelle Auswahl die bearbeitet werden kann. Die anders gefaerbten AEste zeigt seine direkten Kinder an. ",
		"Hier kann die Topfgroesze veraendert werden. Ein groeszerer Topf kann natuerlich mehr Wasser fassen. Allerdings wenn einmal Wurzeln gebildet sind, ist es nicht moeglich den Topf zu verkleinern.",
		"Kann nur selten verwendet werden, gibt dem Baum Staerke und erhoehtes Wachstum",
		"Geht zurueck ins Anfangsmenue, wo diese Hilfe als Menuepunkt aufscheint.",
		"Hier wird der komplette Ast mitsamt seinen Kindern abgeschnitten.",
		"Ein roter Strich markiert die exakte Position wo dieser Ast abgeschnitten wird. Dieser kann mit den Navigationstasten veraendert werden.",
		"Der abgeschnittene Ast wird danach versiegelt und kann somit nicht mehr wachsen und keine neuen Kinder bekommen.",
		"Kuerzt nur den Ast und laesst neue Kinder zu.",
		"Diese Funktion ermoeglicht das Einfaerben des Astes. ",
		"Navigiert immer einen Schritt zurueck. Sowohl in den Menues als auch auszerhalb davon. (Vom Edit- ins Hauptmenue)"};
	
	public final static String[] TU_BUTTON_NAMES = {"Water","Edit","Change Pot","Dung Tree","Exit","Cut Branch","Exact Cut","Seal","Don't Seal","Color Branch","Hash Sign"};
	
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
