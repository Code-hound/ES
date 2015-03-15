package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class MUL extends MUL_Base {
    
    public MUL(Content c1, Content c2) {
        super();
        init(c1, c2);
    }
    public int getContentValue(){
    	int value=1;
    	for(Content c : getArgsSet()){
    		value*=c.getContentValue();
    	}
    	return value;
    }
    
    public String toString(){
    	String s= "MUL";
    	return s + super.toString();
    	
    }
    
    public Element exportToXML() {
    	Element element = new Element("MUL");
    	for(Content c: getArgsSet()){
    		element.addContent(c.exportToXML());
    	}
    	return element;
    }
}
