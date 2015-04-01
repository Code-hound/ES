package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.ExportException;

public abstract class BinaryFunction extends BinaryFunction_Base {

	public BinaryFunction() {
		super();
	}

	public abstract void importFromXML(Element element, SpreadSheet sheet)
			throws ImportException;

	public abstract Element exportToXML() throws ExportException;

	public abstract String toString();

	public abstract int getContentValue();
}
