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
    	String s= "ADD(";
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	s+=contents[0].toString();
    	s+=",";
    	s+=contents[1].toString();
    	s+=")";
    	
    	return s;
    	
    }
}
