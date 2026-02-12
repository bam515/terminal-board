package controller;

import common.Session;
import domain.User;
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
        this.userMenu.put("3", "Logout");
        this.userMenu.put("4", "Back");
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
                    break;
                case "2":
                    break;
                case "3":
                    System.out.println("Logout");
                    this.session.logout();
                    openMenu = false;
                    break;
                case "4":
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
}
