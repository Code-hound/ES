package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class PRD extends PRD_Base {
    
    public PRD(Reference r1, Reference r2) {
        super();
        init(r1,r2);
    }
    
    public int getContentValue(){
    	int value=0;
    	//FIX ME
    	return value;
    }
    
    public String toString(){
    	String s= "PRD";
    	
    	return s+super.toString();
    	
    }
    
    public Element exportToXML() {
    	Element element = new Element("PRD");
    	for(Content c: getArgsSet()){
    		element.addContent(c.exportToXML());
    	}
    	return element;
    }

	@Override
	public void importFromXML(Element contentElement) {
		// TODO Auto-generated method stub
		
	}
}
