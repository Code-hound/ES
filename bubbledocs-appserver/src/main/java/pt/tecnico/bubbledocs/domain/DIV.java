package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class DIV extends DIV_Base {
    
    public DIV(Content c1,Content c2) {
        super();
        init(c1,c2);
    }
    
    public int     getContentValue  ()                { return Getter.visit   (this)          ; }
	public void    importFromXML    (Element element) { Importer.visit (this, element)        ; }
	public Element exportToXML      ()                { return Exporter.visit (this)          ; }
    public String  toString         ()                { return Printer.visit  (this)          ; }
}
