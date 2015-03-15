package pt.tecnico.bubbledocs.domain;

public class Reference extends Reference_Base {
    
    public Reference() {
        super();
    }
    
    public int getContentValue(){
    	Cell c = getCell();
    	Content cont= c.getContent();
    	return cont.getContentValue();
    }
}
