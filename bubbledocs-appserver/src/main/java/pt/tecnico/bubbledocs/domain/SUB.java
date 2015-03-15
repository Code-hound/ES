package pt.tecnico.bubbledocs.domain;

public class SUB extends SUB_Base {
    
    public SUB(Content arg1, Content arg2){
        super();
    	/*if((arg1 instanceof Function)||(arg2 instanceof Function)){
    	throw new InvalidArgumentException("Function");
    	}*/
    init(arg1,arg2);
    }
    
    public int getContentValue(){
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	
    	return	contents[0].getContentValue() - 
    			contents[1].getContentValue();
    }
    
    
    public String toString(){
    	String s= "SUB(";
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	s+=contents[0].toString();
    	s+=",";
    	s+=contents[1].toString();
    	s+=")";
    	
    	return s;
    	
    }
}
