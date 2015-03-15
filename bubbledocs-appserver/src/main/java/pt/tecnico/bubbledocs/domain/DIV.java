package pt.tecnico.bubbledocs.domain;

public class DIV extends DIV_Base {
    
    public DIV() {
        super();
    }
    
    public int getContentValue(){
    	boolean first=true;
    	int value=0;
    	for(Content c : getArgsSet()){
    		if(first){
    			value=c.getContentValue();
    		}else{
    			if(c.getContentValue()!=0){
    				value-=c.getContentValue();
    				}else{
    					//throw new DivisionByZeroException();
    				}
    		}
    	}
    	return value;
    }
}
