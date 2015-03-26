package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.DataConversionException;
import java.lang.NullPointerException;
import java.lang.Integer;
import java.lang.Boolean;
import java.util.List;

import org.joda.time.LocalDate;

public class Importer {

	public static void visit (User user, Element element){
		if (element.getName() != "User") {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

		//try {
			user.set_username (element.getAttribute("username").getName());
			user.set_name     (element.getAttribute("name").getName()    );
			user.set_password (element.getAttribute("password").getName());
		//} catch (DataConversionException e) { 
			//System.out.println("throw new ImportDocumentException();");
		//}
	}

	public static void visit (SpreadSheet spreadsheet, Element element) {
		if (element.getName() != "SpreadSheet") {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

		Cell newCell;

		try {
			spreadsheet.set_id              (Integer.parseInt(element.getAttribute("id").getName())     );
			spreadsheet.set_spreadSheetName (element.getAttribute("name").getName()                     );
			spreadsheet.set_date            (new LocalDate (element.getAttribute("date").getName())     );
			spreadsheet.set_numberRows      (Integer.parseInt(element.getAttribute("rows").getName())   );
			spreadsheet.set_numberColumns   (Integer.parseInt(element.getAttribute("columns").getName()));
			spreadsheet.setOwner(new User (element.getChild("User")));
			for(Element newContent : element.getChildren("Cell")) {
				newCell = new Cell(spreadsheet, newContent);
				for (Cell cell : spreadsheet.getCellsSet()) {
					if (cell.get_cellRow() == newCell.get_cellRow() && cell.get_cellColumn() == newCell.get_cellColumn()) {
						// try{
						cell = newCell;
						// }catch(ProtectedCellException e)
						break;
					}
				}
			}
		//} catch (DataConversionException e) { 
			//System.out.println("throw new ImportDocumentException();");
		} catch (NullPointerException e) {
			System.out.println("throw new ImportDocumentException();");
		}
  }

	public static void visit (Cell cell, Element element, SpreadSheet sheet) {
		if (element.getName() != "Cell") {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

		List<Element> content_list = element.getChildren();
		Element content = content_list.get(0);

		try {
			cell.set_cellRow(Integer.parseInt(element.getAttribute("row").getName()));
			cell.set_cellColumn(Integer.parseInt(element.getAttribute("column").getName()));
			cell.set_protected(Boolean.parseBoolean(element.getAttribute("protected").getName()));
			switch (content.getName()) {
				case "Literal"   : cell.setContent(new Literal   (sheet, content));
				                   break;
				case "Reference" : cell.setContent(new Reference (sheet, content));
				                   break;
				case "ADD"       : cell.setContent(new ADD       (sheet, content));
				                   break;
				case "SUB"       : cell.setContent(new SUB       (sheet, content));
				                   break;
				case "MUL"       : cell.setContent(new MUL       (sheet, content));
				                   break;
				case "DIV"       : cell.setContent(new DIV       (sheet, content));
				                   break;
				case "AVG"       : cell.setContent(new AVG       (sheet, content));
				                   break;
				case "PRD"       : cell.setContent(new PRD       (sheet, content));
				                   break;
				default          : System.out.println("throw new ImportDocumentException();");
			}
		//} catch (DataConversionException e) { 
			//System.out.println("throw new ImportDocumentException();");
		} catch (NullPointerException e) {
			System.out.println("throw new ImportDocumentException();");
		}
    }
  
    public static void visit (Literal literal, Element element, SpreadSheet sheet) {
		if (element.getName() != "Literal") {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

    	//try {
			literal.set_number(Integer.parseInt(element.getAttribute("number").getName()));
		//} catch (DataConversionException e) {
			//System.out.println("throw new ImportDocumentException();");
		//}
    }
  
    public static void visit (Reference reference, Element element, SpreadSheet sheet) {
		if (element.getName() != "Reference") {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

		Element content = element.getChild("Cell");

    	//try {
			int row = Integer.parseInt(element.getChild("User").getAttribute("row").getName());
			int column = Integer.parseInt(element.getChild("User").getAttribute("column").getName());
			for (Cell cell : sheet.getCellsSet()) {
				if (cell.get_cellRow() == row && cell.get_cellColumn() == column) {
					// try{
					reference.setCell(cell);
					// }catch(ProtectedCellException e)
					break;
				}
			}
			
			
		//} catch (DataConversionException e) {
			//System.out.println("throw new ImportDocumentException();");
		//}
    }

    private static void visit (BinaryFunction function, Element element, SpreadSheet sheet, String type) {
		if (element.getName() != type) {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

		for (Element content : element.getChildren()) {
			//try {
				switch (content.getName()) {
					case "Reference" : function.addArgs(new Reference (sheet, content));
					                   break;
					case "Literal"   : function.addArgs(new Literal (sheet, content));
					                   break;
				    default          : System.out.println("throw new ImportDocumentException();");
				}
			//} catch (DataConversionException e) {
				//System.out.println("throw new ImportDocumentException();");
			//}
		}
    }

    public static void visit (ADD function, Element element, SpreadSheet sheet) { visit(function, element, sheet, "ADD"); }
    public static void visit (SUB function, Element element, SpreadSheet sheet) { visit(function, element, sheet, "SUB"); }
    public static void visit (MUL function, Element element, SpreadSheet sheet) { visit(function, element, sheet, "MUL"); }
    public static void visit (DIV function, Element element, SpreadSheet sheet) { visit(function, element, sheet, "DIV"); }

    private static void visit (RangedFunction function, Element element, SpreadSheet sheet, String type) {
		if (element.getName() != type) {
			System.out.println("throw new ImportDocumentException();");
			return;
		}

		for (Element content : element.getChildren()) {
			//try {
				function.addArgs(new Reference (sheet, content));
			//} catch (DataConversionException e) {
				//System.out.println("throw new ImportDocumentException();");
			//}
		}
    }

    public static void visit (AVG function, Element element, SpreadSheet sheet) { visit(function, element, sheet, "AVG"); }
    public static void visit (PRD function, Element element, SpreadSheet sheet) { visit(function, element, sheet, "PRD"); }
}
