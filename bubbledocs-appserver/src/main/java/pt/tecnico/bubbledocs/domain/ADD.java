package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.InvalidValueException;

public class ADD extends ADD_Base {

	public ADD(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public ADD(FunctionArguments arg1, FunctionArguments arg2) {
		super();
		addArgs(arg1);
		addArgs(arg2);
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
