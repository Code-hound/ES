package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.ist.fenixframework.Atomic;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;


public abstract class BubbleDocsService {

	@Atomic
	public final void execute() throws BubbleDocsException {
		dispatch();
	}

	public static BubbleDocs getBubbleDocs() {
		return BubbleDocs.getInstance();
	}
	
	public static boolean checkIfRoot(String usertoken)
			throws UnauthorizedOperationException, UserNotInSessionException {
		BubbleDocs bd = getBubbleDocs();
		User user = bd.getUserLoggedInByToken(usertoken);
		if (user == null)
			throw new UserNotInSessionException(usertoken);
		if (!bd.isRoot(user))
			throw new UnauthorizedOperationException(usertoken);
		return true;
	}
	
	public static String resetUserLastAccess(String userToken) {
		return getBubbleDocs().resetUserLastAccess(userToken);
	}

	public static User getUser(String username) {
		return getBubbleDocs().getUserByUsername(username);
	}

	public static SpreadSheet getSpreadSheet(int sheetId) {
		SpreadSheet sheet = getBubbleDocs().getSpreadSheetById(sheetId);
		if (sheet == null) {
			throw new DocumentDoesNotExistException(sheetId);
		}
		return sheet;
	}
	
	public static boolean canBeWrittenBy
			(SpreadSheet spreadsheet, String username) {
		return (spreadsheet.getUserPermissionLevel(username) == 2);
	}
	
	public static boolean canBeReadBy
			(SpreadSheet spreadsheet, String username) {
		return (spreadsheet.getUserPermissionLevel(username) == 2 ||
				spreadsheet.getUserPermissionLevel(username) == 1);
	}
	
	public static boolean isOwnedBy
		(SpreadSheet spreadsheet, String username) {
		return (spreadsheet.getOwnerUsername().equals(username));
}

	protected abstract void dispatch() throws BubbleDocsException;
}
