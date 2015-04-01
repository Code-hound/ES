package pt.tecnico.bubbledocs.domain;

import java.util.Set;
import org.jdom2.Element;
import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.ExportException;

public abstract class RangedFunction extends RangedFunction_Base {

	public RangedFunction() {
		super();
	}

	public abstract void importFromXML(Element element, SpreadSheet sheet)
			throws ImportException;

	public abstract Element exportToXML() throws ExportException;

	public abstract String toString();

	public abstract int getContentValue();

	public Set<Reference> getExpanded() {
		final int IGNORE = 0;
		final int COPY = 1;

		// Set<Reference> set = new Set<Reference>(); TODO: Create Set
		Reference r[] = new Reference[2];
		int i = 0;
		int state = IGNORE;

		for (Reference content : getArgs()) {
			if (content != null)
				r[i] = content;
			else
				return null; // TODO: Throw error
			i++;
		}
		for (Cell c : getSpreadSheet().getCells()) {
			if (state == IGNORE) {
				if (c == r[0].getCell())
					state = COPY;
			}
			if (state == COPY) {
				if (c == r[1].getCell())
					break;
			} else {
				break; // TODO: Copy Cell
			}

		}

		return null;
	}
}
