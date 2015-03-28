package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;


import org.jdom2.Element;

import org.joda.time.LocalDate;

public class SpreadSheet extends SpreadSheet_Base {

	public SpreadSheet (Element element) {
		super();
		importFromXML(element);
	}

	public SpreadSheet (User owner, int id, String name, int rows, int columns) {
		super();
		//setBubbleDocs(BubbleDocs.getInstance());
		setOwner(owner);
		set_id(id);
			//BubbleDocs.getInstance().
		set_spreadSheetName(name);
		set_date(new LocalDate());
		set_numberRows(rows);
		set_numberColumns(columns);
		for (int row = 1; row < rows; row++) {
			for (int column = 1; column < columns; column++) {
				addCells(new Cell(row, column));
			}
		}
	}
	/*
	public SpreadSheet(User owner, String name, int rows, int columns) {
		this(owner, -1, name, rows, columns);
	}
	*/
	
	public List<User> getReadOnlyUser() {
		List<User> users = new ArrayList<User>();
		for (Access a : getDocAccessSet()) {
			if (a.getPermission() == 4) {
				users.add(a.getUser());
			}
		}
		return users;
	}

	public List<User> getReadWriteUserOnly() {
		List<User> users = new ArrayList<User>();
		/*
		 * User u = getCreator(); users.add(u);
		 */
		for (Access a : getDocAccessSet()) {
			if (a.getPermission() == 3) {
				users.add(a.getUser());
			}
		}
		return users;
	}
	
	public void setOwner(User owner) {
		for (Access a : getDocAccessSet()) {
			if (a.getPermission() == 2) {
				a.setUser(owner);
			}
		}
	}
	
	public User getOwner () {
		User owner = null;
		for (Access a : getDocAccessSet()) {
			if (a.getPermission() == 2) {
				owner = a.getUser();
				break;
			}
		}
		return owner;
	}

	public List<User> getAccessUsers() {
		List<User> users = new ArrayList<User>();
		for (Access a : getDocAccessSet()) {
			users.add(a.getUser());
		}
		return users;
	}
	
	/*
	 * Retorna o valor da permissão que o utilizador tem sobre o documento caso esta exista, 0 caso contrário
	 * Permissões:
	 * 1 - ROOT
	 * 2 - OWNER
	 * 3 - WRITER
	 * 4 - READER
	 */
	public int getUserPermission (User user) {
		for (Access a : getDocAccessSet()) {
			if (a.getUser().get_username().equals(user.get_username())) {
				return a.getPermission();
			}
		}
		return 0;
	}

	public void addContent(Content c, int row, int column) {
		for (Cell cell : getCellsSet()) {
			if (cell.get_cellRow() == row && cell.get_cellColumn() == column) {
				// try{
				cell.setContent(c);
				// }catch(ProtectedCellException e)
				break;
			}
		}
	}

	public void removeContent(int row, int column) {
		for (Cell cell : getCellsSet()) {
			if (cell.get_cellRow() == row && cell.get_cellColumn() == column) {
				// try{
				cell.setContent(null);
				// }catch(ProtectedCellException e)
				break;
			}
		}
	}

	public String getCellDescription(int row, int column) {
		String description = "";
		for (Cell cell : getCellsSet()) {
			if (!description.equals("")) {
				description += "\n";
			}
			if (cell.get_cellRow() == row && cell.get_cellColumn() == column) {
				// try{
				description += row + ";" + column;
				description += cell.toString();
				// }catch(ProtectedCellException e)
			}
		}
		return description;
	}
	
	public void    importFromXML    (Element element) { Importer.use (this, element) ; }
	public Element exportToXML      ()                { return Exporter.use (this)   ; }
    public String  toString         ()                { return Printer.use  (this)   ; }
}
