package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;


import org.jdom2.Element;
import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.ExportException;

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
		setId(id);
			//BubbleDocs.getInstance().
		setSpreadSheetName(name);
		setDate(new LocalDate());
		setNumberRows(rows);
		setNumberColumns(columns);
		for (int row = 1; row < rows; row++) {
			for (int column = 1; column < columns; column++) {
				addCells(new Cell(row, column));
			}
		}
	}
	
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
			if (a.getUser().getUsername().equals(user.getUsername())) {
				return a.getPermission();
			}
		}
		return 0;
	}

	public void addContent(Content c, int row, int column) {
		for (Cell cell : getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				// try{
				cell.setContent(c);
				// }catch(ProtectedCellException e)
				break;
			}
		}
	}

	public void removeContent(int row, int column) {
		for (Cell cell : getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
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
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				// try{
				description += row + ";" + column;
				description += cell.toString();
				// }catch(ProtectedCellException e)
			}
		}
		return description;
	}
	
	public void    importFromXML (Element element)                    throws ImportException { Importer.use (this, element) ; }
	public Element exportToXML   ()                                   throws ExportException { return Exporter.use (this)   ; }
    public String  toString         ()                { return Printer.use  (this)   ; }
}
