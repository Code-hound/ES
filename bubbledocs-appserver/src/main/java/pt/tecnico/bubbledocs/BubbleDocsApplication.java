package pt.tecnico.bubbledocs;

import java.util.Set;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.TransactionManager;

import pt.tecnico.phonebook.domain.BubbleDocs;

import javax.transaction.*;

public class BubbleDocsApplication {

    public static void main(String[] args) {
   	System.out.println("Welcome to the BubbleDocs application!");

	TransactionManager tm = FenixFramework.getTransactionManager();
    	boolean committed = false;

   	try{
		    tm.begin();
	
		    BubbleDocs bubbledocs = BubbleDocs.getInstance();
		    populateDomain(pb);
	    }
   	
	    tm.commit();
	    
	    committed = true;
	    
	}catch (SystemException| NotSupportedException | RollbackException| HeuristicMixedException | HeuristicRollbackException ex) {
	    System.err.println("Error in execution of transaction: " + ex);
	} finally {
	    if (!committed) 
		try{
		    tm.rollback();
		}catch (SystemException ex){
		    System.err.println("Error in roll back of transaction: " + ex);
			}
    	}

	org.jdom2.Document doc = convertToXML();

	printDomainInXML(doc);

	org.jdom2.Document doc2 = convertToXML();

	printDomainInXML(doc2);

	recoverFromBackup(doc);

	doc2 = convertToXML();

	printDomainInXML(doc2);

    }

    @Atomic
    private static void recoverFromBackup(org.jdom2.Document jdomDoc) {
	BubbleDocs bubbledocs = BubbleDocs.getInstance();

	pb.importFromXML(jdomDoc.getRootElement());
    }

    static void populateDomain(BubbleDocs bubbledocs) {

	// setup the initial state if bubbledocs is empty
	
    @Atomic
    public static org.jdom2.Document convertToXML(){
	    BubbleDocs bubbledocs = BubbleDocs.getInstance();
		
		org.jdom2.Document jdomDoc = new org.jdom2.Document();
	
		jdomDoc.setRootElement(pb.exportToXML());
	
		return jdomDoc;
    }

    @Atomic
    public static void printDomainInXML(org.jdom2.Document jdomDoc) {
		XMLOutputter xml = new XMLOutputter();
		xml.setFormat(Format.getPrettyFormat());
		System.out.println(xml.outputString(jdomDoc));
    }
}
