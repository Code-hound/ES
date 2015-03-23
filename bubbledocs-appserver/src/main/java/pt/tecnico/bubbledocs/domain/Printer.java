package pt.tecnico.bubbledocs.domain;

import java.util.Set;

public class Printer{
	 public static String visit (User user) {
		    System.out.println("<USR");

		    System.out.println(" User : " + user.get_username());
		    System.out.println(" Name : " + user.get_name()    );
		    System.out.println(" Pass : " + user.get_password());
		    System.out.println("\\USR>");

		    return "";
	 }

	 public static String visit (SpreadSheet spreadsheet) {
		 	System.out.println("<SPR");

			System.out.print(spreadsheet.getOwner());
		 	System.out.println(" ID   :"  + spreadsheet.get_id()  );
		 	System.out.println(" Date : " + spreadsheet.get_date());
		 	System.out.println(" Size : " + spreadsheet.get_numberRows() + "," + spreadsheet.get_numberColumns());
		 	for (Cell cell : spreadsheet.getCellsSet())
				if(cell.getContent() != null)
					System.out.print(cell);
			System.out.println("\\SPR>");

			return "";
	 }

	 public static String visit (Cell cell) {
		 	System.out.println("<CEL");
		 	
		 	System.out.println(" Loc  : " + cell.get_cellRow() + ";" + cell.get_cellColumn());
		    if(cell.getContent() != null)
	    	    System.out.println(cell.getContent());
			System.out.println("\\CEL>");

		    return "";
	 }

	 public static String visit (Literal literal) {
		    System.out.print(" LIT  : " + literal.get_number());
		 
	    	return "";
	 }

	 public static String visit (Reference reference){
	    	Cell cell = reference.getCell_reference();

	    	System.out.print(" REF  : " + cell.get_cellRow() + ";" + cell.get_cellColumn());
		    //if(cell.getContent() != null)
	    	    //System.out.print(cell.getContent());
	    	
	    	return "";
	 }
	 
	 private static String visit (Function function, String op, String type) {
		    Set<Content> set = function.getArgsSet();
		    Object list[] = set.toArray();

	    	//System.out.print(" " + op + "  : (" + list[0] + type + list[1] + ")");

	    	return "";
	 }

	 public static String visit (ADD function) { return visit(function, "ADD", ","); }
	 public static String visit (SUB function) { return visit(function, "SUB", ","); }
	 public static String visit (MUL function) { return visit(function, "MUL", ","); }
	 public static String visit (DIV function) { return visit(function, "DIV", ","); }
	 public static String visit (AVG function) { return visit(function, "AVG", ":"); }
	 public static String visit (PRD function) { return visit(function, "PRD", ":"); }

}
