package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;

import org.joda.time.LocalDate;

public class SpreadSheet extends SpreadSheet_Base {

	public SpreadSheet(Element element) {
		super();
		importFromXML(element);
	}

	public SpreadSheet(String owner, int id, String name, int rows, int columns) {
		super();
		setOwnerUsername(owner);
		setId(id);
		setSpreadSheetName(name);
		setCreationDate(new LocalDate());
		setNumberRows(rows);
		setNumberColumns(columns);
		for (int row = 1; row <= rows; row++) {
			for (int column = 1; column <= columns; column++) {
				addCells(new Cell(row, column));
			}
		}
	}
	
	public List<User> getAccessUsers() {
		List<User> users = new ArrayList<User>();
		for (Access a : getDocAccessSet()) {
			users.add(a.getUser());
		}
		return users;
	}

	/*
	 * Retorna o valor da permissao que o utilizador tem sobre o documento caso
	 * esta exista, 0 caso contrário
	 * Permissoes: 1 - READER 2 - WRITER
	 */
	public int getUserPermissionLevel (String username) {
		for (Access a : this.getDocAccessSet()) {
			//System.out.println("User "+a.getUser().getUsername()+" has permission level "+a.getPermission());
			if (a.getUser().getUsername().equals(username)) {
				return a.getPermission();
			}
		}
		return 0;
	}

	public void addContent(Content c, int row, int column) throws CellNotInSpreadSheetException {
		for (Cell cell : getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				cell.setContent(c);
				return;
			}
		}
		throw new CellNotInSpreadSheetException(row, column, this.getId());
	}

	public void removeContent(int row, int column) throws CellNotInSpreadSheetException {
		for (Cell cell : getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				cell.setContent(null);
				return;
			}
		}
	}
	
	public Cell getCell (int row, int column) throws CellNotInSpreadSheetException {
		for (Cell cell : getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				return cell;
			}
		}
		throw new CellNotInSpreadSheetException(row, column, this.getId());
	}
	
	public String getCellDescription (int row, int column) throws CellNotInSpreadSheetException {
		String description = "";
		for (Cell cell : getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				description += row + ";" + column + " ";
				description += cell.toString();
				return description;
			}
		}
		throw new CellNotInSpreadSheetException(row, column, this.getId());
	}

	public void importFromXML(Element element) {
		Importer.use(this, element);
	}

	public Element exportToXML() {
		return Exporter.use(this);
	}

	public String toString() {
		return Printer.use(this);
	}
}
