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
	
	public static Result quorumDecider_Result(List<Result> results) {
		if (results.size() == 0) {
			return null;
		}
		
		Result finalResult = results.get(0);
		for (Result result : results) {
			if (result.getTimestamp().equals(finalResult.getTimestamp())) {
				finalResult = result;
			}
		}
		
		return finalResult;
	}
}
