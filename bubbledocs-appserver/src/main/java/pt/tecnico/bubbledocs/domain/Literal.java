package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.ExportException;

public class Literal extends Literal_Base {

	public Literal(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public Literal(int literal) {
		super();
		setNumber(literal);
	}

	public void importFromXML(Element element, SpreadSheet sheet)
			throws ImportException {
		Importer.use(this, element, sheet);
	}

	public Element exportToXML() throws ExportException {
		return Exporter.use(this);
	}

	public String toString() {
		return Printer.use(this);
	}

	public int getContentValue() {
		return Getter.use(this);
	}
}
