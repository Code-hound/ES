package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.*;
import pt.ist.fenixframework.Atomic;

public abstract class BubbleDocsService {
	
	@Atomic
	public final void execute () {
		dispatch();
	}
	
	public static BubbleDocs getBubbleDocs () {
		return BubbleDocs.getInstance();
	}
	
	public abstract void dispatch ();
}
 