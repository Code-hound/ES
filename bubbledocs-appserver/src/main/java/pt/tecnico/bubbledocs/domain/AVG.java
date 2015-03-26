package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class AVG extends AVG_Base {

	public AVG (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

    public AVG (Reference arg1, Reference arg2, SpreadSheet arg3) {
        super();
    	addArgs(arg1);
    	addArgs(arg2);
    	setSpreadSheet(arg3);
    }
    
	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.visit (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.visit (this)   ; }
    public String  toString         ()                { return Printer.visit  (this)   ; }

    public int     getContentValue  ()                { return Getter.visit   (this)   ; }
}
