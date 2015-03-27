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
    			setCellReference(c);
    			break;
    		}
    	}
    }
    
	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.use (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.use (this)   ; }
    public String  toString         ()                { return Printer.use  (this)   ; }

    public int     getContentValue  ()                { return Getter.use   (this)   ; }
}
