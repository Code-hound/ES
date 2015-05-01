package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.joda.time.LocalDate;

import pt.tecnico.bubbledocs.exception.*;
import pt.ist.fenixframework.FenixFramework;

public class BubbleDocs extends BubbleDocs_Base {

	public static BubbleDocs getInstance() {
		BubbleDocs bubble = FenixFramework.getDomainRoot().getBubbleDocs();
		if (bubble == null) {
			bubble = new BubbleDocs();
		}
		
		return bubble;
	}
	
	public boolean isRoot (User root) {
		return (root.getUsername().equals("root"));
	}

	private BubbleDocs() {
		//super();
		FenixFramework.getDomainRoot().setBubbleDocs(this);
		
		setNextDocumentId(0);
		addUsers(new User("root", "root", "rootroot", "email@email.email"));
	}

	private boolean hasUsers() {
		return !getUsersSet().isEmpty();
	}

	public User createUser(String newUsername, String newPassword, String newName, String newEmail) throws BubbleDocsException {
		//System.out.println("username:"+newUsername+" length:"+newUsername.length()+  
		//		"   password:"+newPassword);
		if (newUsername.length() < 3 || newUsername.length() > 8)
			throw new InvalidUsernameException();
		if (hasUserByUsername(newUsername))
			throw new UserAlreadyExistsException(newUsername);
		if (newEmail.length() == 0)
			throw new EmptyEmailException();
		User newUser = new User(newUsername, newPassword, newName, newEmail);
		newUser.setUserToken("");
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
			throws UnknownBubbleDocsUserException {
		if (hasUsers()) {
			for (User u : getUsersSet()) {
				if (u.getUsername().equals(username)) {
					return u;
				}
			}
		}
		return null;
	}

	public String addUserToSession(User user) {
		
		String username = user.getUsername();
		
		this.removeUserFromSession(user);
		
		String usertoken = username + (int)(Math.random()*10);
		user.setUserToken(""+usertoken);
		
		Session session = new Session(user);
		this.addSessions(session);
		
		return usertoken;
	}
	
	public void removeUserFromSession (User user) {
		user.setUserToken("");
		for (Session s : getSessionsSet()) {
			if (s.getUser().getUsername().equals(user.getUsername())) {
				removeSessions(s);
				s = null;
				break;
			}
		}
	}
	
	public String resetUserLastAccess (String userToken) {
		User user = getUserLoggedInByToken(userToken);
		if (user == null)
			return null;
		
		Session session = user.getSession();
		session.resetLastAccess();

		return user.getUsername();
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
				if (session.getUser().getUsername().equals(username))
					return true;
			}
		}
		return false;
	}
	
	public Session getSessionByToken (String userToken) {
		//System.out.println("User token: "+userToken);
		for (Session s : this.getSessionsSet()) {
			//System.out.println("Session: "+s+"\nSession username: "+s.getUser().getUsername());
			if (s.getUser().getUserToken()!=null
					&& s.getUser().getUserToken().equals(userToken)) {
				return s;
			}
		}
		return null;
	}
	
	public User getUserLoggedInByToken(String userToken) {
		Session session = getSessionByToken(userToken);
		if (session != null) {
			return session.getUser();
		}
		return null;
	}
	
	/*
	public String getUsernameLoggedInByToken(String userToken) {
		User user = getUserLoggedInByToken(userToken);
		if (user!=null)
			return user.getUsername();
		return null;
	}
	*/
	
	public boolean hasSpreadSheet() {
		return !getDocsSet().isEmpty();
	}

	public SpreadSheet getSpreadSheetById(int id) throws DocumentDoesNotExistException {
		for (SpreadSheet s : getDocsSet()) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
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
		return null;
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
		return null;
	}

	public SpreadSheet createSpreadSheet(User owner, String sheetName, int rows, int columns) {
		
		if (hasSpreadSheetByOwnerAndName(owner, sheetName))
			throw new UserAlreadyHasThisDocumentException(owner.getUsername(), sheetName);
			
		SpreadSheet newSpreadSheet = new SpreadSheet(owner.getUsername(), getNextDocumentId(),
				sheetName, rows, columns);
		if (newSpreadSheet != null) {
			newSpreadSheet.addDocAccess(new Access(owner, "writer"));
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

	public void addAccessToSpreadSheet(User user, SpreadSheet spreadsheet,
			int permissionLevel) {
		Access access = new Access(user, permissionLevel);
		spreadsheet.addDocAccess(access);
	}
	
	public void addAccessToSpreadSheet(User user, SpreadSheet spreadsheet,
			String permission) {
		Access access = new Access(user, permission);
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

	public Element exportToXML(int sheetId) {
		return getSpreadSheetById(sheetId).exportToXML();
	}

}
