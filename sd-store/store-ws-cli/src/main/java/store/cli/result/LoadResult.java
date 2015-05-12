package store.cli.result;

import java.util.List;

import org.joda.time.DateTime;

public class LoadResult extends Result {
	
	private byte[] content;
	
	public LoadResult(byte[] content, String timestamp, String clientID) {
		super(timestamp, clientID);
		this.content = content;
	}
	
	public byte[] getContent() {
		return this.content;
	}
	
	public static byte[] quorumDecider(List<LoadResult> results) {
		if (results.size() == 0) {
			return null;
		}
		LoadResult finalResult = results.get(0);
		for (LoadResult result : results) {
			if (result.getTimestamp().isAfter(finalResult.getTimestamp())) {
				finalResult = result;
			}
		}
		return finalResult.getContent();
	}
}
