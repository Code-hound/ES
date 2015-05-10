package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.CreateUserService;
import pt.tecnico.bubbledocs.service.RemoveUserService;
import pt.tecnico.bubbledocs.service.GetUserInfoService;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.InvalidUsernameException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;

import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;

/*
 * DELETE USER
 * 
 * Esta classe permite ao user se caso este for
 * o "root" pode entao apagar o user.
 * 
 * @author: Francisco Maria Calisto
 * 
 */

public class RemoveUserIntegrator extends BubbleDocsIntegrator {

	private String userToken;
	private String toDeleteUsername;

	public RemoveUserIntegrator(String userToken, String toDeleteUsername) {
		this.userToken = userToken;
		this.toDeleteUsername = toDeleteUsername;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		GetUserInfoService getInfoService    = new GetUserInfoService(this.toDeleteUsername);
		
		//TODO: Fix GetUserInfoService!!!!
		
		
		CreateUserService  createUserService = new CreateUserService("test", "test", "test", "test");
		RemoveUserService  removeUserService = new RemoveUserService(this.userToken, this.toDeleteUsername);
		IDRemoteServices   localService      = new IDRemoteServices();
		
		//throws UserNotInSessionException
		//throws UnavailableServiceException
		//throws LoginBubbleDocsException
		removeUserService.execute();

		//throws UnavailableServiceException
		try {
			localService.removeUser(this.toDeleteUsername);
		} catch (RemoteInvocationException e) {
			//throws UnauthorizedOperationException
			//throws UserAlreadyExistsException
			createUserService.execute();
			throw new UnavailableServiceException();
		} catch (LoginBubbleDocsException e) {
			//throws UnauthorizedOperationException
			//throws UserAlreadyExistsException
			createUserService.execute();
			throw new LoginBubbleDocsException(this.toDeleteUsername);
		}
		
	}
}
