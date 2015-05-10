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
	
	//@Override
	public static byte[] quorumDecider(List<LoadResult> results) {
		LoadResult finalResult = (LoadResult) Result.quorumDecider_Result( (List<Result>) (List<?>) results);
		return finalResult.getContent();
	}
}
