package pt.tecnico.bubbledocs.domain;

import java.util.Set;

public class Printer{
	 public static String visit (User user) {
		    String s = "";

		    s += " <USR\n";
		    s += "  User : " + user.get_username() + "\n";
		    s += "  Name : " + user.get_name()     + "\n";
		    s += "  Pass : " + user.get_password() + "\n";
		    s += " \\USR>\n";

		    return s;
	 }

	 public static String visit (SpreadSheet spreadsheet) {
		    String s = "";
			User owner = spreadsheet.getOwner();

		 	s += "<SPR\n";
			if (owner != null)
				s += owner.toString();
			else
				s += " User : NULL";
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

	 public static String visit (Cell cell) {
		    String s = "";

		 	s += " <CEL\n";		 	
		 	s += "  Loc  : " + cell.get_cellRow() + ";" + cell.get_cellColumn() + "\n";
		    if(cell.getContent() != null)
	    	    s += "  Cont : " + cell.getContent() + "\n";
	    	else
				s += "NULL\n";
			s += " \\CEL>\n";

		    return s;
	 }

	 public static String visit (Literal literal) {
		    String s = "";

		    s += " LIT (" + literal.getContentValue() + ")";
		 
	    	return s;
	 }

	 public static String visit (Reference reference){
		    String s = "";
	    	Cell cell = reference.getCell_reference();

			if (cell != null)
				s += " REF (" + cell.get_cellRow() + ";" + cell.get_cellColumn() + ")";
			else
				s += " REF (NULL)";
	    	
	    	return s;
	 }

	 private static String visit (BinaryFunction function, String op) {
		    String s = "";
		    String c[] = new String[2];
			int i = 0;

		    for (FunctionArguments content : function.getArgs()) {
				if (content != null)
					c[i] = content.toString();
				else
					c[i] = "NULL";
				i++;
			}
	    	s += " " + op + " (";
	    	s += c[0].toString();
	    	s += ": ";
	    	s += c[1].toString();
	    	s += ")";

	    	return s;
	 }

	 public static String visit (ADD function) { return visit(function, "ADD"); }
	 public static String visit (SUB function) { return visit(function, "SUB"); }
	 public static String visit (MUL function) { return visit(function, "MUL"); }
	 public static String visit (DIV function) { return visit(function, "DIV"); }

	 private static String visit (RangedFunction function, String op) {
		    String s = "";
		    String c[] = new String[2];
			int i = 0;

		    for (Reference content : function.getArgs()) {
				if (content != null)
					c[i] = content.toString();
				else
					c[i] = "NULL";
				i++;
			}
	    	s += " " + op + "  : (";
	    	s += c[0].toString();
	    	s += ":";
	    	c[1].toString();
	    	s += ")";

	    	return s;
	 }

	 public static String visit (AVG function) { return visit(function, "AVG"); }
	 public static String visit (PRD function) { return visit(function, "PRD"); }

}
