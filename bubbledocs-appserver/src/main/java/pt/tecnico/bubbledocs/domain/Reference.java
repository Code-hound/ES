package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.ExportException;

public class Reference extends Reference_Base {

	public Reference(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public Reference(SpreadSheet sheet, int row, int column) {
		super();
		for (Cell c : sheet.getCellsSet()) {
			if (c.getCellColumn() == row && c.getCellRow() == column) {
				setCellReference(c);
				break;
			}
		}
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
