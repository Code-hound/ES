package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Reference extends Reference_Base {
	
    public Reference(Cell c) {
        super();
        setCell(c);
    }
    
    public Reference (SpreadSheet sheet, int row, int column) {
    	super();
    	for (Cell c : sheet.getCellsSet()) {
    		if (c.get_cellColumn() == row && c.get_cellRow() == column) {
    			setCell_reference(c);
    			break;
    		}
    	}
    }
    
    public int     getContentValue  ()                { return Getter.visit   (this)          ; }
	public void    importFromXML    (Element element) { Importer.visit (this, element)        ; }
	public Element exportToXML      ()                { return Exporter.visit (this)          ; }
    public String  toString         ()                { return Printer.visit  (this)          ; }
}
