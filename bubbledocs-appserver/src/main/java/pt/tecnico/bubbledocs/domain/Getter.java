package pt.tecnico.bubbledocs.domain;

import java.util.Set;

public class Getter {
	public static int use(Cell cell) {
		// try{
		Content c = cell.getContent();
		return c.getContentValue();
		// }catch(InvalidValueException){}
	}

	public static int use(Literal literal) {
		return literal.getNumber();
	}

	public static int use(Reference reference) {
		Cell cell = reference.getCellReference();
		Content c = cell.getContent();
		return c.getContentValue();
	}

	private static int apply(String op, int i, int next) {
		switch (op) {
		case "+":
			return i + next;
		case "-":
			return i - next;
		case "*":
			return i * next;
		case "/":
			return i / next;
		default:
			return 0;
		}
	}

	private static int use(BinaryFunction function, String op) {
		int i = 0;

		for (FunctionArguments content : function.getArgsSet())
			if (content != null)
				i = apply(op, i, content.getContentValue());

		return i;
	}

	public static int use(ADD function) {
		return use(function, "+");
	}

	public static int use(SUB function) {
		return use(function, "-");
	}

	public static int use(MUL function) {
		return use(function, "*");
	}

	public static int use(DIV function) {
		return use(function, "/");
	}

	private static int use(Set<Reference> contents, String op) {
		int i = 0;

		for (Reference content : contents)
			if (content != null)
				i = apply(op, i, content.getContentValue());

		return i;
	}

	public static int use(AVG function) {
		Set<Reference> contents = function.getExpanded();

		return use(contents, "+") / contents.size();
	}

	public static int use(PRD function) {
		return use(function.getExpanded(), "*");
	}
}
