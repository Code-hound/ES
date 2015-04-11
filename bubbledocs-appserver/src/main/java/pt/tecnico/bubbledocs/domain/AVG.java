package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class AVG extends AVG_Base {

	public AVG(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public AVG(Reference arg1, Reference arg2, SpreadSheet arg3) {
		super();
		addArgs(arg1);
		addArgs(arg2);
		setSpreadSheet(arg3);
	}

	public void importFromXML(Element element, SpreadSheet sheet) {
		Importer.use(this, element, sheet);
	}

	public Element exportToXML() {
		return Exporter.use(this);
	}

	public String toString() {
		return Printer.use(this);
	}

	public int getContentValue() {
		return Getter.use(this);
	}
}
