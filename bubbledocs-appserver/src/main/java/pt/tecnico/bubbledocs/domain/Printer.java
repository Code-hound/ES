package pt.tecnico.bubbledocs.domain;

public class Printer{
	 public static String visit (User user) {
		    return "UserName: " + user.get_username() + " Name: " + user.get_name()+ " Password: " + user.get_password();
	 }

	 public static String visit (Cell cell) {
		 	String s = "=";
		    if(cell.getContent() != null)
	    	    s += cell.getContent();
		    return s;
	 }

	 public static String visit (Literal literal) {
	    	return "" + literal.getContentValue();
	 }

	 public static String visit (Reference reference){
	    	Cell cell = reference.getCell();
	    	
	    	return cell.get_cellRow() + ";" + cell.get_cellColumn();
	 }
	 
	 public static String visit (Function function) {
	    	Content contents[] = new Content[2];
	    	String op = function.getClass().getName();
	    	String type;
	    	function.getArgsSet().toArray(contents);
	    	
	    	if (op == "AVG" || op == "PRD")
	    		type = ":";
	    	else
	    		type = ",";

	    	return op + "(" + contents[0] + type + contents[1] + ")";
	 }

	 public static String visit (ADD function) { return visit(function); }
	 public static String visit (SUB function) { return visit(function); }
	 public static String visit (MUL function) { return visit(function); }
	 public static String visit (DIV function) { return visit(function); }
	 public static String visit (AVG function) { return visit(function); }
	 public static String visit (PRD function) { return visit(function); }

}
