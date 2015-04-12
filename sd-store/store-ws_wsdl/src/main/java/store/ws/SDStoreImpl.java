
package store.ws;

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import pt.ulisboa.tecnico.sdis.store.ws.*;

public class SDStoreImpl implements SDStore {
	 
	public void createDoc(DocUserPair docUser) throws DocAlreadyExists_Exception {
		//CreateSpreadSheet();
		Element spreadsheet = new Element("spreadsheet");
		spreadsheet.setAttribute("id", docUser.getDocumentId());
		spreadsheet.setAttribute("ownerUsername", docUser.getUserId());
		
		Document document = new Document(spreadsheet);
		
		XMLOutputter XMLoutput = new XMLOutputter();
		//Print on console
		try {
			XMLoutput.output(document, System.out);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		XMLoutput.setFormat(Format.getPrettyFormat());
	}

    public List<String> listDocs(String userId) throws UserDoesNotExist_Exception {
    	return null;
    }

    public void store(DocUserPair docUserPair, byte[] contents)
    		throws CapacityExceeded_Exception, DocDoesNotExist_Exception, 
    		UserDoesNotExist_Exception
    {
    	
    }

    public byte[] load(DocUserPair docUser) {
    	return null;
    }
    
    private List<Element> getDocSet() {
    	return null;
    }
    
    private Element buildSpreadSheet () {
    	return null;
    }
}