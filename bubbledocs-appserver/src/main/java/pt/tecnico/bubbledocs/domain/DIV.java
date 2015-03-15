package pt.tecnico.bubbledocs.domain;

public class DIV extends DIV_Base {
    
    public DIV(Content c1,Content c2) {
        super();
        init(c1,c2);
    }
    
    public int getContentValue(){
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	
    	/*if(contents[1].getContentValue()==0){
    	 * throw new DivisionByZeroException();
    	 * }
    	 * */
    	
    	return	contents[0].getContentValue() - 
    			contents[1].getContentValue();
    }
    
    public String toString(){
    	String s= "DIV";
    	return s+super.toString();
    	
    }
}
