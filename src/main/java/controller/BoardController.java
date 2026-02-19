package controller;

import common.Session;
import dto.*;
import service.BoardService;

import java.util.*;

public class BoardController {
    private final Session session;
    private final BoardService boardService;
    private final Scanner scanner;
    private final CommentController commentController;

    private final Map<String, String> postMenu = new LinkedHashMap<>();

    public BoardController(Session session, BoardService boardService, CommentController commentController) {
        this.session = session;
        this.boardService = boardService;
        this.scanner = new Scanner(System.in);

        this.commentController = commentController;

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
            System.out.println("id: " + postListDto.id());
            System.out.println("title: " + postListDto.title());
            System.out.println("content: " + postListDto.content());
            System.out.println("writer: " + postListDto.writer());
            System.out.println("createdAt: " + postListDto.createdAt());
        }
    }

    public void storePost() {
        System.out.println("Store Post");

        System.out.print("title: ");
        String title = this.scanner.nextLine();

        System.out.print("content: ");
        String content = this.scanner.nextLine();

        LoginUserDto loginUserDto = this.session.getUser();
        String writer = loginUserDto.userId();

        Long writerId = loginUserDto.id();

        PostWriteDto postWriteDto = new PostWriteDto(writerId, title, content, writer);
        this.boardService.storePost(postWriteDto);
    }

    public void showPost() {
        System.out.println("Show Post Detail");

        System.out.print("Input Post Id: ");

        String strId = this.scanner.nextLine();
        Long id = Long.parseLong(strId);

        PostShowDto postShowDto = this.boardService.showPost(id);
        System.out.println("title: " + postShowDto.title());
        System.out.println("content: " + postShowDto.content());
        System.out.println("writer: " + postShowDto.writer());
        System.out.println("createdAt: " + postShowDto.createdAt());

        this.commentController.showCommentList(id);
    }

    public void editPost() {
        System.out.println("Edit Post");

        System.out.print("Input Post Id: ");
        String editStrId = this.scanner.nextLine();
        Long editId = Long.parseLong(editStrId);

        // -- 해당 게시글이 내 게시글인지 확인
        LoginUserDto loginUserDto = this.session.getUser();
        this.boardService.validateWriter(loginUserDto, editId);

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

        // -- 해당 게시글이 내 게시글인지 확인
        LoginUserDto loginUserDto = this.session.getUser();
        this.boardService.validateWriter(loginUserDto, deleteId);

        this.boardService.deletePost(deleteId);
    }
}
