package pt.tecnico.bubbledocs;

import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.jdom2.output.XMLOutputter;



import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.TransactionManager;
import pt.tecnico.bubbledocs.domain.*;

public class BubbleApplication {

	public static void main(String[] args) {
		TransactionManager tm = FenixFramework.getTransactionManager();
		if (tm==null)
			System.out.println("Transaction Manager null!");
		boolean committed = false;
		
		try {
			tm.begin();
			System.out.println("Began");
			
			BubbleDocs bd = BubbleDocs.getInstance();
			System.out.println("Got");
			
			populateDomain(bd);
			System.out.println("Populated");
			
			writeUsers(bd);
			writeUserSheets(bd);
			System.out.println("Wrote");
			
			tm.rollback();
			System.out.println("Reseting database");
			
			System.exit(0);
		} catch (SecurityException |
				 IllegalStateException |
				 NotSupportedException |
				 SystemException |
				 RollbackException |
				 HeuristicMixedException |
				 HeuristicRollbackException ex) {
			// TODO Auto-generated catch block
			System.out.println("Caught transaction exception: " + ex);
			ex.printStackTrace();
		} 
	}
	
	
	static void populateDomain(BubbleDocs bd) throws NotSupportedException,
			                            SystemException,
			                            SecurityException,
			                            IllegalStateException,
			                            RollbackException,
			                            HeuristicMixedException,
			                            HeuristicRollbackException {
		
		XMLOutputter xml = new XMLOutputter();

		User user1 = bd.createUser("fms", "fms", "Francisco de Matos Silveira", "email@email.email");
		User user2 = bd.createUser("lrg", "lrg", "Luis Ribeiro Gomes", "email@email.email");

		//Francisco creates a spreadsheet called "Notas ES"
		SpreadSheet sheet1 = bd.createSpreadSheet(user1, "Notas ES", 300, 20);
		//Luis gets writing access to the spreadsheet
		bd.addAccessToSpreadSheet(user2, sheet1, 2);

		//System.out.println(sheet1);
		//Content added to the spreadsheet
		Content content1 = new Literal(5);
		sheet1.addContent(content1, 3, 4);
		Content content2 = new Reference(sheet1, 5, 6);
		sheet1.addContent(content2, 1, 1);
		Content content3 = new ADD(new Literal(2), new Reference(sheet1, 3, 4));
		sheet1.addContent(content3, 5, 6);
		Content content4 = new DIV(new Reference(sheet1, 1, 1), new Reference(
				sheet1, 3, 4));
		sheet1.addContent(content4, 2, 2);
		
		//System.out.println(sheet1);
		//System.out.println(xml.outputString(sheet1.exportToXML()));

		//bd.addSpreadSheet(sheet1);
		//é preciso re-adicionar a spreadsheet? à partida ela ficava alterada
		
		//return bd;
	}
	
	static void writeUsers(BubbleDocs bd){
		//BubbleDocs bd = BubbleDocs.getInstance();

		// List<User> user_info_list = new ArrayList<User>(bd.getUsersSet());

		System.out.println("Users Info:" + bd.getUsersSet().size());
		for (User u : bd.getUsersSet()) {
			System.out.println(u);
		}
		System.out.println();
	}
	
	static void writeUserSheets(BubbleDocs bd){
		//BubbleDocs bd = BubbleDocs.getInstance();
		
		User fms = bd.getUserByUsername("fms");
		User lrg = bd.getUserByUsername("lrg");

		List<SpreadSheet> spreadsheet_list_fms = bd.getSpreadSheetByOwner(fms);
		List<SpreadSheet> spreadsheet_list_lrg = bd.getSpreadSheetByOwner(lrg);

		System.out.println(fms.getName()+"'s Spreadsheet:");
		for (SpreadSheet s : spreadsheet_list_fms)
			System.out.println(s);
		System.out.println();

		System.out.println(lrg.getName()+"'s Spreadsheet:");
		for (SpreadSheet s : spreadsheet_list_lrg)
			System.out.println(s);
		System.out.println();
	}
	
	/*
	static void writePfSheet(){
		// Write pf spreadsheet xml

		// List<SpreadSheet> spreadsheet_list_pf =
		// bd.getSpreadSheetByName("pf");

		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s : spreadsheet_list_pf) {
			System.out.println(xml.outputString(s.exportToXML()));
		}
		System.out.println();
	}
	*/
	/*
	static void removePfSheet(){
		// BubbleDocs bd = BubbleDocs.getInstance();
		// XMLOutputter xml = new XMLOutputter();

		List<SpreadSheet> notas_es = bd.getSpreadSheetByName("Notas ES");

		System.out.println("pf Spreadsheet XML:");
		for (SpreadSheet s : notas_es) {
			System.out.println(xml.outputString(s.exportToXML()));
		}
		System.out.println();

		bd.removeSpreadSheetByOwner(user1, "Notas ES");
		
	}
	*/
}
