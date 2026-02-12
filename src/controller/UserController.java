package controller;

import common.Session;
import domain.User;
import dto.UserLoginDto;
import dto.UserStoreDto;
import service.UserService;

import java.util.Scanner;

public class UserController {
    private final Session session;
    private final UserService userService;
    private final Scanner scanner;

    public UserController(Session session, UserService userService) {
        this.session = session;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
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
