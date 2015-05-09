package store.ws;

import org.joda.time.*;

public class Document {
	
	private byte[] content;
	//private String lastUserToWrite;
	private DateTime lastTimeChanged;
	private String clientID;
	
    public Document(String clientID, String timestamp) {
    	super();
    	this.clientID = clientID;
    	this.lastTimeChanged = new DateTime(timestamp);
    }
    
    public void setNewContents(byte[] content, String clientID, String timestamp) {
    	this.content = content;
    	lastTimeChanged = DateTime.now();
    	this.clientID = clientID;
    	this.lastTimeChanged = new DateTime(timestamp);
    }

    public byte[] getContents() {
        return content;
    }

    //Returns the size of the document in Bytes
    public int getSize() {
        return content.length;
    }
    
    public DateTime getLastTimeChanged() {
    	return this.lastTimeChanged;
    }
}
