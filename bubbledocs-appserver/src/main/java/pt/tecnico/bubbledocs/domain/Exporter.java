package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import java.lang.NullPointerException;
import pt.tecnico.bubbledocs.exception.ExportException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;

public class Exporter {

	/*
	 *	Exporter
	 *
	 *	Exporta os elementos da SpreadSheet para XML
	 *
	 *	@author: Luís Ribeiro Gomes
	 */

	public static Element use(User type){
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.setAttribute("username", type.get_username());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "username");
		}

		try {
			element.setAttribute("name", type.get_name());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "name");
		}

		try {
			element.setAttribute("password", type.get_password());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "password");
		}

		return element;
	}


	public static Element use(SpreadSheet type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.setAttribute("id", String.valueOf(type.get_id()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "id");
		}

		try {
			element.setAttribute("name", type.get_spreadSheetName());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "name");
		}

		try {
			element.setAttribute("date", type.get_date().toString());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "date");
		}

		try {
			element.setAttribute("rows", String.valueOf(type.get_numberRows()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "rows");
		}

		try {
			element.setAttribute("columns", String.valueOf(type.get_numberColumns()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "columns");
		}

		try {
			element.addContent(type.getOwner().exportToXML());
		} catch (NullPointerException e) {
			throw new ExportException("User");
		}

		for(Cell c : type.getCellsSet())
			if(c.getContent() != null)
				element.addContent(c.exportToXML());

		return element;
	}

	public static Element use(Cell type){
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		if (type.get_protected()) {
			throw new ProtectedCellException (type.get_cellRow(), type.get_cellColumn());
			return;
		}

		try {
			element.setAttribute("row", String.valueOf(type.get_cellRow()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "row");
		}

		try {
			element.setAttribute("column", String.valueOf(type.get_cellColumn()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "column");
		}

		try {
			element.setAttribute("protected", String.valueOf(type.get_protected()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "protected");
		}

		try {
			element.addContent(type.getContent().exportToXML());
		} catch (NullPointerException e) {
			throw new ExportException("Content");
		}

		return element;
	}

	public static Element use(Literal type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.setAttribute("number", String.valueOf(type.get_number()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "number");
		}

		return element;
	}

	public static Element use(Reference type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.addContent(type.getCellReference().exportToXML());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "Cell");
		}

		return element;
	}
	
	public static Element use(BinaryFunction type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		for (FunctionArguments content : type.getArgs()) {
			try {
				element.addContent(content.exportToXML());
			} catch (NullPointerException e) {
				throw new ExportException("FunctionArguments");
			}
		}

		return element;
	}

	public static Element use(RangedFunction type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		for (Reference content : type.getArgs()) {
			try {
				element.addContent(content.exportToXML());
			} catch (NullPointerException e) {
				throw new ExportException("Reference");
			}
		}

		return element;
	}
}
