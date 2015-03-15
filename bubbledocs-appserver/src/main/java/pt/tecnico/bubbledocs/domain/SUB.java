package pt.tecnico.bubbledocs.domain;

public class SUB extends SUB_Base {
    
    public SUB() {
        super();
    }
    
    public SUB(Content arg1, Content arg2){
        /*if((arg1 instanceof Function)||(arg2 instanceof Function)){
    	throw new InvalidArgumentException("Function");
    }*/
    init(arg1,arg2);
    }
    
    public int getContentValue(){
    	boolean first=true;
    	int value=0;
    	for(Content c : getArgsSet()){
    		if(first){
    			value=c.getContentValue();
    		}else{
    			value-=c.getContentValue();
    		}
    	}
    	return value;
    }
}
