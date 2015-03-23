package pt.tecnico.bubbledocs.domain;

public class Printor{
	 public static String visit (User user) {
		    String s = "[USR]\n";

		    s += " UserName: " + user.get_username() + "\n";
		    s += " Name: "     + user.get_name()     + "\n";
		    s += " Password: " + user.get_password() + "\n";
		    s += "[\\USR]\n";

		    return s;
	 }

	 public static String visit (SpreadSheet spreadsheet) {
		 	String s = "[SPR]\n";

		 	s += " ID:"    + spreadsheet.get_id()   + "\n";
		 	s += " Date: " + spreadsheet.get_date() + "\n";
		 	s += " Size: " + spreadsheet.get_numberRows() + "," + spreadsheet.get_numberColumns() + "\n";
		 	for (Cell cell : spreadsheet.getCellsSet())
				if(cell.getContent() != null)
					s += "" + cell + "\n";
			s += "[\\SPR]\n";

			return s;
	 }

	 public static String visit (Cell cell) {
		 	String s = "[CEL]\n";
		 	
		 	s += " " + cell.get_cellRow() + ";" + cell.get_cellColumn() + "\n";
		    if(cell.getContent() != null)
	    	    s += "" + cell.getContent() + "\n";

		    return s;
	 }

	 public static String visit (Literal literal) {
	    	return String.valueOf(literal.getContentValue());
	 }

	 public static String visit (Reference reference){
	    	Cell cell = reference.getCell_reference();
	    	
	    	return cell.get_cellRow() + ";" + cell.get_cellColumn();
	 }
	 
	 public static String visit (Function function, String op, String type) {
	    	Content contents[] = new Content[2];

	    	function.getArgsSet().toArray(contents);

	    	return op + "(" + contents[0] + type + contents[1] + ")";
	 }

	 public static String visit (ADD function) { return visit(function, "[ADD]", ","); }
	 public static String visit (SUB function) { return visit(function, "[SUB]", ","); }
	 public static String visit (MUL function) { return visit(function, "[MUL]", ","); }
	 public static String visit (DIV function) { return visit(function, "[DIV]", ","); }
	 public static String visit (AVG function) { return visit(function, "[AVG]", ":"); }
	 public static String visit (PRD function) { return visit(function, "[PRD]", ":"); }

}
