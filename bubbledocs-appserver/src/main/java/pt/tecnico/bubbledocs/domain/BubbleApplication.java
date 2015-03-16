package pt.tecnico.bubbledocs.domain;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;

public class BubbleApplication {

	public static void main(String[] args) {
		populateDomain();
		
		/* ..... */
	
	}
	
	@Atomic
	static void populateDomain() {
		BubbleDocs bd = BubbleDocs.getInstance();
		
		User user1 = new User("pf","Paul Door","sub");
		bd.addUser(user1);
		User user2 = new User("ra","Step Rabbit","cor");
		bd.addUser(user2);
		
		Spreadsheet sheet1 = new SpreadSheet("Notas ES",300,20);
		user1.addSheet(sheet1);
		Content content1 = new Literal(5);
		sheet1.addContent(content1,3,4);
		Content content2 = new Reference(5,6);
		sheet1.addContent(content2,1,1);
		Content content3 = new ADD(new Literal(2),new Reference(3,4)); //FIXME-SYNTAX
		sheet1.addContent(content3,5,6);
		Content content4 = new DIV(new Reference(1,1),new Reference(3,4));
		sheet1.addContent(content4,2,2);	
	}

}