package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.InvalidValueException;

public class Literal extends Literal_Base {

	public Literal(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public Literal(int literal) {
		super();
		setNumber(literal);
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
