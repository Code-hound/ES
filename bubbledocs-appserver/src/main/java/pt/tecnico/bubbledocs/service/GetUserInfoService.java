package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public class GetUserInfoService extends BubbleDocsService {
	
	private String userName;
	
	public GetUserInfoService(String userName) {
		this.userName = userName;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {
		// FIXME
		
	}
	
}