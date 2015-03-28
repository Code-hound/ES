package pt.tecnico.bubbledocs.domain;
import java.util.ArrayList;

public class PermissionTests {
	public static void main (String args[]) {
		ArrayList<Permission> perms = new ArrayList<Permission>();
		int i = 0;
		for (String s : args) {
			perms.add(Permission.getPermission(Integer.parseInt(s)));
			i++;
		}
		
		for (Permission p : perms) {
			System.out.println("This permission " + p + " has a level of " + p.getPermissionLevel());
		}
	}
}
