package pt.tecnico.bubbledocs.domain;

public abstract class RangedFunction extends RangedFunction_Base {
    
    public RangedFunction() {
        super();
    }
    
    public RangedFunction(Reference arg1, Reference arg2){
    	super();
	    init(arg1,arg2);
    }
    
    public String toString(){
    	String s="(";
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	s+=contents[0].toString();
    	s+=";";
    	s+=contents[1].toString();
    	s+=")";
    	
    	return s;
    }
}
