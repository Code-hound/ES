package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.RenewPasswordService;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;

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

		RenewPasswordService localService = new RenewPasswordService(this.userToken);

		//throws UserNotInSessionException
		localService.execute();

		IDRemoteServices remoteService = new IDRemoteServices();

		//throws UnavailableServiceException
		try {
			remoteService.renewPassword(localService.getUsername());
		} catch (RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}

	}
}
