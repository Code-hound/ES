package pt.tecnico.bubbledocs.domain;

public class MUL extends MUL_Base {
    
    public MUL() {
        super();
    }
    public int getContentValue(){
    	int value=1;
    	for(Content c : getArgsSet()){
    		value*=c.getContentValue();
    	}
    	return value;
    }
}
