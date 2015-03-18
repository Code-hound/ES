package pt.tecnico.bubbledocs.domain;

public class Importer {

  private User importToUser(Element element){
      /*Element element = new Element("user");

      element.setAttribute("username", user.get_username());
      element.setAttribute("name"    , user.get_name()    );
      element.setAttribute("password", user.get_password());
      
      return element;*/
  }

  private Spreadsheet importToSpreadsheet(Element element) {
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

    private Cell importToCell(Element element) {
    	try {
    	set_cellRow(cellElement.getAttribute("row").getIntValue());
    	set_cellColumn(cellElement.getAttribute("column").getIntValue());

    	Element content = cellElement.getChild("content");
    	
    	
    	set_protected(cellElement.getAttribute("protected").getBooleanValue());
    	} catch (DataConversionException e) { 
    		//throw new ImportDocumentException();
	}
    }
  
    private Literal importToLiteral(Element element){
    	try {
			set_number(literalElement.getAttribute("number").getIntValue());
		} catch (DataConversionException e) {
		//	throw new ImportDocumentException();
		}
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
		
    }
  
    private Reference importToReference(Element element) {
    	/*
    	Reference reference = new Reference();
    	
    	Element cell = contentElement.getChild("cell");
    	Cell c= new Cell();
    	c.importFromXML(cell);
    	setCell(c);
    	*/
    }
    
    public Object importFromXML(Element e) {
        switch (element.getTagName()) {
          case "user"       : return importToUser       (e);
	  case "spreadsheet": return importToSpreadsheet(e);
	  case "cell"       : return importToCell       (e);
	  case "Literal"    : return importToLiteral    (e);
	  case "Reference"  : return importToReference  (e);
	  case "Function"   : return importToFunction   (e);
	  case default      : return null;
	}
    }
}
