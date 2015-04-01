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

	public User getUserByUsername(String username) {
		if (hasUsers()) {
			for (User u : getUsersSet()) {
				if (u.getUsername().equals(username))
					return u;
			}
		}
		return null;
	}

	public boolean hasUserByUsername(String UserName) {
		return getUserByUsername(UserName) != null;
	}

	public User getUserLoggedInByToken(String userToken) {
		if (hasUsers()) {
			for (User u : getUsersSet()) {
				if (u.getUserToken().equals(userToken))
					return u;
			}
		}
		return null;
	}

	public boolean checkUserLoggedInByToken(String userToken) {
		return getUserLoggedInByToken(userToken) != null;
	}

	public User createUser(String newUserName, String newName,
			String newPassword) {
		User newUser = new User(newUserName, newName, newPassword);
		addUser(newUser);
		return newUser;
	}

	public void addUser(User user) throws UserAlreadyExistException {
		if (hasUserByUsername(user.getUsername())) {
			throw new UserAlreadyExistException(user.getUsername());
		} else {
			addUsers(user);
		}

	}

	public void addUserToSession(String username) {
		User user = getUserByUsername(username);
		
		Session session = new Session(username);
		addSessions(session);
		/*
		String usertoken = username + Math.random().intValue();
		user.setUserToken(username+);
		*/
	}

	public boolean hasSpreadSheet() {
		return !getDocsSet().isEmpty();
	}

	public void removeUser(User currentUser, User userToRemove) {
		if (hasUserByUsername(userToRemove.getUsername())
				&& currentUser.getUsername().equals("root")) {
			removeUsers(userToRemove);
		}

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
			}
		}
	}

	public void removeSpreadSheetById(int id) {
		removeDocs(getSpreadSheetById(id));
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

	public Element exportSpreadSheetToXML(String sheetName, LocalDate date) {
		// try{
		return Exporter.use(getSpreadSheetByNameAndDate(sheetName, date));
		// }catch(Exception e){throw new NonExistingSpreadSheetException(name,
		// date);}
	}

}
