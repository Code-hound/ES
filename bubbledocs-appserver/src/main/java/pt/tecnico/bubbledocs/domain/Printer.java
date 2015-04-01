package pt.tecnico.bubbledocs.domain;

import java.lang.NullPointerException;

public class Printer {
	public static String use(User user) {
		String s = "";

		s += " <USR\n";
		s += "  User : " + user.getUsername() + "\n";
		s += "  Name : " + user.getName() + "\n";
		s += "  Pass : " + user.getPassword() + "\n";
		s += " \\USR>\n";

		return s;
	}

	public static String use(SpreadSheet spreadsheet) {
		String s = "";

		s += "<SPR\n";
		s += " OwnerUsername : " + spreadsheet.getOwnerUsername();
		s += " ID   : " + spreadsheet.getId() + "\n";
		s += " Name : " + spreadsheet.getSpreadSheetName();
		s += " Date : " + spreadsheet.getCreationDate() + "\n";
		s += " Size : " + spreadsheet.getNumberRows() + "," + spreadsheet.getNumberColumns() + "\n";
		for (Cell cell : spreadsheet.getCellsSet())
			if (cell.getContent() != null)
				s += cell.toString();
		s += "\\SPR>\n";

		return s;
	}

	public static String use(Cell cell) {
		String s = "";

		s += " <CEL\n";
		s += "  Loc  : " + cell.getCellRow() + ";" + cell.getCellColumn()
				+ "\n";
		try {
			s += "  Cont : " + cell.getContent() + "\n";
		} catch (NullPointerException e) {
			s += "NULL\n";
		}
		s += " \\CEL>\n";

		return s;
	}

	public static String use(Literal literal) {
		String s = "";

		s += " LIT (" + literal.getContentValue() + ")";

		return s;
	}

	public static String use(Reference reference) {
		String s = "";
		Cell cell = reference.getCellReference();

		try {
			s += " REF (" + cell.getCellRow() + ";" + cell.getCellColumn()
					+ ")";
		} catch (NullPointerException e) {
			s += " REF (NULL)";
		}

		return s;
	}

	private static String use(BinaryFunction function, String op) {
		String s = "";
		String c[] = new String[2];
		int i = 0;

		for (FunctionArguments content : function.getArgsSet()) {
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

	public static String use(ADD function) {
		return use(function, "ADD");
	}

	public static String use(SUB function) {
		return use(function, "SUB");
	}

	public static String use(MUL function) {
		return use(function, "MUL");
	}

	public static String use(DIV function) {
		return use(function, "DIV");
	}

	private static String use(RangedFunction function, String op) {
		String s = "";
		String c[] = new String[2];
		int i = 0;

		for (Reference content : function.getArgsSet()) {
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

	public static String use(AVG function) {
		return use(function, "AVG");
	}

	public static String use(PRD function) {
		return use(function, "PRD");
	}

}
