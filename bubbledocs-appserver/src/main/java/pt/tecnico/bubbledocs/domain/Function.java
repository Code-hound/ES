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
    
    @Override
	public void importFromXML(Element contentElement) {
    	/*
		Element arg1 = contentElement.getChild("arg1");
		Element arg2 = contentElement.getChild("arg2");
    	Content c1 = new Content();
    	Content c2 = new Content();
    	c1.importFromXML(arg1);
    	c2.importFromXML(arg2);
    	setContent(c1);
    	setContent(c2);
    	*/
		
	}
}
