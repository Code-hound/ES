package pt.tecnico.bubbledocs.domain;

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
}
