package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import java.lang.NullPointerException;
import java.lang.Integer;
import java.lang.Boolean;
import java.util.List;
import pt.tecnico.bubbledocs.exception.ImportException;

import org.joda.time.LocalDate;

public class Importer {

	public static void use (SpreadSheet spreadsheet, Element element)
			throws ImportException {
		try {

			Cell newCell;

			try {
				spreadsheet.setId(Integer.parseInt(element.getAttribute("id")
						.getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "id");
			}

			try {
				spreadsheet.setSpreadSheetName(element.getAttribute("name")
						.getName());
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "name");
			}

			try {
				spreadsheet.setCreationDate(new LocalDate(element.getAttribute("date")
						.getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "date");
			}

			try {
				spreadsheet.setNumberRows(Integer.parseInt(element
						.getAttribute("rows").getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "rows");
			}

			try {
				spreadsheet.setNumberColumns(Integer.parseInt(element
						.getAttribute("columns").getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "columns");
			}

			try {
				spreadsheet.setOwnerUsername (element.getAttribute("ownerUsername").getName());
			} catch (NullPointerException e) { 
				throw new ImportException(element.getName(), "ownerUsername");
			}

			for (Element newContent : element.getChildren("Cell")) {
				try {

					newCell = new Cell(spreadsheet, newContent);
					for (Cell cell : spreadsheet.getCellsSet()) {
						if (cell.getCellRow() == newCell.getCellRow()
								&& cell.getCellColumn() == newCell
										.getCellColumn()) {
							cell = newCell;
							break;
						}
					}

				} catch (NullPointerException e) {
					throw new ImportException("Cell");
				}
			}

		} catch (NullPointerException e) {
			throw new ImportException("SpreadSheet");
		}
	}

	public static void use(Cell cell, Element element, SpreadSheet sheet)
			throws ImportException {
		try {

			List<Element> content_list = element.getChildren();
			Element content = content_list.get(0);

			try {
				cell.setCellRow(Integer.parseInt(element.getAttribute("row")
						.getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "row");
			}

			try {
				cell.setCellColumn(Integer.parseInt(element.getAttribute(
						"column").getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "column");
			}

			try {
				cell.setProtect(Boolean.parseBoolean(element.getAttribute(
						"protect").getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "protect");
			}

			switch (content.getName()) {
			case "Literal":
				cell.setContent(new Literal(sheet, content));
				break;
			case "Reference":
				cell.setContent(new Reference(sheet, content));
				break;
			case "ADD":
				cell.setContent(new ADD(sheet, content));
				break;
			case "SUB":
				cell.setContent(new SUB(sheet, content));
				break;
			case "MUL":
				cell.setContent(new MUL(sheet, content));
				break;
			case "DIV":
				cell.setContent(new DIV(sheet, content));
				break;
			case "AVG":
				cell.setContent(new AVG(sheet, content));
				break;
			case "PRD":
				cell.setContent(new PRD(sheet, content));
				break;
			default:
				throw new ImportException("Content");
			}

		} catch (NullPointerException e) {
			throw new ImportException("Cell");
		}
	}

	public static void use(Literal literal, Element element, SpreadSheet sheet)
			throws ImportException {
		try {

			try {
				literal.setNumber(Integer.parseInt(element.getAttribute(
						"number").getName()));
			} catch (NullPointerException e) {
				throw new ImportException(element.getName(), "number");
			}

		} catch (NullPointerException e) {
			throw new ImportException("Literal");
		}
    }

    public static void use (Reference reference, Element element, SpreadSheet sheet)
    		throws ImportException {
		try {

			try {

				Element content = element.getChild("Cell");

				int row    = Integer.parseInt(content.getAttribute("row").getName());
				int column = Integer.parseInt(content.getAttribute("column").getName());

				for (Cell cell : sheet.getCellsSet()) {
					if (cell.getCellRow() == row && cell.getCellColumn() == column) {
						reference.setCell(cell);
						break;
					}
				}

			} catch (NullPointerException e) {
				throw new ImportException("Cell");
			}

		} catch (NullPointerException e) {
			throw new ImportException("Reference");
		}
	}

	private static void use(BinaryFunction function, Element element, SpreadSheet sheet, String type)
			throws ImportException {
		try {

			for (Element content : element.getChildren()) {
				switch (content.getName()) {
				case "Reference":
					function.addArgs(new Reference(sheet, content));
					break;
				case "Literal":
					function.addArgs(new Literal(sheet, content));
					break;
				default:
					throw new ImportException("FunctionArguments");
				}
			}

		} catch (NullPointerException e) {
			throw new ImportException(type);
		}
	}

	public static void use(ADD function, Element element, SpreadSheet sheet)
			throws ImportException {
		use(function, element, sheet, "ADD");
	}

	public static void use(SUB function, Element element, SpreadSheet sheet)
			throws ImportException {
		use(function, element, sheet, "SUB");
	}

	public static void use(MUL function, Element element, SpreadSheet sheet)
			throws ImportException {
		use(function, element, sheet, "MUL");
	}

	public static void use(DIV function, Element element, SpreadSheet sheet)
			throws ImportException {
		use(function, element, sheet, "DIV");
	}

	private static void use(RangedFunction function, Element element,
			SpreadSheet sheet, String type) throws ImportException {
		try {

			for (Element content : element.getChildren()) {
				try {
					function.addArgs(new Reference(sheet, content));
				} catch (NullPointerException e) {
					throw new ImportException("Reference");
				}
			}

		} catch (NullPointerException e) {
			throw new ImportException(type);
		}
	}

	public static void use(AVG function, Element element, SpreadSheet sheet)
			throws ImportException {
		use(function, element, sheet, "AVG");
	}

	public static void use(PRD function, Element element, SpreadSheet sheet)
			throws ImportException {
		use(function, element, sheet, "PRD");
	}
}
