package dto;

public class UserStoreDto {
    private final String userId;
    private final String password;
    private final String name;

    public UserStoreDto(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
