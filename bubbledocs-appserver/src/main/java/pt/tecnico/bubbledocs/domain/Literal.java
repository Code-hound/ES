package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Literal extends Literal_Base {
    
    public Literal(int literal) {
        super();
        set_number(literal);
    }
    
    public int getContentValue(){
    	return get_number();
    }
    
    public String toString(){
    	String s="";
    	return s+ getContentValue();
    }
    
    public Element exportToXML() {
    	Element element = new Element("Literal");
    	element.setAttribute("number",""+get_number());
    	return element;
    }
}
