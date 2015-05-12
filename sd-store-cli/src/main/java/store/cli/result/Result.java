package store.cli.result;

import java.util.List;

import org.joda.time.DateTime;

public abstract class Result {
	private DateTime timestamp;
	private String clientID;
	
	public Result(String timestamp, String clientID) {
		this.timestamp = new DateTime(timestamp);
		this.clientID = clientID;
	}
	
	public DateTime getTimestamp() {
		return this.timestamp;
	}
	
	public String getClientID() {
		return this.clientID;
	}
}
