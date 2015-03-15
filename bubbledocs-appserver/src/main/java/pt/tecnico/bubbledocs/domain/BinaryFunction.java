package pt.tecnico.bubbledocs.domain;

public abstract class BinaryFunction extends BinaryFunction_Base {
    
    public BinaryFunction() {
        super();
    }
    
    public String toString(){
    	String s="(";
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	s+=contents[0].toString();
    	s+=",";
    	s+=contents[1].toString();
    	s+=")";
    	return s;
    }
}
