package store.ws;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.jws.*;

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

/*
 * Luis: acrescentei esta anotacao, tal como descrito no lab:
 * http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/labs/10-ws4/index.html
 */
@HandlerChain(
	file="/handler-chain.xml"
)

public class StoreImpl implements SDStore {
	private static final String STORED = "stored_files";
	private static final String TERMINATOR = ".txt";
	private static final String SEPARATOR = Character.toString(File.separatorChar);
	 
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
    
   
	
	public void destroyFile (String docId) {
		String path = STORED;
		destroyFile(docId, path);
	}
	
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
	
	public void destroyRepository (String username) {
		String path = getUserPath(username);
		File repository = new File (path);
		repository.delete();
	}

	private String getFilePath (DocUserPair docUser) {
    	return (String) getUserPath(docUser.getUserId())
				+ SEPARATOR + docUser.getDocumentId() + TERMINATOR;
    }
	
	private String getUserPath (String username) {
		return (String) STORED + SEPARATOR + username;
	}
}