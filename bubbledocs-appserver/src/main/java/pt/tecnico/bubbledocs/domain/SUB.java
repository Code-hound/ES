package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class SUB extends SUB_Base {
    
    public SUB(Content c1, Content c2){
        super();
    	/*if((arg1 instanceof Function)||(arg2 instanceof Function)){
    	throw new InvalidArgumentException("Function");
    	}*/
    init(c1,c2);
    }
    
    public int getContentValue(){
    	Content contents[]=new Content[2];
    	
    	getArgsSet().toArray(contents);
    	
    	return	contents[0].getContentValue() - 
    			contents[1].getContentValue();
    }
    
    
    public String toString(){
    	String s= "SUB";

    	return s+super.toString();
    	
    }
    
    @Override
    public Element exportToXML() {
    	Element element = new Element("SUB");
    	for(Content c: getArgsSet()){
    		element.addContent(c.exportToXML());
    	}
    	return element;
    }
}
