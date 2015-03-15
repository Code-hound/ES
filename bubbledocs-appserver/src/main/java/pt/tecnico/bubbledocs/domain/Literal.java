package pt.tecnico.bubbledocs.domain;

public class Literal extends Literal_Base {
    
    public Literal(int literal) {
        super();
        set_number(literal);
    }
    
    public int getContentValue(){
    	return get_number();
    }
    
    public String toString(){
    	String s="";
    	return s+ getContentValue();
    }
}
