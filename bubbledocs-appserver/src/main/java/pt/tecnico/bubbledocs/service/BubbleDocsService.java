package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.ist.fenixframework.Atomic;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public abstract class BubbleDocsService {
	
	@Atomic
	public final void execute() throws BubbleDocsException {
		dispatch();
	}
	
	public static BubbleDocs getBubbleDocs() {
		return BubbleDocs.getInstance();
	}
	
	public abstract void dispatch() throws BubbleDocsException;
}
