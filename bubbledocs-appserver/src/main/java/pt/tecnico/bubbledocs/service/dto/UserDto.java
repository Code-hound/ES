package pt.tecnico.bubbledocs.service.dto;

public class UserDto {

    private String userToken;
    private String username;

    public UserDto(String userToken, String username) {
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