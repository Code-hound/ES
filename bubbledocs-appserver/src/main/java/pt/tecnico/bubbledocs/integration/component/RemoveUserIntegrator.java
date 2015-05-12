package pt.tecnico.bubbledocs.integration.component;

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
 * Delete User Integrator
 * 
 * Esta classe permite ao user se caso este for
 * o "root" pode entao apagar o user.
 * 
 * @author: Luis Ribeiro Gomes
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

		// auxiliary services
		GetUserInfoService getInfoService    = new GetUserInfoService(this.toDeleteUsername);
		
		// throws InvalidUserException
		getInfoService.execute();

		String password = getInfoService.getPassword();
		String name     = getInfoService.getName();

		// service
		CreateUserService  createUserService = new CreateUserService(this.userToken, this.toDeleteUsername, password, name);
		RemoveUserService  removeUserService = new RemoveUserService(this.userToken, this.toDeleteUsername);
		IDRemoteServices   localService      = new IDRemoteServices();

		//throws UserNotInSessionException
		//throws UnauthorizedOperationException
		removeUserService.execute();

		//throws UnavailableServiceException
		//throws LoginBubbleDocsException
		try {

			//catches RemoteInvocationException
			//catches LoginBubbleDocsException
			localService.removeUser(this.toDeleteUsername);

		} catch (RemoteInvocationException e) {

			//doesn't throw UserNotInSessionException
			//doesn't throw UnauthorizedOperationException
			//doesn't throw UserAlreadyExistsException
			//doesn't throw EmptyEmailException
			//doesn't throw InvalidUsernameException
			createUserService.execute();
			throw new UnavailableServiceException();

		} catch (LoginBubbleDocsException e) {

			//doesn't throw UnauthorizedOperationException
			//doesn't throw UserAlreadyExistsException
			//doesn't throw UserAlreadyExistsException
			//doesn't throw EmptyEmailException
			//doesn't throw InvalidUsernameException
			createUserService.execute();
			throw new LoginBubbleDocsException(this.toDeleteUsername);

		}
		
	}
}
