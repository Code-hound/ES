package pt.tecnico.bubbledocs.domain;

import java.util.Set;

public class Getter{
	public static int visit (Cell cell) {
		//try{
		Content c = cell.getContent();
		return 	c.getContentValue();
		//}catch(InvalidValueException){}
	}

	public static int visit (Literal literal) {
		return literal.get_number();
	}

	public static int visit (Reference reference) {
		Cell cell = reference.getCell();
		Content c = cell.getContent();
		return c.getContentValue();
	}

	private static int apply (String op, int i, int next) {
		switch(op) {
			case "+" : return i + next;
			case "-" : return i - next;
			case "*" : return i * next;
			case "/" : if (next == 0)
						  return -9999; //Meter erro
					   return i / next;
			default:   return -9999; //Meter erro
		}
	}

	private static int visit (BinaryFunction function, String op) {
		int i = 0, next;

		for (FunctionArguments content : function.getArgs())
			if (content != null)
				i = apply(op,i,content.getContentValue());

		return	i;
	} 

	public static int visit (ADD function) { return	visit(function, "+"); }
	public static int visit (SUB function) { return	visit(function, "-"); }
	public static int visit (MUL function) { return	visit(function, "*"); }
	public static int visit (DIV function) { return	visit(function, "/"); }

	private static int visit (Set<Reference> contents, String op) {
		int i = 0, next;

		for (Reference content : contents)
			if (content != null)
				i = apply(op,i,content.getContentValue());

		return	i;
	} 

	public static int visit (AVG function) {
		Set<Reference> contents = function.getExpanded();

		return visit(contents, "+") / contents.size();
	}
	public static int visit (PRD function) {
		return visit(function.getExpanded(), "*");
	}
}
