package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.InvalidValueException;

public class PRD extends PRD_Base {

	public PRD(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public PRD(Reference arg1, Reference arg2, SpreadSheet arg3) {
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

	public int getContentValue() throws InvalidValueException {
		return Getter.use(this);
	}
}
