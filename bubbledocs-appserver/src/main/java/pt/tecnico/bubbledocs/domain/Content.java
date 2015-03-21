package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public abstract class Content extends Content_Base {
	
    public Content() {
        super();
    }

	public abstract int     getContentValue  ()                ;
	public abstract void    importFromXML    (Element element) ;
	public abstract Element exportToXML      ()                ;
    public abstract String  toString         ()                ;  
}
