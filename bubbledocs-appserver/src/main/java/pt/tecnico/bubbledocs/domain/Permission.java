package pt.tecnico.bubbledocs.domain;

public enum Permission {
	NONE (0), ROOT (1), OWNER (2), WRITER (3), READER (4);
	
	private final int permissionLevel;
	
	public int getPermissionLevel () {
		return this.permissionLevel;
	}
	
	Permission (int level) {
		this.permissionLevel = level;
	}
	
	public static Permission getPermission (int level) {
		for (Permission p : Permission.values()) {
			if (p.getPermissionLevel() == level) {
				return p;
			}
		}
		return Permission.NONE;
	}
}
