package pt.tecnico.bubbledocs.domain;

public class AVG extends AVG_Base {
    
    public AVG(Reference r1, Reference r2) {
        super();
        init(r1,r2);
    }
    
    public int getContentValue(){
    	int value=0;
    	//FIX ME
    	return value;
    }
    
    public String toString(){
    	String s= "AVG";
    	return s + super.toString();
    	
    }
}
