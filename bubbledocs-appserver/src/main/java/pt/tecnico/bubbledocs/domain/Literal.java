package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Literal extends Literal_Base {
    
    public Literal(int literal) {
        super();
        set_number(literal);
    }
    
    public int     getContentValue  ()                { return Getter.visit   (this)          ; }
	public void    importFromXML    (Element element) { Importer.visit (this, element)        ; }
	public Element exportToXML      ()                { return Exporter.visit (this)          ; }
    public String  toString         ()                { return Printer.visit  (this)          ; }
}
