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

	public boolean hasUsers() {
		return !getUsersSet().isEmpty();
	}

	public void addUser(User user) throws UserAlreadyExistException {
		if (hasUserByUsername(user.getUsername())) {
			throw new UserAlreadyExistException(user.getUsername());
		} else {
			addUsers(user);
		}
	
	}

	public void removeUser(User currentUser, User userToRemove) {
		if (hasUserByUsername(userToRemove.getUsername())
				&& currentUser.getUsername().equals("root")) {
			removeUsers(userToRemove);
		}
		userToRemove = null;
	}

	public boolean hasUserByUsername(String UserName) {
		return getUserByUsername(UserName) != null;
	}

	public User getUserByUsername(String username) {
		if (hasUsers()) {
			for (User u : getUsersSet()) {
				if (u.getUsername().equals(username))
					return u;
			}
		}
		return null;
	}

	public User getUserLoggedInByToken(String userToken) {
		User user;
		if (hasUsers()) {
			for (Session s : getSessionsSet()) {
				user = getUserByUsername(s.getUsername());
				if (user.getUserToken().equals(userToken))
					return user;
			}
		}
		return null;
	}

	public boolean hasUserLoggedInByToken(String userToken) {
		return getUserLoggedInByToken(userToken) != null;
	}

	public void addUserToSession(String username) {
		removeIdleUsersInSession ();
		User user = getUserByUsername(username);
		
		Session session = new Session(username);
		addSessions(session);
		
		String usertoken = username + (int)(Math.random()*10);
		user.setUserToken(""+username+usertoken);
	}
	
	public void removeUserFromSession (String userToken) {
		User user = getUserLoggedInByToken (userToken);
		user.setUserToken(null);
		
		for (Session s : getSessionsSet()) {
			if (s.getUsername().equals(user.getUsername())) {
				removeSessions(s);
				s = null;
				break;
			}
		}
	}

	public User createUser(String newUserName, String newName,
			String newPassword) {
		User newUser = new User(newUserName, newName, newPassword);
		addUser(newUser);
		return newUser;
	}

	private void removeIdleUsersInSession() {
		if (hasUsers()) {
			for (Session s : getSessionsSet()) {
				if(s.getLastAccess().plusHours(2).isAfterNow()) {
					removeSessions(s);
				}
			}
		}
	}

	public boolean hasSpreadSheet() {
		return !getDocsSet().isEmpty();
	}

	public SpreadSheet getSpreadSheetById(int id) {
		// int idInteger = Integer.parseInt(id);
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
			String s_name = s.getSpreadSheetName();
			if (s_name.equals(sheetName)) {
				list.add(s);
			}
		}
		return list;
	}

	public List<SpreadSheet> getSpreadSheetByOwner(String userName) {
		List<SpreadSheet> list = new ArrayList<SpreadSheet>();
		for (SpreadSheet s : getDocsSet()) {
			String s_name = s.getOwnerUsername();
			if (s_name.equals(userName)) {
				list.add(s);
			}
		}
		return list;
	}

	public SpreadSheet getSpreadSheetByNameAndDate(String sheetName,
			LocalDate date) {
		for (SpreadSheet s : getDocsSet()) {
			if (s.getSpreadSheetName().equals(sheetName)
					&& s.getCreationDate().equals(date))
				return s;
		}
		return null;
	}

	public SpreadSheet createSpreadSheet(User user, String sheetName, int rows,
			int columns) {
		String username = user.getUsername();
		SpreadSheet newSpreadSheet = new SpreadSheet(username, getNextDocumentId(),
				sheetName, rows, columns);
		if (newSpreadSheet != null) {
			newSpreadSheet.addDocAccess(new Access(username, "writer"));
			addSpreadSheet(newSpreadSheet);

			setNextDocumentId(getNextDocumentId() + 1);
		}
		return newSpreadSheet;
	}

	public void addSpreadSheet(SpreadSheet spreadsheet) {
		if (spreadsheet.getId() <= 0)
			spreadsheet.setId(getNextDocumentId());
		spreadsheet.addDocAccess(new Access(spreadsheet.getOwnerUsername(), "writer"));
		addDocs(spreadsheet);

		setNextDocumentId(getNextDocumentId() + 1);
	}

	public void addAccessToSpreadSheet(User user, SpreadSheet spreadsheet,
			int permissionLevel) {
		String username = user.getUsername();
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

	public void removeSpreadSheetByOwner(String owner, String sheetName) {
		for (SpreadSheet s : getSpreadSheetByName(sheetName)) {
			if (s.getOwnerUsername().equals(owner)) {
				removeDocs(s);
				s = null;
			}
		}
	}

	public void removeSpreadSheetById(int id) {
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
