package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class MUL extends MUL_Base {

	public MUL (SpreadSheet sheet, Element element) {
		super();
		importFromXML(element, sheet);
	}

    public MUL (FunctionArguments arg1, FunctionArguments arg2) {
        super();
    	addArgs(arg1);
    	addArgs(arg2);
    }

	public void    importFromXML    (Element element, SpreadSheet sheet) { Importer.visit (this, element, sheet) ; }
	public Element exportToXML      ()                { return Exporter.visit (this)   ; }
    public String  toString         ()                { return Printer.visit  (this)   ; }

    public int     getContentValue  ()                { return Getter.visit   (this)   ; }
}
