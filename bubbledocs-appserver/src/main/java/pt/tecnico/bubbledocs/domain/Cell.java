package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.DataConversionException;

public class Cell extends Cell_Base {
	
    public Cell(int row, int column) {
        super();
        set_cellRow(row);
        set_cellColumn(column);
    }
    
    public String ToString(){
    	if(getContent()!= null)
    		return "="+getContent().toString();
    	return "=";
    }
    
    public int getValue(){
    	//try{
    	Content c=getContent();
    	return 	c.getContentValue();
    	//}catch(InvalidValueException){}
    }
    
    public void toogleProtection(){
    	set_protected(!get_protected());
    }
    
    public Element exportToXML() {
    	Element element = new Element("cell");
    	element.setAttribute("row", ""+get_cellRow());
    	element.setAttribute("column",""+get_cellColumn());
    	element.setAttribute("protected",String.valueOf(get_protected()));
    	element.addContent(getContent().exportToXML());

    	return element;
    }
    
    public void importFromXML(Element cellElement) {
    	try {
    	set_cellRow(cellElement.getAttribute("row").getIntValue());
    	set_cellColumn(cellElement.getAttribute("column").getIntValue());

    	Element content = cellElement.getChild("content");
    	
    	
    	set_protected(cellElement.getAttribute("protected").getBooleanValue());
    	} catch (DataConversionException e) { 
    		//throw new ImportDocumentException();
	}
    }

}
