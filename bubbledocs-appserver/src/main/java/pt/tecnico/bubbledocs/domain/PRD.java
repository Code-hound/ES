package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class PRD extends PRD_Base {
    
    public PRD(Reference r1, Reference r2) {
        super();
        init(r1,r2);
    }
    
    public int     getContentValue  ()                { return Getter.visit   (this)          ; }
	public void    importFromXML    (Element element) { Importer.visit (this, element)        ; }
	public Element exportToXML      ()                { return Exporter.visit (this)          ; }
    public String  toString         ()                { return Printer.visit  (this)          ; }
}
