package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class ADD extends ADD_Base {
    
    public ADD(Content c1,Content c2) {
        super();
        init(c1,c2);
    }
    
    public int getContentValue(){
    	int value=0;
    	for(Content c : getArgsSet()){
    		value+=c.getContentValue();
    	}
    	return value;
    }
    public String toString(){
    	String s= "ADD";
    	return s + super.toString();
    	
    }
    
    public Element exportToXML() {
    	Element element = new Element("ADD");
    	for(Content c: getArgsSet()){
    		element.addContent(c.exportToXML());
    	}
    	return element;
    }
}
