package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.InvalidValueException;

public abstract class Function extends Function_Base {

	public Function() {
		super();
	}

	public abstract void importFromXML(Element element, SpreadSheet sheet);

	public abstract Element exportToXML();

	public abstract String toString();

	public abstract int getContentValue() throws InvalidValueException;
}
