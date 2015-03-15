package pt.tecnico.bubbledocs.domain;

public class Cell extends Cell_Base {
    
    public Cell(int row, int column) {
        super();
        set_cellRow(row);
        set_cellColumn(column);
    }
    
    public String ToString(){
    	return "="+getContent().getContentValue();
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
}
