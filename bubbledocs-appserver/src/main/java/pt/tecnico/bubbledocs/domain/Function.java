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
    
}
