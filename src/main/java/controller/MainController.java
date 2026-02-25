package controller;

import common.Session;
import repository.*;
import service.BoardService;
import service.CommentService;
import service.UserService;

import java.util.*;

public class MainController {
    private final Map<String, String> beforeLoginMenuMap = new LinkedHashMap<>();
    private final Map<String, String> afterLoginMenuMap = new LinkedHashMap<>();

    private final BoardController boardController;
    private final UserController userController;

    private final Session session = new Session();

    public MainController() {
        this.beforeLoginMenuMap.put("1", "Sign up");
        this.beforeLoginMenuMap.put("2", "Login");
        this.beforeLoginMenuMap.put("3", "Exit");

        this.afterLoginMenuMap.put("1", "Post Manage");
        this.afterLoginMenuMap.put("2", "User Manage");
        this.afterLoginMenuMap.put("3", "Logout");
        this.afterLoginMenuMap.put("4", "Exit");

//        UserRepository userRepository = new MemoryUserRepository();
//        UserRepository userRepository = new FileUserRepository();
        UserRepository userRepository = new JdbcUserRepository();
        // CommentRepository commentRepository = new MemoryCommentRepository();
        CommentRepository commentRepository = new FileCommentRepository();
//        BoardRepository boardRepository = new MemoryBoardRepository();
//        BoardRepository boardRepository = new FileBoardRepository();
        BoardRepository boardRepository = new JdbcBoardRepository();

        CommentController commentController = new CommentController(this.session, new CommentService(commentRepository, userRepository));
        this.boardController = new BoardController(this.session, new BoardService(boardRepository), commentController);
        this.userController = new UserController(this.session, new UserService(userRepository));
    }

    public Map<String, Map<String, String>> getMenu() {
        Map<String, Map<String, String>> menuMap = new HashMap<>();

        menuMap.put("before_login", this.beforeLoginMenuMap);
        menuMap.put("after_login", this.afterLoginMenuMap);

        return menuMap;
    }

    public void runProgram() {
        boolean run = true;

        Scanner scanner = new Scanner(System.in);

        // -- show menu
        Map<String, String> menu;
        Map<String, Map<String, String>> mainMenu = this.getMenu();

        while (run) {
            boolean loginCheck = this.session.isLoggedIn();
            if (loginCheck) {
                menu = mainMenu.get("after_login");
                for (String menuKey : menu.keySet()) {
                    String menuValue = menu.get(menuKey);
                    System.out.print(menuKey + ". " + menuValue + "\t");
                }
                System.out.println();

                System.out.print("Select Menu > ");
                String inputMenu = scanner.nextLine();
                try {
                    switch (inputMenu) {
                        case "1":
                            this.boardController.runMenu();
                            break;
                        case "2":
                            this.userController.runMenu();
                            break;
                        case "3":
                            System.out.println("Logout");
                            this.session.logout();
                            break;
                        case "4":
                        default:
                            run = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID is only number.");
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                menu = mainMenu.get("before_login");
                for (String menuKey : menu.keySet()) {
                    String menuValue = menu.get(menuKey);
                    System.out.print(menuKey + ". " + menuValue + "\t");
                }
                System.out.println();

                System.out.print("Select Menu > ");
                String inputMenu = scanner.nextLine();

                try {
                    switch (inputMenu) {
                        case "1":
                            this.userController.signUp();
                            break;
                        case "2":
                            this.userController.login();
                            break;
                        case "3":
                        default:
                            run = false;
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
