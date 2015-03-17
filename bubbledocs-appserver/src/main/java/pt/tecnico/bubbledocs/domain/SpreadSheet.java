package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.jdom2.Element;

public class SpreadSheet extends SpreadSheet_Base {

	public SpreadSheet(User owner, int id, String name, int rows, int columns) {
		setBubbleDocs(BubbleDocs.getInstance());
		setOwner(owner);
		set_id(id);
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

	public SpreadSheet(User owner, String name, int rows,
			int columns) {
		this(owner, -1, name, rows, columns);
	}

	public List<User> getReadOnlyUser() {
		List<User> users = new ArrayList<User>();
		for (Access a : getDocAccessSet()) {
			if (a.get_permission() == 2) {
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
			if (a.get_permission() == 3) {
				users.add(a.getUser());
			}
		}
		return users;
	}

	public List<User> getAccessUsers() {
		List<User> users = new ArrayList<User>();
		for (Access a : getDocAccessSet()) {
			users.add(a.getUser());
		}
		return users;
	}

	public void addContent(Content c, int row, int column) {
		for (Cell cell : getCellsSet()) {
			if (cell.get_cellRow() == row && cell.get_cellColumn() == column) {
				// try{
				cell.setContent(c);
				// }catch(ProtectedCellException e)
			}
		}
	}

	public void removeContent(int row, int column) {
		for (Cell cell : getCellsSet()) {
			if (cell.get_cellRow() == row && cell.get_cellColumn() == column) {
				// try{
				cell.setContent(null);
				// }catch(ProtectedCellException e)
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

	public Element exportToXML() {
		Element element = new Element("spreadsheet");
		element.setAttribute("id", "" + get_id());
		element.setAttribute("name", get_spreadSheetName());
		element.setAttribute("date", get_date().toString());
		element.setAttribute("rows", String.valueOf(get_numberRows()));
		element.setAttribute("columns", String.valueOf(get_numberColumns()));
		return element;
	}

}
