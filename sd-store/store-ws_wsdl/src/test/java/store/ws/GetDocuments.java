package store.ws;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.jdom2.Element;

import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;

public class GetDocuments {
	private static final String USERNAME = "username";
	private static final String SHEET_ID = "2205";
	private static DocUserPair pair = new DocUserPair();
	private static StoreImpl store = new StoreImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pair.setUserId(USERNAME);
		pair.setDocumentId(SHEET_ID);
		
		try {
			store.createDoc(pair);
		} catch (DocAlreadyExists_Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		store.destroyFile(SHEET_ID);
	}
	
	@Test
	public void printDocuments() {
		for (Element element : store.getDocSet())
			System.out.println(element);
			//The errors so far are in the import of the file into XML
	}
}
