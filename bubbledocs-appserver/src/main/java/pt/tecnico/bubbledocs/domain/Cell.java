package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Cell extends Cell_Base {

	public Cell (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}
	
    public Cell (int row, int column) {
        super();
        setCellRow(row);
        setCellColumn(column);
    }
    
    public void toogleProtection(){
    	setProtect(!getProtect());
    }

	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.use (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.use (this)          ; }
    public String  toString         ()                { return Printer.use  (this)          ; }

    public int     getValue         ()                { return Getter.use   (this)          ; }
}
