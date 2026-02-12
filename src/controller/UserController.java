package controller;

import common.Session;
import domain.User;
import dto.UserLoginDto;
import dto.UserStoreDto;
import repository.MemoryUserRepository;
import service.UserService;

import java.util.Scanner;

public class UserController {
    private final Session session;

    private final UserService userService = new UserService(new MemoryUserRepository());

    public UserController(Session session) {
        this.session = session;
    }

    public void signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sign Up");

        System.out.print("Input ID: ");
        String inputId = scanner.nextLine();

        System.out.print("Input Password: ");
        String inputPassword = scanner.nextLine();

        System.out.print("Input name: ");
        String inputName = scanner.nextLine();

        UserStoreDto userStoreDto = new UserStoreDto(inputId, inputPassword, inputName);

        this.userService.signUp(userStoreDto);
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login");

        System.out.print("Input ID: ");
        String signUpId = scanner.nextLine();

        System.out.print("Input password: ");
        String signUpPassword = scanner.nextLine();

        UserLoginDto userLoginDto = new UserLoginDto(signUpId, signUpPassword);

        User user = this.userService.login(userLoginDto);
        this.session.login(user);
    }
}
