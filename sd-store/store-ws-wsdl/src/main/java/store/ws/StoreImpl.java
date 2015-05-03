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

@HandlerChain(
	file="/handler-chain.xml"
)

public class StoreImpl implements SDStore {
	private static final String STORED = "stored_files";
	private static final String TERMINATOR = ".txt";
	private static final String SEPARATOR = Character.toString(File.separatorChar);
	private static final int MAX_SIZE_IN_BYTES = 10000000;
	 
	/*
	 *  Creates a new document in the provided user's repository.
     *	In case this is the first operation on that user, 
     *	a new repository is created for the new user.
     *	Faults: a document already exists with the same id
	 */
	public void createDoc(DocUserPair docUser) 
			throws DocAlreadyExists_Exception {
		
		String path = getFilePath(docUser);
		try {
			File file = new File(path);
			
			//If the file already exists, throw an exception
			checkFileAlreadyExists(file, docUser.getUserId(), docUser.getDocumentId());
			/*
			if (file.exists()) {
				throw new DocAlreadyExists_Exception(("User \"" +
						docUser.getUserId() + 
						"\" already has a document called \"" +
						docUser.getDocumentId() + "\"."),
						new DocAlreadyExists());
			}
			*/
			
			//If the repository does not exist, create it
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
    public List<String> listDocs(String userId) 
    		throws UserDoesNotExist_Exception {
    	
    	String path = getUserPath(userId);
    	File repository = new File(path);
    	
    	checkUserExists(repository, userId);
    	/*
    	if (!repository.exists()) {
    		throw new UserDoesNotExist_Exception(("User \"" +
    				userId +"\" does not exist."),
    				new UserDoesNotExist());
    	}
    	*/
    	
    	File[] files = repository.listFiles();
    	List<String> fileList = new ArrayList<String>();
    	
    	for (File file : files) {
    		fileList.add(file.getName());
    	}
    	
    	return fileList;
    }

    public void store(DocUserPair docUser, byte[] contents)
    		throws CapacityExceeded_Exception,
    		DocDoesNotExist_Exception, 
    		UserDoesNotExist_Exception
    {
    	if (contents.length > MAX_SIZE_IN_BYTES) {
    		throw new CapacityExceeded_Exception(("The max size of " +
    				(MAX_SIZE_IN_BYTES/1000000) + 
    				" Megabytes was exceeded."),
    				new CapacityExceeded());
    	}
    	String path = getFilePath(docUser);
    	File file = new File(path);
    	
    	checkUserExists(file.getParentFile(), docUser.getUserId());
    	checkFileExists(file, docUser.getDocumentId());
    	/*
    	if (!file.getParentFile().exists()) {
    		throw new UserDoesNotExist_Exception(("User \"" +
    				docUser.getUserId() +"\" does not exist."),
    				new UserDoesNotExist());
    	}
    	if (!file.exists()) {
    		throw new DocDoesNotExist_Exception (("Document \"" +
    				docUser.getDocumentId() +"\" does not exist."),
    				new DocDoesNotExist());
    	}
    	*/
    	
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

    public byte[] load(DocUserPair docUser)
    		throws UserDoesNotExist_Exception,
    		DocDoesNotExist_Exception
    {
    	String path = getFilePath(docUser);
    	File file = new File(path);
    	checkUserExists(file.getParentFile(), docUser.getUserId());
    	checkFileExists(file, docUser.getDocumentId());
    	
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
    
    public void destroyRepository (String username) {
		String path = getUserPath(username);
		File repository = new File (path);
		File[] userFiles = repository.listFiles();
		for (File file : userFiles) {
			file.delete();
		}
		repository.delete();
	}

	public void destroyFile (DocUserPair docUser) {
    	destroyFile(docUser.getUserId(), docUser.getDocumentId());
   	}
	
	private void destroyFile (String repository, String docId) {
		File[] files = new File(getUserPath(repository)).listFiles();
		for (File file : files) {
			if (file.getName().equals(docId+TERMINATOR)) {
				file.delete();
				return;
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
	
	private void checkUserExists (File repository, String userId) 
			throws UserDoesNotExist_Exception {
		if (!repository.exists()) {
    		throw new UserDoesNotExist_Exception(("User \"" +
    				 userId + "\" does not exist."),
    				new UserDoesNotExist());
		}
	}
	
	private void checkFileExists (File file, String docId) 
			throws DocDoesNotExist_Exception {
		if (!file.exists()) {
    		throw new DocDoesNotExist_Exception (("Document \"" +
    				docId +"\" does not exist."),
    				new DocDoesNotExist());
    	}
	}
	
	private void checkFileAlreadyExists (File file, String userId, String docId) 
			throws DocAlreadyExists_Exception {
		if (file.exists()) {
			throw new DocAlreadyExists_Exception(("User \"" + userId + 
					"\" already has a document called \"" + docId + "\"."),
					new DocAlreadyExists());
		}
	}
}