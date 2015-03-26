package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Reference extends Reference_Base {

	public Reference (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
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
    
	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.visit (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.visit (this)   ; }
    public String  toString         ()                { return Printer.visit  (this)   ; }

    public int     getContentValue  ()                { return Getter.visit   (this)   ; }
}
