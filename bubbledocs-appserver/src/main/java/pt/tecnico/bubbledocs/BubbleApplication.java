package pt.tecnico.bubbledocs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.output.XMLOutputter;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.bubbledocs.domain.ADD;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.Content;
import pt.tecnico.bubbledocs.domain.DIV;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.domain.Reference;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.User;

public class BubbleApplication {
	private static int sheetId = 1;

	public static void main(String[] args) {
		doIt();
	}
	
	@Atomic
	public static void doIt() {
		System.out.println("Welcome to the BubbleDocs application!");

//		TransactionManager tm = FenixFramework.getTransactionManager();
//	    boolean committed = false;
//
//	   	try {
		    //tm.begin();
		    BubbleDocs bd = populateDomain();
	
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
			
			
			//Remove spreadsheet from pf from persistent state
			try {
				System.out.println("entrei no try");
				List<SpreadSheet> notas_es = bd.getSpreadSheetByName("Notas ES");
				File file = new File("C:\\Users\\JPZef\\Desktop\\esproj\\pf_Notas_ES.xml");
				
				if (!file.exists()) {
					System.out.println("cria novo ficheiro");
					file.createNewFile();
					System.out.println("novo ficheiro criado");
				}
				
				FileWriter fw = new FileWriter(file.getAbsolutePath());
				BufferedWriter bw = new BufferedWriter(fw);
				System.out.println("passou o buffer writter");
				for (SpreadSheet s: notas_es) {
					System.out.println("dentro do for");
					bw.write(xml.outputString(s.exportToXML()));
				}
				System.out.println("depois do for");
				bw.flush();
				bw.close();
				System.out.println("depois do bw.close");
			
			} catch (IOException e) {
				System.out.println("entrei no catch");
				e.printStackTrace();
			}
			
			bd.removeSpreadSheetByOwner("pf", "Notas ES");

//		    tm.commit();
//		    committed = true;
//		}catch (SystemException| NotSupportedException | RollbackException| HeuristicMixedException | HeuristicRollbackException ex) {
//		    System.err.println("Error in execution of transaction: " + ex);
//		} finally {
//		    if (!committed) 
//			try {
//			    tm.rollback();
//			} catch (SystemException ex) {
//			    System.err.println("Error in roll back of transaction: " + ex);
//			}
//    	}
//		
	}
    
    @Atomic
    static BubbleDocs populateDomain() {
    	
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
    	
		return bd;
    /*	
	PhoneBook pb = PhoneBook.getInstance();

	Person person = new Person("Manel");
 	pb.addPerson(person);
	person.addContact(new Contact("SOS", 112));
	Contact c = new Contact("IST", 214315112);
	c.setPerson(person);

	person = new Person("Teste");
 	pb.addPerson(person);
	person.addContact(new Contact("LOL", 111));
	c = new Contact("ESCSPUTL", 214315166);
	c.setPerson(person);
	

	try {
	    c = new Contact("IST", 214315112);
	    c.setPerson(person);
	    System.out.println("Error! Business rule violated!");
	} catch (pt.tecnico.phonebook.exception.NameAlreadyExistsException nae) {
	    System.out.println("Could not add two equals contacts to the same person");
	}
	*/
    
    }

}
