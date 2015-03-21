package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public abstract class Function extends Function_Base {
    
    public Function() {
        super();
    }
    
    protected void init(Content arg1,Content arg2){
    	addArgs(arg1);
    	addArgs(arg2);
    }
    
    public abstract int     getContentValue  ()                ;
	public abstract void    importFromXML    (Element element) ;
	public abstract Element exportToXML      ()                ;
    public abstract String  toString         ()                ;
}
