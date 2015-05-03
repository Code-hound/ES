package pt.tecnico.bubbledocs.integration.dto;

public class UserDtoIntegrator {

	private String userToken;
	private String username;

	public UserDtoIntegrator(String userToken, String username) {
		this.userToken = userToken;
		this.username = username;
	}

	public final String getUserToken() {
		return this.userToken;
	}

	public final String getUserName() {
		return this.username;
	}
}
