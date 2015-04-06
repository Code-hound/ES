package pt.tecnico.bubbledocs.domain;

import pt.tecnico.bubbledocs.domain.User;

/* You can set an access by giving it the user and 
 * either a string with the desired permission
 * or an integer with the desired permission level.
 * Any value outside this scope will return an access
 * with permission NONE.
 * 
 * permissionLevel values:
 * (int) 0 : (String) "none"
 * (int) 1 : (String) "reader"
 * (int) 2 : (String) "writer"
 */

public class Access extends Access_Base {

	private Permission permission;

	public Access(User user, int permissionLevel) {
		super();
		setUser(user);
		permission = Permission.newPermission(permissionLevel);
	}

	public Access(User user, String permissionLevel) {
		super();
		setUser(user);
		permission = Permission.newPermission(permissionLevel);
	}

	public int getPermission() {
		return this.permission.getPermissionLevel();
	}
}
