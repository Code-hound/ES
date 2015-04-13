
package store.ws;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.jws.*;

import store.ws.*; 

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

import pt.ulisboa.tecnico.sdis.store.ws.*;

/*
 * Interface methods:
 * -createDoc
 * -listDocs
 * -store
 * -load
 */

@WebService(
	    endpointInterface="pt.ulisboa.tecnico.sdis.store.ws.SDStore", 
	    wsdlLocation="SD-STORE.1_1.wsdl",
	    name="SDStore",
	    portName="SDStoreImplPort",
	    targetNamespace="urn:pt:ulisboa:tecnico:sdis:store:ws",
	    serviceName="SDStore"
	)

public class StoreImpl implements SDStore {
	private static final String STORED = "stored_files";
	private static final String TERMINATOR = ".txt";
	private static final String SEPARATOR = Character.toString(File.separatorChar);
	private static final int MAX_FILE_SIZE_BYTES = 1024; //10MB per file
	 
	/*
	 *  Creates a new document in the provided user's repository.
     *	In case this is the first operation on that user, 
     *	a new repository is created for the new user.
     *	Faults: a document already exists with the same id
	 */
	public void createDoc(DocUserPair docUser) throws DocAlreadyExists_Exception {
		String path = getFilePath(docUser);
		
		try {
			File file = new File(path);
			//If the user repository does not yet exist, create it
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// Create the new file
			file.createNewFile();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/*
		Element spreadsheet = new Element("spreadsheet");
		spreadsheet.setAttribute("id", docUser.getDocumentId());
		spreadsheet.setAttribute("ownerUsername", docUser.getUserId());
		
		Document document = new Document(spreadsheet);
		
		XMLOutputter xmlOutput = new XMLOutputter();
		//Print on console
		/*
		try {
			xmlOutput.output(document, System.out);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		//Create a buffered file writer
		try {
			FileWriter writer = new FileWriter("stored_files/"+docUser.getDocumentId()+".xml");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			//Output the XML document through the buffered file writer
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(document, bufferedWriter);
			
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	*/
	
	/*
	 * 	Lists the document ids of the user's repository.
     *	Faults: user does not exist
	 */
    public List<String> listDocs(String userId) throws UserDoesNotExist_Exception {
    	String path = getUserPath(userId);
    	File[] files = new File(path).listFiles();
    	List<String> fileList = new ArrayList<String>();
    	
    	for (File file : files) {
    		fileList.add(file.getName());
    	}
    	
    	return fileList;
    }

    public void store(DocUserPair docUserPair, byte[] contents)
    		throws CapacityExceeded_Exception, DocDoesNotExist_Exception, 
    		UserDoesNotExist_Exception
    {
    	String path = getFilePath(docUserPair);
    	File file = new File(path);
    	FileOutputStream writer;
    	BufferedOutputStream bufferedWriter = null;
    	
    	try {
    		writer = new FileOutputStream(file);
    		bufferedWriter = new BufferedOutputStream(writer);
    		
    		//System.out.println(contents);
    		bufferedWriter.write(contents);
    		bufferedWriter.flush();
    		bufferedWriter.close();
    	} catch (IOException ex) {
    		System.out.println("Failed IO");//ex.getMessage());
    	} finally {
    		try {
    			if (bufferedWriter != null)
    				bufferedWriter.close();	
    		} catch (IOException ex) {
    			System.out.println("Failed IO");//ex.getMessage());
    		}
    	}
    }

    public byte[] load(DocUserPair docUser) {
    	String path = getFilePath(docUser);
    	File file = new File(path);
    	float fileSize = file.length();
    	//WARNING: casting float as int may not be safe
    	byte[] content = new byte[(int)fileSize];
    	FileInputStream reader;
    	BufferedInputStream bufferedReader = null;
    	
    	try {
    		reader = new FileInputStream(file);
    		bufferedReader = new BufferedInputStream(reader);
    		bufferedReader.read(content);
    		bufferedReader.close();
    	} catch (IOException ex) {
    		System.out.println("Failed IO");
    	}
    	
    	return content;
    }
    
    // ========== END OF INTERFACE METHODS ==========
    
    /*
	private Element getElementFromFile (File file) {
		//Creates a XML builder using the SAXBuilder class
		SAXBuilder xmlBuilder = new SAXBuilder();
		
		try {
			try {
				//Generates a XML document through the XML builder using the imported document
				Document document = xmlBuilder.build(file);
				//Creates a new XML element equal to the root element from the document
				Element element = document.getRootElement();
				System.out.println("Printing document:");
				System.out.println(document);
				System.out.println("Printing root element:");
				System.out.println(element);
				System.out.println("Printing element contents:");
				System.out.println(element.getContent());
				for (int i = 0; i<element.getContent().size(); i++) {
					System.out.println(element.getContent().get(i).getValue());
				}
	
	    		return element;
			} catch (JDOMException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	*/
	
	public void destroyFile (String id) {
		String path = STORED;
		destroyFile(id, path);
	}

	/*
	private Element getElementFromFile (File file) {
		//Creates a XML builder using the SAXBuilder class
		SAXBuilder xmlBuilder = new SAXBuilder();
		
		try {
			try {
				//Generates a XML document through the XML builder using the imported document
				Document document = xmlBuilder.build(file);
				//Creates a new XML element equal to the root element from the document
				Element element = document.getRootElement();
				System.out.println("Printing document:");
				System.out.println(document);
				System.out.println("Printing root element:");
				System.out.println(element);
				System.out.println("Printing element contents:");
				System.out.println(element.getContent());
				for (int i = 0; i<element.getContent().size(); i++) {
					System.out.println(element.getContent().get(i).getValue());
				}
	
	    		return element;
			} catch (JDOMException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	*/
	
	private void destroyFile (String id, String path) {
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (file.getName().equals(id+TERMINATOR)) {
				file.delete();
				break;
			}
			else if (file.isDirectory()) {
				destroyFile(id, file.getPath());
			}
		}
	}

	private String getFilePath (DocUserPair docUser) {
    	return (String) getUserPath(docUser.getUserId())
				+ SEPARATOR + docUser.getDocumentId() + TERMINATOR;
    }
	
	private String getUserPath (String username) {
		return (String) STORED + SEPARATOR + username;
	}
    
    /*
    private Element getElementFromFile (File file) {
    	//Creates a XML builder using the SAXBuilder class
    	SAXBuilder xmlBuilder = new SAXBuilder();
    	
    	try {
    		try {
    			//Generates a XML document through the XML builder using the imported document
    			Document document = xmlBuilder.build(file);
    			//Creates a new XML element equal to the root element from the document
    			Element element = document.getRootElement();
    			System.out.println("Printing document:");
    			System.out.println(document);
    			System.out.println("Printing root element:");
    			System.out.println(element);
    			System.out.println("Printing element contents:");
    			System.out.println(element.getContent());
    			for (int i = 0; i<element.getContent().size(); i++) {
    				System.out.println(element.getContent().get(i).getValue());
    			}

	    		return element;
    		} catch (JDOMException ex) {
    			System.out.println(ex.getMessage());
    		}
    	} catch (IOException ex) {
    		System.out.println(ex.getMessage());
    	}
    	return null;
    }
    */
}