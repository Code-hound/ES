package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Reference extends Reference_Base {
	
    public Reference(Cell c) {
        super();
        setCell(c);
    }
    
    public Reference (SpreadSheet sheet, int row, int column) {
    	super();
    	for (Cell c : sheet.getCellsSet()) {
    		if (c.get_cellColumn() == row && c.get_cellRow() == column) {
    			setCell(c);
    			break;
    		}
    	}
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
    	Element cellElement= new Element("cell");
    	element.addContent(cellElement);
    	cellElement.addContent(getCell().exportToXML());
    	return element;
    }
    
    public void importFromXML(Element contentElement) {
    	/*
    	Element cell = contentElement.getChild("cell");
    	Cell c= new Cell();
    	c.importFromXML(cell);
    	setCell(c);
    	*/
    }
}
