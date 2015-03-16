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
		
		Spreadsheet sheet1 = new Sheet();
		user1.addSheet(sheet1);
	}

}