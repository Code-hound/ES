package pt.tecnico.bubbledocs.domain;

/*permissionLevel values:
 * 
 * 1 - root access
 * 2 - creator access
 * 3 - read access
 * 4 - write access
 */

public class Access extends Access_Base {
    
    public Access(User user,int permissionLevel) {
        super();
        setUser(user);
        set_permission(permissionLevel);
        permission = Permission.OWNER;
    }
    
    private Permission permission;
}
