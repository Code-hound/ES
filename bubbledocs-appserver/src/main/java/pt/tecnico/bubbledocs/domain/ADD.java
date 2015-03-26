package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class ADD extends ADD_Base {

	public ADD (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

    public ADD (FunctionArguments arg1, FunctionArguments arg2) {
        super();
    	addArgs(arg1);
    	addArgs(arg2);
    }

	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.visit (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.visit (this)   ; }
    public String  toString         ()                { return Printer.visit  (this)   ; }

    public int     getContentValue  ()                { return Getter.visit   (this)   ; }
}
