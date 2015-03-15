package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Reference extends Reference_Base {
    
    public Reference(Cell c) {
        super();
        setCell(c);
    }
    
    public int getContentValue(){
    	Cell c = getCell();
    	Content cont= c.getContent();
    	return cont.getContentValue();
    }
    
    public String toString(){
    	String s="";
    	Cell c = getCell();
    	
    	s+=c.get_cellRow();
    	s+=";";
    	s+=c.get_cellColumn();
    	return s;
    }
    
    public Element exportToXML() {
    	Element element = new Element("Reference");
    	element.addContent(getCell().exportToXML());
    	return element;
    }
    /*
    public void importFromXML(Element contactElement) {
    	setA(contactElement.getAttribute("Reference").getValue());
    	try {
    	    setPhoneNumber(contactElement.getAttribute("phoneNumber").getIntValue());
    	} catch (DataConversionException e) { 
    	    throw new ImportDocumentException();
    	}
        }*/
}
