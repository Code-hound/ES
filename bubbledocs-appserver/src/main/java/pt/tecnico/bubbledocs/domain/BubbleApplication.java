package pt.tecnico.bubbledocs.domain;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;

import java.util.List;
import java.util.ArrayList;
import static java.lang.System.out;
import org.jdom2.output.XMLOutputter;


public class BubbleApplication {
	private static int sheetId = 1;

	public static void main(String[] args) {
		populateDomain();
		
		BubbleDocs bd = BubbleDocs.getInstance();
		
		//Write all registered users info
		List<User> user_info_list = new ArrayList<User>(bd.getUsersSet());

		System.out.println("Users Info:");
		for (User u: user_info_list)
			System.out.println(u);
		System.out.println();
		


		//Write pf and ra spreadsheet names
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

		
		
		//Write pf spreadsheet xml
		XMLOutputter xml = new XMLOutputter();
		
		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s: spreadsheet_list_pf)
			System.out.println(xml.outputString(s.exportToXML()));
		System.out.println();
		
	}
	
	@Atomic
	static void populateDomain() {
		BubbleDocs bd = BubbleDocs.getInstance();
		
		User user1 = new User("pf","Paul Door","sub");
		bd.addUser(user1);
		User user2 = new User("ra","Step Rabbit","cor");
		bd.addUser(user2);
		
		SpreadSheet sheet1 = new SpreadSheet(user1, sheetId, "Notas ES",null, 300,20);
		//user1.addSheet(sheet1);
		Content content1 = new Literal(5);
		sheet1.addContent(content1,3,4);
		Content content2 = new Reference(sheet1,5,6);
		sheet1.addContent(content2,1,1);
		Content content3 = new ADD(new Literal(2),new Reference(sheet1,3,4));
		sheet1.addContent(content3,5,6);
		Content content4 = new DIV(new Reference(sheet1,1,1),new Reference(sheet1,3,4));
		sheet1.addContent(content4,2,2);
	}

}