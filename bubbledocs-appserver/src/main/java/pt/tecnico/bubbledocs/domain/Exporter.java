package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Exporter {

	public static Element visit(User type){
		String classname = type.getClass().getName();
		Element element = new Element(classname);

		element.setAttribute("username", type.get_username());
		element.setAttribute("name"    , type.get_name()    );
		element.setAttribute("password", type.get_password());

		return element;
	}

	public static Element visit(SpreadSheet type) {
		String classname = type.getClass().getName();
		Element element = new Element(classname);
		User user = type.getOwner();

		element.setAttribute("id",      String.valueOf(type.get_id()));
		element.setAttribute("name",    type.get_spreadSheetName());
		element.setAttribute("date",    type.get_date().toString());
		element.setAttribute("rows",    String.valueOf(type.get_numberRows()   ));
		element.setAttribute("columns", String.valueOf(type.get_numberColumns()));

		if (user != null)
			element.addContent(user.exportToXML());
		else
			element.addContent("<NULL type: User ></NULL>");

		for(Cell c : type.getCellsSet())
			if(c.getContent() != null)
				element.addContent(c.exportToXML());

		return element;
	}

	public static Element visit(Cell type){
		String classname = type.getClass().getName();
		Element element = new Element(classname);
		Content c = type.getContent();

		element.setAttribute("row"      , String.valueOf(type.get_cellRow()   ));
		element.setAttribute("column"   , String.valueOf(type.get_cellColumn()));
		element.setAttribute("protected", String.valueOf(type.get_protected() ));
		if (c != null)
			element.addContent(c.exportToXML());
		else
			element.addContent("<NULL></NULL>");

		return element;
	}

	public static Element visit(Literal type) {
		String classname = type.getClass().getName();
		Element element = new Element(classname);

		element.setAttribute("number", String.valueOf(type.get_number()));

		return element;
	}

	public static Element visit(Reference type) {
		String classname = type.getClass().getName();
		Element element = new Element(classname);
		Cell c = type.getCell();

		if (c != null) {
			element.setAttribute("row",    String.valueOf(c.get_cellRow()   ));
			element.setAttribute("column", String.valueOf(c.get_cellColumn()));	
		} else {
			element.setAttribute("Error", "null");
		}

		return element;
	}
	
	public static Element visit(BinaryFunction type) {
		String classname = type.getClass().getName();
		Element element = new Element(classname);

		for (FunctionArguments content : type.getArgs()) {
			if (content != null)
				element.addContent(content.exportToXML());
			else
				element.addContent("<NULL></NULL>");
		}

		return element;
	}

	public static Element visit(RangedFunction type) {
		String classname = type.getClass().getName();
		Element element = new Element(classname);

		for (Reference content : type.getArgs()) {
			if (content != null)
				element.addContent(content.exportToXML());
			else
				element.addContent("<NULL></NULL>");
		}

		return element;
	}
}
