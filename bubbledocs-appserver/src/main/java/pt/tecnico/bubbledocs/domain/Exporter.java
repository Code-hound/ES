package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.DataConversionException;

public class Exporter {

  public Element exportToXML(User user){
      Element element = new Element("user");

      element.setAttribute("username", user.get_username());
      element.setAttribute("name"    , user.get_name()    );
      element.setAttribute("password", user.get_password());
      
      return element;
  }

  public Element exportToXML(SpreadSheet spreadsheet) {
      Element element = new Element("spreadsheet");

      element.setAttribute("id",      String.valueOf(spreadsheet.get_id()));
      element.setAttribute("name",    spreadsheet.get_spreadSheetName());
      element.setAttribute("date",    spreadsheet.get_date().toString());
      element.setAttribute("rows",    String.valueOf(spreadsheet.get_numberRows()   ));
      element.setAttribute("columns", String.valueOf(spreadsheet.get_numberColumns()));

      for(Cell cell : spreadsheet.getCellsSet())
	      element.addContent(exportToXML(cell));

      return element;
  }

  public Element exportToXML(Cell cell){
      Element element = new Element("cell");
      Element content = exportToXML(cell.getContent());

      element.setAttribute("row"      , String.valueOf(cell.get_cellRow()   ));
      element.setAttribute("column"   , String.valueOf(cell.get_cellColumn()));
      element.setAttribute("protected", String.valueOf(cell.get_protected() ));
      element.addContent(content);

      return element;
  }
  
  public Element exportToXML(Content content) {
      if (content instanceof Literal)
	      return exportToXML((Literal) content);
      if (content instanceof Function)
	      return exportToXML((Function) content);
      if (content instanceof Reference)
	      return exportToXML((Reference) content);
      return null;
  }
  
  public Element exportToXML(Literal literal) {
      Element element = new Element("Literal");

      element.setAttribute("number", String.valueOf(literal.get_number()));

      return element;
  }
  
  public Element exportToXML(Function function) {
      String classname = function.getClass().getName();
      Element element = new Element(classname);

      for(Content c: function.getArgsSet()){
	      element.addContent(c.exportToXML());
      }

      return element;
  }
  
  public Element exportToXML(Reference reference) {
      Element element = new Element("Reference");

      element.setAttribute("row",    String.valueOf(reference.getCell().get_cellRow()   ));
      element.setAttribute("column", String.valueOf(reference.getCell().get_cellColumn()));

      return element;
  }
}
