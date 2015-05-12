package store.cli.result;

import java.util.List;

public class ListDocsResult extends Result {
	private List<String> docs;

	public ListDocsResult(List<String> docs, String timestamp, String clientID) {
		super(timestamp, clientID);
		this.docs = docs;
	}
	
	public List<String> getDocs() {
		return this.docs;
	}
	
	public static List<String> quorumDecider(List<ListDocsResult> results) {
		List<String> finalResult = results.get(0).getDocs();
		for (ListDocsResult result : results) {
			for (String doc : result.getDocs()) {
				if (!finalResult.contains(doc)) {
					finalResult.add(doc);
				}
			}
		}
		return finalResult;
	}
}
