package store.ws;

import org.joda.time.*;

public class Document {
	
	private byte[] content;
	//private String lastUserToWrite;
	private LocalDate lastTimeChanged;
	
    public Document() {
    	super();
    }
    
    public void setNewContents(byte[] content) {
    	this.content = content;
    	lastTimeChanged = LocalDate.now();
    }

    public byte[] getContents() {
        return content;
    }

    //Returns the size of the document in Bytes
    public int getSize() {
        return content.length;
    }
    
    public LocalDate getLastChangedTime() {
    	return this.lastTimeChanged;
    }
}
