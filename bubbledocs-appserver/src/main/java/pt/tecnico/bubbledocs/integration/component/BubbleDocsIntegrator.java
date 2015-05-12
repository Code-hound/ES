package pt.tecnico.bubbledocs.integration.component;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public abstract class BubbleDocsIntegrator {

	public final void execute() throws BubbleDocsException {
		dispatch();
	}

	protected abstract void dispatch() throws BubbleDocsException;
}
