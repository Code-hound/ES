package pt.tecnico.bubbledocs.domain;

import java.util.Set;
import pt.tecnico.bubbledocs.exception.InvalidValueException;;

public class Getter {
	public static int use(Cell cell) throws InvalidValueException {

		Content c = cell.getContent();

		return c.getContentValue();

	}

	public static int use(Literal literal) throws InvalidValueException {
		return literal.getNumber();
	}

	public static int use(Reference reference) throws InvalidValueException {

		Cell cell = reference.getCellReference();
		
		Content c = cell.getContent();
		
		if (c == null)
			throw new InvalidValueException();

		return c.getContentValue();

	}

	private static int apply(String op, int i, int next) throws InvalidValueException {

		switch (op) {
		case "+":
			return i + next;
		case "-":
			return i - next;
		case "*":
			return i * next;
		case "/":
			if (next == 0)
				throw new InvalidValueException();
			return i / next;
		default:
			throw new InvalidValueException();
		}

	}

	private static int use(BinaryFunction function, String op) throws InvalidValueException {

		Integer i = null;

		for (FunctionArguments content : function.getArgsSet()) {
			if (content == null)
				throw new InvalidValueException();
			
			if (i == null) {
				i = content.getContentValue();
			} else {
				i = apply(op, i, content.getContentValue());
			}
		}

		return i;

	}

	public static int use(ADD function) throws InvalidValueException {
		return use(function, "+");
	}

	public static int use(SUB function) throws InvalidValueException {
		return use(function, "-");
	}

	public static int use(MUL function) throws InvalidValueException {
		return use(function, "*");
	}

	public static int use(DIV function) throws InvalidValueException {
		return use(function, "/");
	}

	private static int use(Set<Reference> contents, String op) throws InvalidValueException {
		int i = 0;

		for (Reference content : contents) {
			if (content == null)
				throw new InvalidValueException();

			i = apply(op, i, content.getContentValue());
		}

		return i;
	}

	public static int use(AVG function) throws InvalidValueException {

		Set<Reference> contents = function.getExpanded();

		return use(contents, "+") / contents.size();

	}

	public static int use(PRD function) throws InvalidValueException {
		return use(function.getExpanded(), "*");
	}
}
