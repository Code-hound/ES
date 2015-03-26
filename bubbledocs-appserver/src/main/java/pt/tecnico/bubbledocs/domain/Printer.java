package pt.tecnico.bubbledocs.domain;

import java.util.Set;
import java.lang.NullPointerException;

public class Printer{
	 public static String use (User user) {
		    String s = "";

		    s += " <USR\n";
		    s += "  User : " + user.get_username() + "\n";
		    s += "  Name : " + user.get_name()     + "\n";
		    s += "  Pass : " + user.get_password() + "\n";
		    s += " \\USR>\n";

		    return s;
	 }

	 public static String use (SpreadSheet spreadsheet) {
		    String s = "";
			User owner = spreadsheet.getOwner();

		 	s += "<SPR\n";
			try {
				s += owner.toString();
			} catch (NullPointerException e) {
				s += " User : NULL";
			}
		 	s += " ID   : " + spreadsheet.get_id()   + "\n";
		 	s += " Name : " + spreadsheet.get_spreadSheetName();
		 	s += " Date : " + spreadsheet.get_date() + "\n";
		 	s += " Size : " + spreadsheet.get_numberRows() + "," + spreadsheet.get_numberColumns() + "\n";
		 	for (Cell cell : spreadsheet.getCellsSet())
				if(cell.getContent() != null)
					s += cell.toString();
			s += "\\SPR>\n";

			return s;
	 }

	 public static String use (Cell cell) {
		    String s = "";

		 	s += " <CEL\n";		 	
		 	s += "  Loc  : " + cell.get_cellRow() + ";" + cell.get_cellColumn() + "\n";
		    try {
	    	    s += "  Cont : " + cell.getContent() + "\n";
	    	} catch (NullPointerException e) {
				s += "NULL\n";
			}
			s += " \\CEL>\n";

		    return s;
	 }

	 public static String use (Literal literal) {
		    String s = "";

		    s += " LIT (" + literal.getContentValue() + ")";
		 
	    	return s;
	 }

	 public static String use (Reference reference){
		    String s = "";
	    	Cell cell = reference.getCell();

			try {
				s += " REF (" + cell.get_cellRow() + ";" + cell.get_cellColumn() + ")";
			} catch (NullPointerException e) {
				s += " REF (NULL)";
			}
	    	
	    	return s;
	 }

	 private static String use (BinaryFunction function, String op) {
		    String s = "";
		    String c[] = new String[2];
			int i = 0;

		    for (FunctionArguments content : function.getArgs()) {
				try {
					c[i] = content.toString();
				} catch (NullPointerException e) {
					c[i] = "NULL";
				}
				i++;
			}
	    	s += " " + op + " (";
	    	s += c[0].toString();
	    	s += ": ";
	    	s += c[1].toString();
	    	s += ")";

	    	return s;
	 }

	 public static String use (ADD function) { return use(function, "ADD"); }
	 public static String use (SUB function) { return use(function, "SUB"); }
	 public static String use (MUL function) { return use(function, "MUL"); }
	 public static String use (DIV function) { return use(function, "DIV"); }

	 private static String use (RangedFunction function, String op) {
		    String s = "";
		    String c[] = new String[2];
			int i = 0;

		    for (Reference content : function.getArgs()) {
				try {
					c[i] = content.toString();
				} catch (NullPointerException e) {
					c[i] = "NULL";
				}
				i++;
			}
	    	s += " " + op + "  : (";
	    	s += c[0].toString();
	    	s += ":";
	    	c[1].toString();
	    	s += ")";

	    	return s;
	 }

	 public static String use (AVG function) { return use(function, "AVG"); }
	 public static String use (PRD function) { return use(function, "PRD"); }

}
