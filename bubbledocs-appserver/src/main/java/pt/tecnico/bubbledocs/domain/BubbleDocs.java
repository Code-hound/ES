package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.joda.time.LocalDate;

import pt.tecnico.bubbledocs.exception.*;
import pt.ist.fenixframework.FenixFramework;

public class BubbleDocs extends BubbleDocs_Base {

	private BubbleDocs() {
		super();
		setNextDocumentId(0);
		addUsers(new User("root", "root", "rootroot"));
	}

	public static BubbleDocs getInstance() {
		BubbleDocs bubble = FenixFramework.getDomainRoot().getBubbleDocs();
		if (bubble == null) {
			bubble = new BubbleDocs();
			// bubble.
		}

		return bubble;
	}
	
	private boolean isRoot (User user) {
		return user.getUsername().equals("root");
	}
	
	public boolean checkIfRoot (String userToken)
			throws UserIsNotRootException {
		User root = getUserLoggedInByToken (userToken);
		if (!isRoot(root)) throw new UserIsNotRootException(root.getUsername());
		return true;
	}

	private boolean hasUsers() {
		return !getUsersSet().isEmpty();
	}

	public User createUser(String newUserName, String newName,
			String newPassword) throws UserAlreadyExistsException {
		if (hasUserByUsername(newUserName)) throw new UserAlreadyExistsException(newUserName);
		User newUser = new User(newUserName, newName, newPassword);
		addUsers(newUser);
		return newUser;
	}
	/*
	public void deleteUser(User userToRemove)
			throws UserDoesNotExistException {
		User user = getUserByUsername (userToRemove);
		removeUsers(user);
	}
	*/
	private boolean hasUserByUsername(String UserName) {
		if (hasUsers()) {
			for (User u : getUsersSet()) {
				if (u.getUsername().equals(UserName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public User getUserByUsername(String username) 
			throws UserDoesNotExistException {
		if (hasUsers()) {
			for (User u : getUsersSet()) {
				if (u.getUsername().equals(username)) {
					return u;
				}
			}
		}
		throw new UserDoesNotExistException(username);
	}

	public void addUserToSession(User user, String password)
			throws UserAlreadyInSessionException, WrongPasswordException {
		removeIdleUsersInSession ();
		String username = user.getUsername();
		//User user = getUserByUsername(username); //already throws UserDoesNotExistException
		if (hasUserLoggedInByUsername(username)) throw new UserAlreadyInSessionException(username);
		if (!user.getPassword().equals(password)) throw new WrongPasswordException(username);
		
		Session session = new Session(username);
		addSessions(session);
		
		String usertoken = username + (int)(Math.random()*10);
		user.setUserToken(""+username+usertoken);
	}

	public void removeUserFromSession (String userToken) {
		User user = getUserLoggedInByToken (userToken); //already throws UserNotInSessionException
		removeUserFromSession(user);
	}

	public void removeUserFromSession (User user) {
		user.setUserToken(null);
		
		for (Session s : getSessionsSet()) {
			if (s.getUsername().equals(user.getUsername())) {
				removeSessions(s);
				s = null;
				break;
			}
		}
	}

	private
	void removeIdleUsersInSession() {
		if (hasUsers()) {
			for (Session s : getSessionsSet()) {
				if(s.getLastAccess().plusHours(2).isAfterNow()) {
					User userToRemove = getUserByUsername(s.getUsername());
					removeUserFromSession(userToRemove);
					removeSessions(s);
				}
			}
		}
	}

	public boolean hasUserLoggedInByToken(String userToken) {
		if (hasUsers()) {
			for (User user : this.getUsersSet()) {
				if (user.getUserToken().equals(userToken))
					return true;
			}
		}
		return false;
	}
	
	public boolean hasUserLoggedInByUsername (String username) {
		if (hasUsers()) {
			for (Session session : this.getSessionsSet()) {
				if (session.getUsername().equals(username))
					return true;
			}
		}
		return false;
	}

	public User getUserLoggedInByToken(String userToken) 
			throws UserNotInSessionException {
		if (hasUsers()) {
			for (User user : this.getUsersSet()) {
				if (user.getUserToken().equals(userToken))
					return user;
			}
		}
		throw new UserNotInSessionException(userToken);
	}
	
	public boolean hasSpreadSheet() {
		return !getDocsSet().isEmpty();
	}

	public SpreadSheet getSpreadSheetById(int id) throws DocumentDoesNotExistException {
		for (SpreadSheet s : getDocsSet()) {
			if (s.getId() == id) {
				return s;
			}
		}
		throw new DocumentDoesNotExistException(id);
	}

	public List<SpreadSheet> getSpreadSheetByName(String sheetName) {
		List<SpreadSheet> list = new ArrayList<SpreadSheet>();
		for (SpreadSheet s : getDocsSet()) {
			if (s.getSpreadSheetName().equals(sheetName)) {
				list.add(s);
			}
		}
		return list;
	}

	public List<SpreadSheet> getSpreadSheetByOwner(User owner) {
		List<SpreadSheet> list = new ArrayList<SpreadSheet>();
		for (SpreadSheet s : getDocsSet()) {
			if (s.getOwnerUsername().equals(owner.getUsername())) {
				list.add(s);
			}
		}
		return list;
	}
	
	public SpreadSheet getSpreadSheetByOwnerAndName (User owner, String sheetName) 
			throws DocumentDoesNotExistException {
		for (SpreadSheet sheet : getSpreadSheetByOwner(owner)) {
			if (sheet.getSpreadSheetName().equals(sheetName)) {
				return sheet;
			}
		}
		throw new DocumentDoesNotExistException(owner.getUsername(), sheetName);
	}
	
	public boolean hasSpreadSheetByOwnerAndName (User owner, String sheetName)
			throws DocumentDoesNotExistException {
		for (SpreadSheet sheet : getSpreadSheetByOwner(owner)) {
			if (sheet.getSpreadSheetName().equals(sheetName)) {
				return true;
			}
		}
		return false;
	}

	public SpreadSheet getSpreadSheetByNameAndDate(String sheetName,
			LocalDate date) throws DocumentDoesNotExistException {
		for (SpreadSheet s : getDocsSet()) {
			if (s.getSpreadSheetName().equals(sheetName)
					&& s.getCreationDate().equals(date))
				return s;
		}
		throw new DocumentDoesNotExistException(sheetName, date);
	}

	public SpreadSheet createSpreadSheet(User owner, String sheetName, int rows,
			int columns) throws UserAlreadyHasThisDocumentException {
		
		if (hasSpreadSheetByOwnerAndName(owner, sheetName))
			throw new UserAlreadyHasThisDocumentException(owner.getUsername(), sheetName);
		
		//User user = getUserByUsername(username);
			
		SpreadSheet newSpreadSheet = new SpreadSheet(owner.getUsername(), getNextDocumentId(),
				sheetName, rows, columns);
		if (newSpreadSheet != null) {
			newSpreadSheet.addDocAccess(new Access(owner.getUsername(), "writer"));
			addDocs(newSpreadSheet);

			setNextDocumentId(getNextDocumentId() + 1);
		}
		return newSpreadSheet;
	}
	/*
	public void addSpreadSheet(SpreadSheet spreadsheet) {
		if (spreadsheet.getId() <= 0)
			spreadsheet.setId(getNextDocumentId());
		spreadsheet.addDocAccess(new Access(spreadsheet.getOwnerUsername(), "writer"));
		addDocs(spreadsheet);

		setNextDocumentId(getNextDocumentId() + 1);
	}
	*/

	public void addAccessToSpreadSheet(String username, SpreadSheet spreadsheet,
			int permissionLevel) {
		//String username = user.getUsername();
		Access access = new Access(username, permissionLevel);
		spreadsheet.addDocAccess(access);
	}

	public String listSpreadSheetNameAndId() {
		String list = "";

		if (hasSpreadSheet()) {
			for (SpreadSheet s : getDocsSet()) {
				if (!list.equals("")) {
					list += "\n";
				}
				list += "Name: " + s.getSpreadSheetName() + " Id: " + s.getId();
			}
			return list;
		}
		return null;
	}

	public void removeSpreadSheetByOwner(User owner, String sheetName)
			throws DocumentDoesNotExistException {
		for (SpreadSheet s : getSpreadSheetByName(sheetName)) {
			if (s.getOwnerUsername().equals(owner.getUsername())) {
				removeDocs(s);
				s = null;
				return;
			}
		}
		throw new DocumentDoesNotExistException(owner.getUsername(), sheetName);
	}

	public void removeSpreadSheetById(int id) throws DocumentDoesNotExistException {
		SpreadSheet spreadsheetToRemove = getSpreadSheetById(id);
		removeDocs(spreadsheetToRemove);
		spreadsheetToRemove = null;
	}

	public String listUsers() {
		if (hasUsers()) {
			String s = "";
			for (User u : getUsersSet()) {
				if (s.equals(""))
					;
				s += u.toString();
			}
			return s;
		}
		return null;
	}

	public Element exportToXML(String sheetName, LocalDate date) {
		return Exporter.use(getSpreadSheetByNameAndDate(sheetName, date));
	}

}
