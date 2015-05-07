package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

public class RenewPasswordIntegrator extends BubbleDocsIntegrator {
	
	private String userToken;

	public RenewPasswordIntegrator (String userToken) {
		this.userToken = userToken;
	}

	public String getUserToken() {
		return this.userToken;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {

		IDRemoteServices integration = new IDRemoteServices();
		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null)
			throw new UserNotInSessionException(username);

		//throws UnavailableServiceException
		try {
			integration.renewPassword(username);
		} catch (RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}

		getUser(username).setPassword(null);
	}
}
