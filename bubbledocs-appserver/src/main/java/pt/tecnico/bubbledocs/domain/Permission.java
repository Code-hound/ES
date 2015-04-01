package pt.tecnico.bubbledocs.domain;

public enum Permission {
	NONE(0), READER(1), WRITER(2);

	private final int permissionLevel;

	public int getPermissionLevel() {
		return this.permissionLevel;
	}

	Permission(int level) {
		this.permissionLevel = level;
	}

	public static Permission newPermission(int level) {
		for (Permission p : Permission.values()) {
			if (p.getPermissionLevel() == level) {
				return p;
			}
		}
		return Permission.NONE;
	}

	public static Permission newPermission(String level) {
		for (Permission p : Permission.values()) {
			if (level.equalsIgnoreCase(p.name())) {
				return p;
			}
		}
		return Permission.NONE;
	}
}
