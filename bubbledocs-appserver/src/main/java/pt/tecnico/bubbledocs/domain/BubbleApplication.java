package pt.tecnico.bubbledocs.domain;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Hashtable<K,V>;
import java.util.ArrayList;


public class BubbleApplication {
	private static int sheetId = 1;

	public static void main(String[] args) {
		populateDomain();
		
		BubbleDocs bd = BubbleDocs.getInstance();
		
		//Write all registered users info
		List<String, User> user_info_list = bd.viewUsers();


		//Write pf and ra spreadsheet names
		List<String> spreadsheet_name_list = new ArrayList<String>;

		List<String> pf = bd.viewUser("pf").viewSheets();
		List<String> ra = bd.viewUser("ra").viewSheets();
		spreadsheet_name_list.addAll(pf).addAll(ra);
		
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