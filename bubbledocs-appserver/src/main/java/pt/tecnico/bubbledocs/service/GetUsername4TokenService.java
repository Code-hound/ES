package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public class GetUsername4TokenService extends BubbleDocsService {

	private String userToken;
	
	public GetUsername4TokenService(String userToken) {
		this.userToken = userToken;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {
		// FIXME
		
	}
	
}