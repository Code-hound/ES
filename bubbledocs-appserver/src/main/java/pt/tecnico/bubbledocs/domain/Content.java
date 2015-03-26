package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public abstract class Content extends Content_Base {
	
    public Content() {
        super();
    }

	public abstract void    importFromXML    (Element element, SpreadSheet sheet) ;
	public abstract Element exportToXML      ()                ;
    public abstract String  toString         ()                ;

	public abstract int     getContentValue  ()                ;
}
