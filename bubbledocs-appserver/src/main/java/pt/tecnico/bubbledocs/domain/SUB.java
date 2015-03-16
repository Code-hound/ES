package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

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

	@Override
	public void importFromXML(Element contentElement) {
		Element arg1 = contentElement.getChild("arg1");
		Element arg2 = contentElement.getChild("arg2");
    	Content c1 = new Content();
    	Content c2 = new Content();
    	c1.importFromXML(arg1);
    	c2.importFromXML(arg2);
    	setContent(c1);
    	setContent(c2);
		
	}
}
