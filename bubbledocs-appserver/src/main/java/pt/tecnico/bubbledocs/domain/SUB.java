package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class SUB extends SUB_Base {

	public SUB (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

    public SUB (FunctionArguments arg1, FunctionArguments arg2) {
        super();
    	addArgs(arg1);
    	addArgs(arg2);
    }
    
	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.use (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.use (this)   ; }
    public String  toString         ()                { return Printer.use  (this)   ; }

    public int     getContentValue  ()                { return Getter.use   (this)   ; }
}
