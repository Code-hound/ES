package pt.tecnico.bubbledocs;

import java.util.List;

import org.jdom2.output.XMLOutputter;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.bubbledocs.domain.*;

public class BubbleApplication {
	public static void main(String[] args) {
		populateDomain();
		//writeUsers();
		//writeUserSheets();
		//writePfSheet();
		//removePfSheet();
	}
    
	@Atomic
	static void populateDomain() {
    	
		BubbleDocs bd = BubbleDocs.getInstance();

		User user1 = new User("pf","Paul Door","sub");
		bd.addUser(user1);
		User user2 = new User("ra","Step Rabbit","cor");
		bd.addUser(user2);

		SpreadSheet sheet1 = new SpreadSheet(user1, "Notas ES", 300,20);

		Content content1 = new Literal(5);
		sheet1.addContent(content1,3,4);
		Content content2 = new Reference(sheet1,5,6);
		sheet1.addContent(content2,1,1);
		Content content3 = new ADD(new Literal(2),new Reference(sheet1,3,4));
		sheet1.addContent(content3,5,6);
		Content content4 = new DIV(new Reference(sheet1,1,1),new Reference(sheet1,3,4));
		sheet1.addContent(content4,2,2);
		System.out.println(sheet1);
		bd.addSpreadSheet(sheet1);
    	
		//return bd;
	//}
    
	//@Atomic
	//static void writeUsers(){
		//BubbleDocs bd = BubbleDocs.getInstance();

		//List<User> user_info_list = new ArrayList<User>(bd.getUsersSet());

		System.out.println("Users Info:" + bd.getUsersSet().size());
		for (User u: bd.getUsersSet()){
			System.out.println(u);}
		System.out.println();
	//}

	//@Atomic
	//static void writeUserSheets(){
		//BubbleDocs bd = BubbleDocs.getInstance();
		
		List<SpreadSheet> spreadsheet_list_pf = bd.getSpreadSheetByName("pf");
		List<SpreadSheet> spreadsheet_list_ra = bd.getSpreadSheetByName("ra");
		
		System.out.println("pf Spreadsheet Names:");
		for (SpreadSheet s: spreadsheet_list_pf)
			System.out.println(s.get_spreadSheetName());
		System.out.println();
		
		System.out.println("ra Spreadsheet Names:");
		for (SpreadSheet s: spreadsheet_list_ra)
			System.out.println(s.get_spreadSheetName());
		System.out.println();
	//}
	
	//@Atomic
	//static void writePfSheet(){
		//Write pf spreadsheet xml
		//BubbleDocs bd = BubbleDocs.getInstance();
		XMLOutputter xml = new XMLOutputter();
		
		//List<SpreadSheet> spreadsheet_list_pf = bd.getSpreadSheetByName("pf");
				
		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s : spreadsheet_list_pf){
			System.out.println(xml.outputString(Exporter.exportToXML(s)));
		}
		System.out.println();
	//}
	
	//@Atomic
	//static void removePfSheet(){
		//BubbleDocs bd = BubbleDocs.getInstance();
		//XMLOutputter xml = new XMLOutputter();
		
		List<SpreadSheet> notas_es = bd.getSpreadSheetByName("Notas ES");

		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s : notas_es) {
			System.out.println(xml.outputString(Exporter.exportToXML(s)));
		}

	
		bd.removeSpreadSheetByOwner("pf", "Notas ES");		
	}
}
