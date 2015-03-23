package pt.tecnico.bubbledocs.domain;

import java.util.*;

public class Getter{
	public static int visit (Cell cell) {
		//try{
		Content c=cell.getContent();
		return 	c.getContentValue();
		//}catch(InvalidValueException){}
	}

	public static int visit (Literal literal) {
		return literal.get_number();
	}

	public static int visit (Reference reference) {
		Cell c = reference.getCell();
		Content cont= c.getContent();
		return cont.getContentValue();
	}

	private static int apply (String op, Iterator<Content> contents) {
		int a = contents.next().getContentValue();
		contents.remove();

		if (!contents.hasNext())
			return a;

		int b = apply(op, contents);
		switch(op) {
			case "+" : return a+b;
			case "-" : return a-b;
			case "*" : return a*b;
			case "/" : if (b == 0)
                          return 0;
			           return a/b;
			default:   return 0;
		}
	}

	private static int visit (BinaryFunction function, String op) {
		return	apply(op, function.getArgsSet().iterator());
	} 

	public static int visit (ADD function) { return	visit(function, "+"); }
	public static int visit (SUB function) { return	visit(function, "-"); }
	public static int visit (MUL function) { return	visit(function, "*"); }
	public static int visit (DIV function) { return	visit(function, "/"); }

	public static int visit (AVG function) {
		int value=0;
		//FIX ME
		return value;
	}	 

	public static int visit (PRD function) {
		int value=0;
		//FIX ME
		return value;
	}
}
