package pt.tecnico.bubbledocs.domain;

/*permissionLevel values:
 * 
 * 0 - root access
 * 1 - creator access
 * 2 - read access
 * 3 - write access
 */

public class Access extends Access_Base {
    
    public Access(User user,int permissionLevel) {
        super();
        setUser(user);
        set_permission(permissionLevel);
    }
    
}
