package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class AVG extends AVG_Base {
    
    public AVG(Reference r1, Reference r2) {
        super();
        init(r1,r2);
    }
    
    public int getContentValue(){
    	int value=0;
    	//FIX ME
    	return value;
    }
    
    public String toString(){
    	String s= "AVG";
    	return s + super.toString();
    	
    }
    
    public Element exportToXML() {
    	Element element = new Element("AVG");
    	for(Content c: getArgsSet()){
    		element.addContent(c.exportToXML());
    	}
    	return element;
    }

	@Override
	public void importFromXML(Element contentElement) {
		/*
		Element arg1 = contentElement.getChild("arg1");
		Element arg2 = contentElement.getChild("arg2");
    	Reference r1 = new Reference();
    	Reference r2 = new Reference();
    	r1.importFromXML(arg1);
    	r2.importFromXML(arg2);
    	setReference(r1);
    	setReference(r2);
		*/
	}
}
