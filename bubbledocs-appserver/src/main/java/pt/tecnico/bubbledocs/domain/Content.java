package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public abstract class Content extends Content_Base {
    
    public Content() {
        super();
    }
    
    public abstract int getContentValue();
    
    public abstract Element exportToXML();
    
   // public abstract void importFromXML();
    
}
