package pt.tecnico.bubbledocs.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ImportDocumentException;

public class ImportDocumentService extends BubbleDocsService {

    // the tokens
	private String userToken;
	private byte[] doc;

	private SpreadSheet result;

	public ImportDocumentService(String userToken, byte[] doc) {
		this.userToken = userToken;
		this.doc       = doc;
	}
	
	public SpreadSheet getResult() {
		return this.result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		
		String username = resetUserLastAccess(this.userToken);

		//throws UserNotInSessionException
		if (userIsNotValid(username)) {
			throw new UserNotInSessionException(username);
		}

        org.jdom2.Document jdomDoc;

        SAXBuilder builder = new SAXBuilder();
        builder.setIgnoringElementContentWhitespace(true);
        
        //throws ImportDocumentException
        try {
            jdomDoc = builder.build(new ByteArrayInputStream(this.doc));
        } catch (JDOMException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ImportDocumentException();
        }

        Element rootElement = jdomDoc.getRootElement();
        this.result = getBubbleDocs().importFromXML(rootElement);

        //throws InvalidAccessException
        if ( !this.result.getOwnerUsername().equals(username) ) {
        	throw new InvalidAccessException (username, this.result.getSpreadSheetName(), "Owner");
        }

	}

}
