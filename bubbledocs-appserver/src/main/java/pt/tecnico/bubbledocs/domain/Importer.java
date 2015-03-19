package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.DataConversionException;

public class Importer {

  private User importToUser(Element element){
      /*Element element = new Element("user");

      element.setAttribute("username", user.get_username());
      element.setAttribute("name"    , user.get_name()    );
      element.setAttribute("password", user.get_password());
      
      return element;*/
      return null;
  }

  private SpreadSheet importToSpreadsheet(Element element) {
      /*Element element = new Element("spreadsheet");

      element.setAttribute("id",      String.valueOf(get_id()));
      element.setAttribute("name",    get_spreadSheetName());
      element.setAttribute("date",    get_date().toString());
      element.setAttribute("rows",    String.valueOf(get_numberRows()   ));
      element.setAttribute("columns", String.valueOf(get_numberColumns()));

      for(Cell cell : this.getCellsSet())
	      element.addContent(exportToXML(cell));

      return element;*/
      return null;
  }

    private Cell importToCell(Element element) {
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
      return null;
    }
  
    private Literal importToLiteral(Element element){
    /*
    	try {
			set_number(element.getAttribute("number").getIntValue());
		} catch (DataConversionException e) {
		//	throw new ImportDocumentException();
		}
*/
      return null;
    }
  
    private Function importToFunction(Element element) {
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
      return null;
    }
  
    private Reference importToReference(Element element) {
    	/*
    	Reference reference = new Reference();
    	
    	Element cell = contentElement.getChild("cell");
    	Cell c= new Cell();
    	c.importFromXML(cell);
    	setCell(c);
    	*/
      return null;
    }
    
    public Object importFromXML(Element e) {
        String classname = e.getName();

        switch (classname) {
          case "user"       : return importToUser       (e);
	  case "spreadsheet": return importToSpreadsheet(e);
	  case "cell"       : return importToCell       (e);
	  case "Literal"    : return importToLiteral    (e);
	  case "Reference"  : return importToReference  (e);
	  case "Function"   : return importToFunction   (e);
	  default           : return null;
	}
    }
}
