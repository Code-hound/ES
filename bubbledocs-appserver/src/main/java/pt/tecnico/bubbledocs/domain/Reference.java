package pt.tecnico.bubbledocs.domain;

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
}
