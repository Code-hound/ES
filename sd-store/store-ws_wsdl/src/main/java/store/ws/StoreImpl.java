
package store.ws;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

import javax.jws.*;
import store.ws.*; 
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.WebResult;
//import javax.xml.ws.RequestWrapper;
//import javax.xml.ws.ResponseWrapper;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

import pt.ulisboa.tecnico.sdis.store.ws.*;

@WebService(
	    endpointInterface="pt.ulisboa.tecnico.sdis.store.ws.SDStore", 
	    wsdlLocation="SD-STORE.1_1.wsdl",
	    name="SDStore",
	    portName="SDStoreImplPort",
	    targetNamespace="urn:pt:ulisboa:tecnico:sdis:store:ws",
	    serviceName="SDStore"
	)

public class StoreImpl implements SDStore {
	 
	public void createDoc(DocUserPair docUser) throws DocAlreadyExists_Exception {
		//CreateSpreadSheet();
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
		*/
		
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
    
    public List<Element> getDocSet() {
    	List<Element> documents = new ArrayList<Element>();
    	
    	File[] files = new File("stored_files").listFiles();
    	for (File file : files) {
    		Element element = getElementFromFile(file);
    		documents.add(element);
    	}
    	return documents;
    }
    
    private Element buildSpreadSheet () {
    	return null;
    }
    
    private Element getElementFromFile (File file) {
    	//Creates a XML builder using the SAXBuilder class
    	SAXBuilder xmlBuilder = new SAXBuilder();
    	
    	try {
    		try {
    			//Generates a XML document through the XML builder using the imported document
    			Document document = xmlBuilder.build(file);
    			//Creates a new XML element equal to the root element from the document
    			Element element = document.getRootElement();

	    		return element;
    		} catch (JDOMException ex) {
    			System.out.println(ex.getMessage());
    		}
    	} catch (IOException ex) {
    		System.out.println(ex.getMessage());
    	}
    	return null;
    }
    
    public void destroyFile (String id) {
    	File[] files = new File("stored_files").listFiles();
    	for (File file : files) {
    		Element element = getElementFromFile(file);
    		if (element.getAttribute("id").getValue().equals(id)) {
    			file.delete();
    		}
    		
    	}
    }
}