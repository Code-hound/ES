package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import java.lang.NullPointerException;
import java.lang.Integer;
import java.lang.Boolean;
import java.util.List;

import org.joda.time.LocalDate;

public class Importer {

	public static void use (SpreadSheet spreadsheet, Element element) {

		Cell newCell;

		spreadsheet.setId(Integer.parseInt(element.getAttribute("id")
					.getValue()));
		spreadsheet.setSpreadSheetName(element.getAttribute("spreadSheetName")
					.getValue());
		spreadsheet.setCreationDate(new LocalDate(element.getAttribute("creationDate")
					.getValue()));
		spreadsheet.setNumberRows(Integer.parseInt(element
					.getAttribute("numberRows").getValue()));
		spreadsheet.setNumberColumns(Integer.parseInt(element
					.getAttribute("numberColumns").getValue()));
		spreadsheet.setOwnerUsername (element.getAttribute("ownerUsername").getValue());
		
		for (Element newContent : element.getChildren("Cell")) {
			newCell = new Cell(spreadsheet, newContent);
			for (Cell cell : spreadsheet.getCellsSet()) {
				if (cell.getCellRow() == newCell.getCellRow()
						&& cell.getCellColumn() == newCell
								.getCellColumn()) {
					cell = newCell;
					break;
				}
			}
		}
	}

	public static void use(Cell cell, Element element, SpreadSheet sheet) {

		List<Element> content_list = element.getChildren();
		Element content = content_list.get(0);

		cell.setCellRow(Integer.parseInt(element.getAttribute("cellRow")
					.getValue()));
		cell.setCellColumn(Integer.parseInt(element.getAttribute(
				"cellColumn").getValue()));
		cell.setProtect(Boolean.parseBoolean(element.getAttribute(
					"protect").getValue()));

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
			//throw new ImportDocumentException("Content");
		}
	}

	public static void use(Literal literal, Element element, SpreadSheet sheet) {

		literal.setNumber(Integer.parseInt(element.getAttribute(
						"number").getValue()));

    }

    public static void use (Reference reference, Element element, SpreadSheet sheet) {
		
    	Element content = element.getChild("Cell");

		int row    = Integer.parseInt(content.getAttribute("cellRow").getValue());
		int column = Integer.parseInt(content.getAttribute("cellColumn").getValue());

		for (Cell cell : sheet.getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				reference.setCell(cell);
				break;
			}
		}

	}

	private static void use(BinaryFunction function, Element element, SpreadSheet sheet, String type) {
		
		for (Element content : element.getChildren()) {
			switch (content.getValue()) {
			case "Reference":
				function.addArgs(new Reference(sheet, content));
				break;
			case "Literal":
				function.addArgs(new Literal(sheet, content));
				break;
			default:
				//throw new ImportDocumentException("FunctionArguments");
			}
		}
	}

	public static void use(ADD function, Element element, SpreadSheet sheet) {
		use(function, element, sheet, "ADD");
	}

	public static void use(SUB function, Element element, SpreadSheet sheet) {
		use(function, element, sheet, "SUB");
	}

	public static void use(MUL function, Element element, SpreadSheet sheet) {
		use(function, element, sheet, "MUL");
	}

	public static void use(DIV function, Element element, SpreadSheet sheet) {
		use(function, element, sheet, "DIV");
	}

	private static void use(RangedFunction function, Element element, SpreadSheet sheet, String type) {
		for (Element content : element.getChildren()) {
			function.addArgs(new Reference(sheet, content));
		}
	}

	public static void use(AVG function, Element element, SpreadSheet sheet) {
		use(function, element, sheet, "AVG");
	}

	public static void use(PRD function, Element element, SpreadSheet sheet) {
		use(function, element, sheet, "PRD");
	}
}
