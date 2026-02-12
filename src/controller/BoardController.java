package controller;

import common.Session;
import domain.User;
import dto.*;
import service.BoardService;

import java.util.*;

public class BoardController {
    private final Session session;
    private final BoardService boardService;
    private final Scanner scanner;

    private final Map<String, String> postMenu = new LinkedHashMap<>();

    public BoardController(Session session, BoardService boardService) {
        this.session = session;
        this.boardService = boardService;
        this.scanner = new Scanner(System.in);

        this.postMenu.put("1", "Post List");
        this.postMenu.put("2", "Write Post");
        this.postMenu.put("3", "Show Post");
        this.postMenu.put("4", "Edit Post");
        this.postMenu.put("5", "Delete Post");
        this.postMenu.put("6", "Back");
    }

    public void runMenu() {
        boolean openMenu = true;

        while (openMenu) {
            for (String menuKey : this.postMenu.keySet()) {
                String menuValue = this.postMenu.get(menuKey);
                System.out.print(menuKey + ". " + menuValue + "\t");
            }
            System.out.println();

            System.out.print("Select Menu > ");
            String inputMenu = scanner.nextLine();

            switch (inputMenu) {
                case "1":
                    this.showPostList();
                    break;
                case "2":
                    this.storePost();
                    break;
                case "3":
                    this.showPost();
                    break;
                case "4":
                    this.editPost();
                    break;
                case "5":
                    this.deletePost();
                    break;
                case "6":
                default:
                    openMenu = false;
                    break;
            }
        }
    }

    public void showPostList() {
        System.out.println("Show Post List");

        List<PostListDto> postListDtoList = this.boardService.getPostList();
        for (PostListDto postListDto : postListDtoList) {
            System.out.println("id: " + postListDto.getId());
            System.out.println("title: " + postListDto.getTitle());
            System.out.println("content: " + postListDto.getContent());
            System.out.println("writer: " + postListDto.getWriter());
            System.out.println("createdAt: " + postListDto.getCreatedAt());
        }
    }

    public void storePost() {
        System.out.println("Store Post");

        System.out.print("title: ");
        String title = this.scanner.nextLine();

        System.out.print("content: ");
        String content = this.scanner.nextLine();

        User loginedUser = this.session.getUser();
        String writer = loginedUser.getUserId();

        PostWriteDto postWriteDto = new PostWriteDto(title, content, writer);
        this.boardService.storePost(postWriteDto);
    }

    public void showPost() {
        System.out.println("Show Post Detail");

        System.out.print("Input Post Id: ");

        String strId = this.scanner.nextLine();
        Long id = Long.parseLong(strId);

        PostShowDto postShowDto = this.boardService.showPost(id);
        System.out.println("title: " + postShowDto.getTitle());
        System.out.println("content: " + postShowDto.getContent());
        System.out.println("writer: " + postShowDto.getWriter());
        System.out.println("createdAt: " + postShowDto.getCreatedAt());
    }

    public void editPost() {
        System.out.println("Edit Post");

        System.out.print("Input Post Id: ");
        String editStrId = this.scanner.nextLine();
        Long editId = Long.parseLong(editStrId);

        System.out.print("title: ");
        String editTitle = this.scanner.nextLine();

        System.out.print("content: ");
        String editContent = this.scanner.nextLine();
        PostEditDto postEditDto = new PostEditDto(editId, editTitle, editContent);

        this.boardService.editPost(postEditDto);
    }

    public void deletePost() {
        System.out.println("Delete Post");

        System.out.print("Input Post Id: ");
        String deleteStrId = this.scanner.nextLine();
        Long deleteId = Long.parseLong(deleteStrId);

        this.boardService.deletePost(deleteId);
    }
}
