package controller;

import dto.*;
import repository.MemoryBoardRepository;
import repository.MemoryUserRepository;
import service.BoardService;
import service.UserService;

import java.util.*;

public class BoardController {
    private final BoardService boardService = new BoardService(new MemoryBoardRepository());
    private final UserService userService = new UserService(new MemoryUserRepository());
    private boolean loginCheck = false;

    public BoardController() {}

    public String getMenuString() {
        String menu = "";
        if (!this.loginCheck) {
            menu = "1. Sign up\t2. Login\t3. Exit";
        } else {
            menu = "1. Post List\t2. Write Post\t3. Show Post\t4. Edit Post\t5. Delete Post\t6. Logout\t7. Exit";
        }
        return menu;
    }

    public void runProgram() {
        boolean run = true;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Run Terminal Board Program");

        while (run) {
            // -- show menu
            String menu = this.getMenuString();
            System.out.println(menu);

            System.out.print("Select Menu > ");
            String inputMenu = scanner.nextLine();
            if (this.loginCheck) {
                try {
                    switch (inputMenu) {
                        case "1":
                            System.out.println("Show Post List");

                            List<PostListDto> postListDtoList = this.boardService.getPostList();
                            for (PostListDto postListDto : postListDtoList) {
                                System.out.println("id: " + postListDto.getId());
                                System.out.println("title: " + postListDto.getTitle());
                                System.out.println("content: " + postListDto.getContent());
                                System.out.println("writer: " + postListDto.getWriter());
                                System.out.println("createdAt: " + postListDto.getCreatedAt());
                            }

                            break;
                        case "2":
                            System.out.println("Store Post");

                            System.out.print("title: ");
                            String title = scanner.nextLine();

                            System.out.print("content: ");
                            String content = scanner.nextLine();

                            System.out.print("writer: ");
                            String writer = scanner.nextLine();

                            PostWriteDto postWriteDto = new PostWriteDto(title, content, writer);
                            this.boardService.storePost(postWriteDto);

                            break;
                        case "3":
                            System.out.println("Show Post Detail");

                            System.out.print("Input Post Id: ");

                            String strId = scanner.nextLine();
                            Long id = Long.parseLong(strId);

                            PostShowDto postShowDto = this.boardService.showPost(id);
                            System.out.println("title: " + postShowDto.getTitle());
                            System.out.println("content: " + postShowDto.getContent());
                            System.out.println("writer: " + postShowDto.getWriter());
                            System.out.println("createdAt: " + postShowDto.getCreatedAt());
                            break;
                        case "4":
                            System.out.println("Edit Post");

                            System.out.print("Input Post Id: ");
                            String editStrId = scanner.nextLine();
                            Long editId = Long.parseLong(editStrId);

                            System.out.print("title: ");
                            String editTitle = scanner.nextLine();

                            System.out.print("content: ");
                            String editContent = scanner.nextLine();
                            PostEditDto postEditDto = new PostEditDto(editId, editTitle, editContent);

                            this.boardService.editPost(postEditDto);

                            break;
                        case "5":
                            System.out.println("Delete Post");

                            System.out.print("Input Post Id: ");
                            String deleteStrId = scanner.nextLine();
                            Long deleteId = Long.parseLong(deleteStrId);

                            this.boardService.deletePost(deleteId);
                            break;
                        case "6":
                            System.out.print("Logout");
                            this.loginCheck = false;
                            break;
                        case "7":
                        default:
                            run = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID is only number.");
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    switch (inputMenu) {
                        case "1":
                            System.out.println("Sign Up");

                            System.out.print("Input ID: ");
                            String inputId = scanner.nextLine();

                            System.out.print("Input Password: ");
                            String inputPassword = scanner.nextLine();

                            System.out.print("Input name: ");
                            String inputName = scanner.nextLine();

                            UserStoreDto userStoreDto = new UserStoreDto(inputId, inputPassword, inputName);

                            this.userService.signUp(userStoreDto);
                            break;
                        case "2":
                            System.out.println("Login");

                            System.out.print("Input ID: ");
                            String signUpId = scanner.nextLine();

                            System.out.print("Input password: ");
                            String signUpPassword = scanner.nextLine();

                            UserLoginDto userLoginDto = new UserLoginDto(signUpId, signUpPassword);

                            this.loginCheck = this.userService.login(userLoginDto);
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
        System.out.println("Stop Terminal Board Program");
    }
}
