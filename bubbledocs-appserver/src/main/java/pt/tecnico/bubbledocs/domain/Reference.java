package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.InvalidValueException;

public class Reference extends Reference_Base {

	public Reference(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public Reference(SpreadSheet sheet, int row, int column) {
		super();
		for (Cell c : sheet.getCellsSet()) {
			if (c.getCellRow() == row && c.getCellColumn() == column) {
				setCellReference(c);
				break;
			}
		}
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
