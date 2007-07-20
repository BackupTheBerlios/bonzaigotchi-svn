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
	public final static String CMD_STARTM_ADMIN = "Admin Backups";
	// if adding one Menu entry increment MAINMENU_LIST_MAX in GlobalVars!!
	
	public final static String CMD_TREEMENU_MENU = "Menu";
	public final static String CMD_TREEMENU_RETURN = "Resume";
	public final static String CMD_TREEMENU_WATER = "Water";
	public final static String CMD_TREEMENU_EDIT  = "Edit";
	public final static String CMD_TREEMENU_POT  = "Change Pot";
	public final static String CMD_TREEMENU_DUNG = "Dung Tree";
	public final static String CMD_TREEMENU_FREEZE = "Freeze Tree";
	public final static String CMD_TREEMENU_FREEZE_UNDO = "Undo Freeze Tree";

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
	
	//Tutorial english
	public final static String TU_HELPTEXT_TITLE = "Help";
	public final static String TU_HELPTEXT = "\nWelcome to the tutorial of BonzaiGotchi!\n\n"
		+"This shows you how to plant and take care of your tree.\n\n"
		+"The SELECT Button (or Number 5) retrieve the menu.\n"
		+"You can always step back with the # - Button.\n"
		+"The tree grows like any plant with enough water. "
		+"But too much water can hurt him. The brown branches indicates a harried tree. \n"
		+"BonzaiGotchi saves the tree automatically after any activity. It is not possible"
		+"to undo any actions!";
	
	public final static String TU_LIST_TITLE = "List of Buttons";
	public final static String[] TU_BUTTONS = {"Pour the tree. The size of the canister can be chanced while pressing the arrow keys (left and right).",
		"You can select the a specific branch while navigating with the arrow keys. The red colored branch is the selected one, which can be manipulated. The magenta/yellow/blue ones show the immediate children.",
		"To change the potsize press the left and right arrow key. You can scale up the pot to increase the water capacity. After scaling up it is impossible to undo this because of the roots.",
		"Can be used rarly. This gives the tree strengh and higher growth.",
		"Here you can leave the tree screen. Goes back to root menue.",
		"If you activate this button the selected branch and all of his children will be killed.",
		"A small line shows you the exact position where you can cut down the tree. The navigation works with the arrow keys up and down.",
		"The exact cutted branch can be sealed and refuse new children.",
		"Chop the branch and allow new children.",
		"Here you can give the tree (and branches) your individual colors.",
		"This button navigates always one step back. (From edit to menu, etc.)"};

	
	//Tutorial deutsch
//	public final static String TU_HELPTEXT_TITLE = "Hilfe";
//	public final static String TU_HELPTEXT = "Herzlich Willkommen im Tutorial von BonzaiGotchi.\n\n"
//		+"Hier lernen Sie wie sie ihren Baum anlegen und pflegen koennen.\n\n"
//		+"Der �Select� Knopf (Mitte der Kreis oder Taste 5) waehlt immer das Menue aus.\n"
//		+"Mit der # - Taste kommen sie immer einen Schritt bzw. Menue zurueck.\n"
//		+"Der Baum waechst wie jede natuerliche Pflanze nur wenn man ihn genuegend gieszt. "
//		+"Allerdings kann zuviel gieszen dem BonzaiGotchi Schaden zufuegen. Diesen kann man "
//		+"an den braunen Aesten erkennen. \n"
//		+"Gespeichert wird der Baum automatisch nach jeder getanen Aktion. Es ist somit "
//		+"nicht moeglich Taetigkeiten rueckgaengig zu machen.";
//	
//	public final static String TU_LIST_TITLE = "Buttonsliste";
//	public final static String[] TU_BUTTONS = {"Gieszt den Baum. Die Groesze der Kanne kann mit den Pfeiltasten veraendert werden.",
//		"Im Edit Modus, kann ein spezifischer Ast mit den Navigationstasten ausgewaehlt werden. Der rot eingefaerbte Ast, ist die aktuelle Auswahl die bearbeitet werden kann. Die anders gefaerbten AEste zeigt seine direkten Kinder an. ",
//		"Hier kann die Topfgroesze veraendert werden. Ein groeszerer Topf kann natuerlich mehr Wasser fassen. Allerdings wenn einmal Wurzeln gebildet sind, ist es nicht moeglich den Topf zu verkleinern.",
//		"Kann nur selten verwendet werden, gibt dem Baum Staerke und erhoehtes Wachstum",
//		"Geht zurueck ins Anfangsmenue, wo diese Hilfe als Menuepunkt aufscheint.",
//		"Hier wird der komplette Ast mitsamt seinen Kindern abgeschnitten.",
//		"Ein roter Strich markiert die exakte Position wo dieser Ast abgeschnitten wird. Dieser kann mit den Navigationstasten veraendert werden.",
//		"Der abgeschnittene Ast wird danach versiegelt und kann somit nicht mehr wachsen und keine neuen Kinder bekommen.",
//		"Kuerzt nur den Ast und laesst neue Kinder zu.",
//		"Diese Funktion ermoeglicht das Einfaerben des Astes. ",
//		"Navigiert immer einen Schritt zurueck. Sowohl in den Menues als auch auszerhalb davon. (Vom Edit- ins Hauptmenue)"};

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
