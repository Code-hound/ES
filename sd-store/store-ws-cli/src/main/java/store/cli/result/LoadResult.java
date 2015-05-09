package store.cli.result;

import org.joda.time.DateTime;

public class LoadResult {
	
	private DateTime timestamp;
	private String clientID;
	private byte[] content;
	
	public LoadResult(byte[] content, String timestamp, String clientID) {
		this.timestamp = new DateTime(timestamp);
		this.content = content;
		this.clientID = clientID;
	}
	
	public byte[] getContent() {
		return this.content;
	}
	
	public DateTime getTimestamp() {
		return this.timestamp;
	}
	
	public String getClientID() {
		return this.clientID;
	}
}
