package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.ist.fenixframework.Atomic;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;


public abstract class BubbleDocsService {

	@Atomic
	public final void execute() throws BubbleDocsException {
		dispatch();
	}

	public static BubbleDocs getBubbleDocs() {
		return BubbleDocs.getInstance();
	}
	
	public static String resetUserLastAccess(String userToken) {
		return getBubbleDocs().resetUserLastAccess(userToken);
	}

	public static User getUser(String username) {
		User user = getBubbleDocs().getUserByUsername(username);
		if (user == null) {
			return null;
		}
		return user;
	}

	public static SpreadSheet getSpreadSheet(int sheetId) {
		SpreadSheet sheet = getBubbleDocs().getSpreadSheetById(sheetId);
		if (sheet == null) {
			throw new DocumentDoesNotExistException(sheetId);
		}
		return sheet;
	}

	protected abstract void dispatch() throws BubbleDocsException;
}
