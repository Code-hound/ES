package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.User;

import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

/*
 * DELETE USER
 * 
 * Esta classe permite ao user se caso este for
 * o "root" pode entao apagar o user.
 * 
 * @author: Francisco Maria Calisto
 * 
 */

public class DeleteUser extends BubbleDocsService
{

	private String userToken;
	private String toDeleteUsername;

	public DeleteUser(String userToken, String toDeleteUsername)
	{
		this.userToken = userToken;
		this.toDeleteUsername = toDeleteUsername;
	}

	@Override
	protected void dispatch() throws UserDoesNotExistException, BubbleDocsException
	{
		if (userToken == "root")
		{
			User userToDelete = getUser(toDeleteUsername);

			if (userToDelete != null)
			{
				getUser(userToken).removeUser(userToDelete);
			}
			else
			{
				throw new UserDoesNotExistException(this.toDeleteUsername);
			}
		}
	}

}
