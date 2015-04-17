package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.InvalidValueException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;

public class Cell extends Cell_Base {

	public Cell(SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

	public Cell(int row, int column) {
		super();
		setCellRow(row);
		setCellColumn(column);
	}

	public void toogleProtection() {
		setProtect(!getProtect());
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

	public int getValue() {
		return Getter.use(this);
	}
	
	@Override
	public void setContent(Content content) throws ProtectedCellException {
		if (getProtect())
			throw new ProtectedCellException(getCellRow(),getCellColumn());
		else
			super.setContent(content);
	}
	
	@Override
	public Content getContent() throws ProtectedCellException, InvalidValueException {
		if (getProtect())
			throw new ProtectedCellException(getCellRow(),getCellColumn());
		else
			return super.getContent();
	}
}
