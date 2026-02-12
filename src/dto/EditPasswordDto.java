package dto;

public class EditPasswordDto {
    private final String beforePassword;
    private final String newPassword;

    public EditPasswordDto(String beforePassword, String newPassword) {
        this.beforePassword = beforePassword;
        this.newPassword = newPassword;
    }

    public String getBeforePassword() {
        return beforePassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
