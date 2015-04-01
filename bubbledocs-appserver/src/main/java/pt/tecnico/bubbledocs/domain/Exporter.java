package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import java.lang.NullPointerException;
import pt.tecnico.bubbledocs.exception.ExportException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;

public class Exporter {

	/*
	 * Exporter
	 * 
	 * Exporta os elementos da SpreadSheet para XML
	 * 
	 * @author: Luis Ribeiro Gomes
	 */

	public static Element use(SpreadSheet type) throws ExportException {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.setAttribute("id", String.valueOf(type.getId()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "id");
		}

		try {
			element.setAttribute("name", type.getSpreadSheetName());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "name");
		}

		try {
			element.setAttribute("date", type.getCreationDate().toString());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "date");
		}

		try {
			element.setAttribute("rows", String.valueOf(type.getNumberRows()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "rows");
		}

		try {
			element.setAttribute("columns",
					String.valueOf(type.getNumberColumns()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "columns");
		}

		try {
			element.setAttribute("ownerUsername", type.getOwnerUsername());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "ownerUsername");
		}

		for (Cell c : type.getCellsSet())
			if (c.getContent() != null)
				element.addContent(c.exportToXML());

		return element;
	}

	public static Element use(Cell type) throws ExportException {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		if (type.getProtect()) {
			throw new ProtectedCellException(type.getCellRow(),
					type.getCellColumn());
		} else {

			try {
				element.setAttribute("row", String.valueOf(type.getCellRow()));
			} catch (NullPointerException e) {
				throw new ExportException(classname, "row");
			}

			try {
				element.setAttribute("column",
						String.valueOf(type.getCellColumn()));
			} catch (NullPointerException e) {
				throw new ExportException(classname, "column");
			}

			try {
				element.setAttribute("protect",
						String.valueOf(type.getProtect()));
			} catch (NullPointerException e) {
				throw new ExportException(classname, "protect");
			}

			if (type.getContent() != null)
				element.addContent(type.getContent().exportToXML());

		}

		return element;
	}

	public static Element use(Literal type) throws ExportException {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.setAttribute("number", String.valueOf(type.getNumber()));
		} catch (NullPointerException e) {
			throw new ExportException(classname, "number");
		}

		return element;
	}

	public static Element use(Reference type) throws ExportException {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		try {
			element.addContent(type.getCellReference().exportToXML());
		} catch (NullPointerException e) {
			throw new ExportException(classname, "Cell");
		}

		return element;
	}

	public static Element use(BinaryFunction type) throws ExportException {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		for (FunctionArguments content : type.getArgsSet()) {
			try {
				element.addContent(content.exportToXML());
			} catch (NullPointerException e) {
				throw new ExportException("FunctionArguments");
			}
		}

		return element;
	}

	public static Element use(RangedFunction type) throws ExportException {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		for (Reference content : type.getArgsSet()) {
			try {
				element.addContent(content.exportToXML());
			} catch (NullPointerException e) {
				throw new ExportException("Reference");
			}
		}

		return element;
	}
}
