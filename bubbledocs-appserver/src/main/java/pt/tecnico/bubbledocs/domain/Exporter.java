package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Exporter {

	/*
	 * Exporter
	 * 
	 * Exporta os elementos da SpreadSheet para XML
	 * 
	 * @author: Luis Ribeiro Gomes
	 */

	public static Element use(SpreadSheet type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname), content;

		element.setAttribute("id"             , String.valueOf(type.getId()));
		element.setAttribute("spreadSheetName", type.getSpreadSheetName());
		element.setAttribute("ownerUsername"  , type.getOwnerUsername());
		element.setAttribute("creationDate"   , type.getCreationDate().toString());
		element.setAttribute("numberRows"     , String.valueOf(type.getNumberRows()));
		element.setAttribute("numberColumns"  , String.valueOf(type.getNumberColumns()));

		for (Cell c : type.getCellsSet())
			if (c.getContent() != null) {
				content = c.exportToXML();
				if (content != null)
					element.addContent(content);
				else
					return null;
			}

		return element;
	}

	public static Element use(Cell type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname), content;

		element.setAttribute("cellRow"   , String.valueOf(type.getCellRow()));
		element.setAttribute("cellColumn", String.valueOf(type.getCellColumn()));
		element.setAttribute("protect"   , String.valueOf(type.getProtect()));

		if (type.getContent() == null)
			return null;

		content = type.getContent().exportToXML();
		element.addContent(content);

		return element;
	}

	public static Element use(Literal type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname);

		element.setAttribute("number", String.valueOf(type.getNumber()));

		return element;
	}
	
	public static Element use(Reference type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname), content;


		if (type.getCellReference() != null)
			return null;
			
		content = type.getCellReference().exportToXML();
		element.addContent(content);

		return element;
	}

	public static Element use(BinaryFunction type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname), content;

		for (FunctionArguments c : type.getArgsSet()) {
			content = c.exportToXML();
			element.addContent(content);
		}

		return element;
	}

	public static Element use(RangedFunction type) {
		String classname = type.getClass().getSimpleName();
		Element element = new Element(classname), content;

		for (Reference  c : type.getArgsSet()) {
			content = c.exportToXML();
			element.addContent(content);
		}

		return element;
	}
}
