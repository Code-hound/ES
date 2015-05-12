package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.RenewPasswordService;
import pt.tecnico.bubbledocs.service.GetUsername4TokenService;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;

public class RenewPasswordIntegrator extends BubbleDocsIntegrator {
	
	private String userToken;

	public RenewPasswordIntegrator (String userToken) {
		this.userToken = userToken;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {

		GetUsername4TokenService getUsernameService = new GetUsername4TokenService(this.userToken);
		RenewPasswordService renewPasswordService   = new RenewPasswordService(this.userToken);

		//throws UserNotInSessionException
		getUsernameService.execute();

		String username = getUsernameService.getUsername();
		IDRemoteServices remoteService = new IDRemoteServices();

		//throws LoginBubbleDocsException
		//throws UnavailableServiceException
		try {

			//throws LoginBubbleDocsException
			//throws RemoteInvocationException
			remoteService.renewPassword(username);

		} catch (RemoteInvocationException e) {

			throw new UnavailableServiceException();

		}

		//doesn't throw UserNotInSessionException
		renewPasswordService.execute();

	}

}
