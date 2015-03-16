package pt.tecnico.bubbledocs.domain;

public abstract class RangedFunction extends RangedFunction_Base {
    
    public RangedFunction() {
        super();
    }
    
    public RangedFunction(Reference r1, Reference r2){
    	super();
	    init(r1,r2);
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
