package pt.tecnico.bubbledocs.domain;

public class ADD extends ADD_Base {
    
    public ADD(Content c1,Content c2) {
        super();
        init(c1,c2);
    }
    
    public int getContentValue(){
    	int value=0;
    	for(Content c : getArgsSet()){
    		value+=c.getContentValue();
    	}
    	return value;
    }
    public String toString(){
    	String s= "ADD";
    	return s + super.toString();
    	
    }
}
