package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import java.lang.NullPointerException;

public class Exporter {

	public static Element visit(User type){
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		element.setAttribute("username", type.get_username());
		element.setAttribute("name"    , type.get_name()    );
		element.setAttribute("password", type.get_password());

		return element;
	}

	public static Element visit(SpreadSheet type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		element.setAttribute("id",      String.valueOf(type.get_id()));
		element.setAttribute("name",    type.get_spreadSheetName());
		element.setAttribute("date",    type.get_date().toString());
		element.setAttribute("rows",    String.valueOf(type.get_numberRows()   ));
		element.setAttribute("columns", String.valueOf(type.get_numberColumns()));

		try {
			element.addContent(type.getOwner().exportToXML());
		} catch (NullPointerException e) {
			System.out.println("Spreadsheet throws new ExportDocumentException();");
		}

		for(Cell c : type.getCellsSet())
			if(c.getContent() != null)
				element.addContent(c.exportToXML());

		return element;
	}

	public static Element visit(Cell type){
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		element.setAttribute("row"      , String.valueOf(type.get_cellRow()   ));
		element.setAttribute("column"   , String.valueOf(type.get_cellColumn()));
		element.setAttribute("protected", String.valueOf(type.get_protected() ));
		if (type.getContent() != null)
			element.addContent(type.getContent().exportToXML());

		return element;
	}

	public static Element visit(Literal type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		element.setAttribute("number", String.valueOf(type.get_number()));

		return element;
	}

	public static Element visit(Reference type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.addContent(type.getCell_reference().exportToXML());
		} catch (NullPointerException e) {
			System.out.println("Reference throws new ExportDocumentException();");
		}

		return element;
	}
	
	public static Element visit(BinaryFunction type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		for (FunctionArguments content : type.getArgs()) {
			try {
				element.addContent(content.exportToXML());
			} catch (NullPointerException e) {
				System.out.println("BinaryFunction throws new ExportDocumentException();");
			}	
		}

		return element;
	}

	public static Element visit(RangedFunction type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		for (Reference content : type.getArgs()) {
			try {
				element.addContent(content.exportToXML());
			} catch (NullPointerException e) {
				System.out.println("RangedFunction throws new ExportDocumentException();");
			}
		}

		return element;
	}
}
