package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import pt.tecnico.bubbledocs.domain.Permission;

public class PermissionTests {

	private static int[] int_levels = { 0, 1, 2, 3, 4, 5, 10, -1, 20, -20 };
	private static String[] string_levels = { "none", "reader", "writer",
			"0", "1", "2", "dog", "1", "Shrek", "itsallogrenow" };

	private static ArrayList<Permission> int_permissions = new ArrayList<Permission>();
	private static ArrayList<Permission> string_permissions = new ArrayList<Permission>();

	@Before
	@Test
	public void createPermissionFromInteger() {
		for (int i : int_levels) {
			int_permissions.add(Permission.newPermission(i));
		}
	}

	@Before
	@Test
	public void createPermissionFromString() {
		for (String s : string_levels) {
			string_permissions.add(Permission.newPermission(s));
		}
	}

	@Test
	public void checkIntPermissionsLevel() {
		assertEquals(
				Integer.toString(int_permissions.get(0).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(1).getPermissionLevel()),
				"1");
		assertEquals(
				Integer.toString(int_permissions.get(2).getPermissionLevel()),
				"2");
		assertEquals(
				Integer.toString(int_permissions.get(3).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(4).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(5).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(6).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(7).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(8).getPermissionLevel()),
				"0");
		assertEquals(
				Integer.toString(int_permissions.get(9).getPermissionLevel()),
				"0");
	}

	@Test
	public void checkIntPermissionsName() {
		assertEquals(int_permissions.get(0).name(), "NONE");
		assertEquals(int_permissions.get(1).name(), "READER");
		assertEquals(int_permissions.get(2).name(), "WRITER");
		assertEquals(int_permissions.get(3).name(), "NONE");
		assertEquals(int_permissions.get(4).name(), "NONE");
		assertEquals(int_permissions.get(5).name(), "NONE");
		assertEquals(int_permissions.get(6).name(), "NONE");
		assertEquals(int_permissions.get(7).name(), "NONE");
		assertEquals(int_permissions.get(8).name(), "NONE");
		assertEquals(int_permissions.get(9).name(), "NONE");
	}

	@Test
	public void checkStringPermissionsLevel() {
		assertEquals(Integer.toString(string_permissions.get(0)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(1)
				.getPermissionLevel()), "1");
		assertEquals(Integer.toString(string_permissions.get(2)
				.getPermissionLevel()), "2");
		assertEquals(Integer.toString(string_permissions.get(3)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(4)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(5)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(6)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(7)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(8)
				.getPermissionLevel()), "0");
		assertEquals(Integer.toString(string_permissions.get(9)
				.getPermissionLevel()), "0");
	}

	@Test
	public void checkStringPermissionsName() {
		assertEquals(string_permissions.get(0).name(), "NONE");
		assertEquals(string_permissions.get(1).name(), "READER");
		assertEquals(string_permissions.get(2).name(), "WRITER");
		assertEquals(string_permissions.get(3).name(), "NONE");
		assertEquals(string_permissions.get(4).name(), "NONE");
		assertEquals(string_permissions.get(5).name(), "NONE");
		assertEquals(string_permissions.get(6).name(), "NONE");
		assertEquals(string_permissions.get(7).name(), "NONE");
		assertEquals(string_permissions.get(8).name(), "NONE");
		assertEquals(string_permissions.get(9).name(), "NONE");
	}
	/*
	 * for (Permission p : perms) { System.out.println("This permission " + p +
	 * " has a level of " + p.getPermissionLevel()); } }
	 */
}
