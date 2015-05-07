package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

public class RenewPasswordService extends BubbleDocsService {
	
	private String userToken;

	public RenewPasswordService (String userToken) {
		this.userToken = userToken;
	}

	public String getUserToken() {
		return this.userToken;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {

		IDRemoteServices service = new IDRemoteServices();
		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null)
			throw new UserNotInSessionException(username);

		//throws UnavailableServiceException
		try {
			service.renewPassword(username);
		} catch (RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}

		getUser(username).setPassword(null);
	}
}
