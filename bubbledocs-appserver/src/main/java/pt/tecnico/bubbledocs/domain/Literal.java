package pt.tecnico.bubbledocs.domain;

import org.jdom2.DataConversionException;
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
    
    public void importFromXML(Element literalElement){
    	try {
			set_number(literalElement.getAttribute("number").getIntValue());
		} catch (DataConversionException e) {
		//	throw new ImportDocumentException();
		}
    }
}
