package pt.tecnico.bubbledocs.domain;

public class ADD extends ADD_Base {
    
    public ADD() {
        super();
    }
    
    public int getContentValue(){
    	int value=0;
    	for(Content c : getArgsSet()){
    		value+=c.getContentValue();
    	}
    	return value;
    }
}
