package controller;

import common.Session;
import domain.User;
import dto.EditPasswordDto;
import dto.LoginUserDto;
import dto.UserLoginDto;
import dto.UserStoreDto;
import service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class UserController {
    private final Session session;
    private final UserService userService;
    private final Scanner scanner;

    private final Map<String, String> userMenu = new LinkedHashMap<>();

    public UserController(Session session, UserService userService) {
        this.session = session;
        this.userService = userService;
        this.scanner = new Scanner(System.in);

        this.userMenu.put("1", "Show User");
        this.userMenu.put("2", "Edit Password");
        this.userMenu.put("3", "Back");
    }

    public void runMenu() {
        boolean openMenu = true;

        while (openMenu) {
            for (String menuKey : this.userMenu.keySet()) {
                String menuValue = this.userMenu.get(menuKey);
                System.out.print(menuKey + ". " + menuValue + "\t");
            }
            System.out.println();

            System.out.print("Select Menu > ");
            String inputMenu = scanner.nextLine();

            switch (inputMenu) {
                case "1":
                    this.showUser();
                    break;
                case "2":
                    this.editUser();
                    break;
                case "3":
                default:
                    openMenu = false;
                    break;
            }
        }
    }

    public void signUp() {
        System.out.println("Sign Up");

        System.out.print("Input ID: ");
        String inputId = this.scanner.nextLine();

        System.out.print("Input Password: ");
        String inputPassword = this.scanner.nextLine();

        System.out.print("Input name: ");
        String inputName = this.scanner.nextLine();

        UserStoreDto userStoreDto = new UserStoreDto(inputId, inputPassword, inputName);

        this.userService.signUp(userStoreDto);
    }

    public void login() {
        System.out.println("Login");

        System.out.print("Input ID: ");
        String signUpId = this.scanner.nextLine();

        System.out.print("Input password: ");
        String signUpPassword = this.scanner.nextLine();

        UserLoginDto userLoginDto = new UserLoginDto(signUpId, signUpPassword);

        User user = this.userService.login(userLoginDto);
        this.session.login(user);
    }

    // -- 현재 로그인한 계정 정보
    public void showUser() {
        System.out.println("Show User");

        LoginUserDto loginUserDto = this.session.getUser();
        System.out.println("ID: " + loginUserDto.getUserId());
        System.out.println("NAME: " + loginUserDto.getName());
        System.out.println("createdAt: " + loginUserDto.getCreatedAt());
        System.out.println("lastLoginDate: " + loginUserDto.getLastLoginDate());
    }

    // -- 현재 로그인한 계정 비밀번호 변경
    public void editUser() {
        System.out.println("Edit User");

        System.out.print("Input Current Password: ");
        String beforePassword = this.scanner.nextLine();

        System.out.print("Input New Password: ");
        String newPassword = this.scanner.nextLine();

        LoginUserDto loginUserDto = this.session.getUser();

        EditPasswordDto editPasswordDto = new EditPasswordDto(beforePassword, newPassword);
        this.userService.editPassword(loginUserDto, editPasswordDto);
    }
}
