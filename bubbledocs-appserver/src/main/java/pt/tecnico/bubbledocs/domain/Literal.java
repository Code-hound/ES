package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Literal extends Literal_Base {

	public Literal (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

    public Literal(int literal) {
        super();
        set_number(literal);
    }
    
	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.visit (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.visit (this)          ; }
    public String  toString         ()                { return Printer.visit  (this)          ; }

    public int     getContentValue  ()                { return Getter.visit   (this)          ; }
}
