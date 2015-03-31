package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Literal extends Literal_Base {

	public Literal (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

    public Literal(int literal) {
        super();
        setNumber(literal);
    }
    
	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.use (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.use (this)          ; }
    public String  toString         ()                { return Printer.use  (this)          ; }

    public int     getContentValue  ()                { return Getter.use   (this)          ; }
}
