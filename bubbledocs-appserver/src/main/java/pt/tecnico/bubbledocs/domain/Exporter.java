package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Exporter {

	public static Element visit(User type){
      String classname = type.getClass().getName();
      Element element = new Element(classname);

      element.setAttribute("username", type.get_username());
      element.setAttribute("name"    , type.get_name()    );
      element.setAttribute("password", type.get_password());

      XMLOutputter xml = new XMLOutputter();
      System.out.println("USER");
      System.out.println(xml.outputString(element) + "\n");

      return element;
  }

  public static Element visit(SpreadSheet type) {
      String classname = type.getClass().getName();
      Element element = new Element(classname);

      element.setAttribute("id",      String.valueOf(type.get_id()));
      element.setAttribute("name",    type.get_spreadSheetName());
      element.setAttribute("date",    type.get_date().toString());
      element.setAttribute("rows",    String.valueOf(type.get_numberRows()   ));
      element.setAttribute("columns", String.valueOf(type.get_numberColumns()));

      XMLOutputter xml = new XMLOutputter();
      System.out.println("SPREADSHEET 1");
      System.out.println(xml.outputString(element) + "\n");

      for(Cell c : type.getCellsSet())
	      if(c.getContent() != null)
	           element.addContent(c.exportToXML());

      System.out.println("SPREADSHEET 2");
      System.out.println(xml.outputString(element) + "\n");

      return element;
  }

  public static Element visit(Cell type){
      String classname = type.getClass().getName();
      Element element = new Element(classname);

      element.setAttribute("row"      , String.valueOf(type.get_cellRow()   ));
      element.setAttribute("column"   , String.valueOf(type.get_cellColumn()));
      element.setAttribute("protected", String.valueOf(type.get_protected() ));

      XMLOutputter xml = new XMLOutputter();
      System.out.println("CELL 1");
      System.out.println(xml.outputString(element) + "\n");

      Element c = type.getContent().exportToXML();
      element.addContent(c);

      System.out.println("CELL 2");
      System.out.println(xml.outputString(element) + "\n");

      return element;
  }
  
  public static Element visit(Function type) {
      String classname = type.getClass().getName();
      Element element = new Element(classname);

      XMLOutputter xml = new XMLOutputter();
      System.out.println("FUNCTION 1");
      System.out.println(xml.outputString(element) + "\n");

      for(Content c: type.getArgsSet())
		  if(c != null)
	          element.addContent(c.exportToXML());

      System.out.println("FUNCTION 2");
      System.out.println(xml.outputString(element) + "\n");

      return element;
  }

  public static Element visit(Literal type) {
      String classname = type.getClass().getName();
      Element element = new Element(classname);

      element.setAttribute("number", String.valueOf(type.get_number()));

      XMLOutputter xml = new XMLOutputter();
      System.out.println("LITERAL");
      System.out.println(xml.outputString(element) + "\n");

      return element;
  }

  public static Element visit(Reference type) {
      String classname = type.getClass().getName();
      Element element = new Element(classname);

      element.setAttribute("row",    String.valueOf(type.getCell().get_cellRow()   ));
      element.setAttribute("column", String.valueOf(type.getCell().get_cellColumn()));

      XMLOutputter xml = new XMLOutputter();
      System.out.println("REFERENCE");
      System.out.println(xml.outputString(element) + "\n");

      return element;
  }
}
