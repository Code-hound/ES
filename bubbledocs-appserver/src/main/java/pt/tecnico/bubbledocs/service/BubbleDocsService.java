package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.*;
import pt.ist.fenixframework.Atomic;

public abstract class BubbleDocsService throws BubbleDocsException {
	
	@Atomic
	public final void execute () {
		dispatch();
	}
	
	public static BubbleDocs getBubbleDocs () {
		return BubbleDocs.getInstance();
	}
	
	public abstract void dispatch () throws BubbleDocsException;
}
 