package pt.tecnico.bubbledocs;

import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.jdom2.output.XMLOutputter;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.bubbledocs.domain.*;

public class BubbleApplication {
	public static void main(String[] args) {
		try {
			populateDomain();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// writeUsers();
		// writeUserSheets();
		// writePfSheet();
		// removePfSheet();
	}

	static void populateDomain() throws NotSupportedException, SystemException,
			SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException {
		FenixFramework.getTransactionManager().begin();
		BubbleDocs bd = BubbleDocs.getInstance();
		XMLOutputter xml = new XMLOutputter();

		User user1 = new User("pf", "Paul Door", "sub");
		bd.addUser(user1);
		User user2 = new User("ra", "Step Rabbit", "cor");
		bd.addUser(user2);

		SpreadSheet sheet1 = new SpreadSheet(user1.getUsername(), -1, "Notas ES", 300, 20);

		System.out.println(sheet1);

		Content content1 = new Literal(5);
		sheet1.addContent(content1, 3, 4);
		Content content2 = new Reference(sheet1, 5, 6);
		sheet1.addContent(content2, 1, 1);
		Content content3 = new ADD(new Literal(2), new Reference(sheet1, 3, 4));
		sheet1.addContent(content3, 5, 6);
		Content content4 = new DIV(new Reference(sheet1, 1, 1), new Reference(
				sheet1, 3, 4));
		sheet1.addContent(content4, 2, 2);

		System.out.println(sheet1);
		System.out.println(xml.outputString(sheet1.exportToXML()));

		bd.addSpreadSheet(sheet1);

		// return bd;
		// }

		// @Atomic
		// static void writeUsers(){
		// BubbleDocs bd = BubbleDocs.getInstance();

		// List<User> user_info_list = new ArrayList<User>(bd.getUsersSet());

		System.out.println("Users Info:" + bd.getUsersSet().size());
		for (User u : bd.getUsersSet()) {
			System.out.println(u);
		}
		System.out.println();
		// }

		// @Atomic
		// static void writeUserSheets(){
		// BubbleDocs bd = BubbleDocs.getInstance();

		List<SpreadSheet> spreadsheet_list_pf = bd.getSpreadSheetByOwner("pf");
		List<SpreadSheet> spreadsheet_list_ra = bd.getSpreadSheetByOwner("ra");

		System.out.println("pf Spreadsheet Names:");
		for (SpreadSheet s : spreadsheet_list_pf)
			System.out.println(s.getSpreadSheetName());
		System.out.println();

		System.out.println("ra Spreadsheet Names:");
		for (SpreadSheet s : spreadsheet_list_ra)
			System.out.println(s.getSpreadSheetName());
		System.out.println();
		// }

		// @Atomic
		// static void writePfSheet(){
		// Write pf spreadsheet xml
		// BubbleDocs bd = BubbleDocs.getInstance();

		// List<SpreadSheet> spreadsheet_list_pf =
		// bd.getSpreadSheetByName("pf");

		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s : spreadsheet_list_pf) {
			System.out.println(xml.outputString(s.exportToXML()));
		}
		System.out.println();
		// }

		// @Atomic
		// static void removePfSheet(){
		// BubbleDocs bd = BubbleDocs.getInstance();
		// XMLOutputter xml = new XMLOutputter();

		List<SpreadSheet> notas_es = bd.getSpreadSheetByName("Notas ES");

		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s : notas_es) {
			System.out.println(xml.outputString(s.exportToXML()));
		}
		System.out.println();

		bd.removeSpreadSheetByOwner("pf", "Notas ES");

		System.exit(0);
		FenixFramework.getTransactionManager().commit();
	}
}
