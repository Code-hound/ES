package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Cell extends Cell_Base {
	
    public Cell(int row, int column) {
        super();
        set_cellRow(row);
        set_cellColumn(column);
    }
    
    public void toogleProtection(){
    	set_protected(!get_protected());
    }

    public int     getValue         ()                { return Getter.visit   (this)          ; }
	public void    importFromXML    (Element element) { Importer.visit (this, element)        ; }
	public Element exportToXML      ()                { return Exporter.visit (this)          ; }
    public String  toString         ()                { return Printer.visit  (this)          ; }
}
