package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public abstract class RangedFunction extends RangedFunction_Base {
    
    public RangedFunction() {
        super();
    }
    
    public RangedFunction(Reference r1, Reference r2){
    	super();
	    init(r1,r2);
    }
    
	public abstract int     getContentValue  ()                ;
	public abstract void    importFromXML    (Element element) ;
	public abstract Element exportToXML      ()                ;
    public abstract String  toString         ()                ;
}
