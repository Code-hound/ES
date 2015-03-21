package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class Importer {

	public static void visit (User user, Element element){
      /*Element element = new Element("user");

      element.setAttribute("username", user.get_username());
      element.setAttribute("name"    , user.get_name()    );
      element.setAttribute("password", user.get_password());
      
      return element;*/
  }

	public static void visit (SpreadSheet spreadsheet, Element element) {
      /*Element element = new Element("spreadsheet");

      element.setAttribute("id",      String.valueOf(get_id()));
      element.setAttribute("name",    get_spreadSheetName());
      element.setAttribute("date",    get_date().toString());
      element.setAttribute("rows",    String.valueOf(get_numberRows()   ));
      element.setAttribute("columns", String.valueOf(get_numberColumns()));

      for(Cell cell : this.getCellsSet())
	      element.addContent(exportToXML(cell));

      return element;*/
  }

  public static void visit (Cell cell, Element element) {
    /*
    	try {
    	set_cellRow(element.getAttribute("row").getIntValue());
    	set_cellColumn(element.getAttribute("column").getIntValue());

    	Element content = element.getChild("content");
    	
    	
    	set_protected(element.getAttribute("protected").getBooleanValue());
    	} catch (DataConversionException e) { 
    		//throw new ImportDocumentException();
	}
*/
    }
  
    public static void visit (Literal literal, Element element){
    /*
    	try {
			set_number(element.getAttribute("number").getIntValue());
		} catch (DataConversionException e) {
		//	throw new ImportDocumentException();
		}
*/
    }
  
    public static void visit (Function function, Element element) {
    	/*
		Element arg1 = contentElement.getChild("arg1");
		Element arg2 = contentElement.getChild("arg2");
    	Content c1 = new Content();
    	Content c2 = new Content();
    	c1.importFromXML(arg1);
    	c2.importFromXML(arg2);
    	setContent(c1);
    	setContent(c2);
    	*/
    }
  
    public static void visit (Reference reference, Element element) {
    	/*
    	Reference reference = new Reference();
    	
    	Element cell = contentElement.getChild("cell");
    	Cell c= new Cell();
    	c.importFromXML(cell);
    	setCell(c);
    	*/
    }

    public static void visit (ADD function, Element element) {}
    public static void visit (SUB function, Element element) {}
    public static void visit (MUL function, Element element) {}
    public static void visit (DIV function, Element element) {}
    public static void visit (AVG function, Element element) {}
    public static void visit (PRD function, Element element) {}
}
