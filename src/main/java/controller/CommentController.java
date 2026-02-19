package controller;

import common.Session;
import dto.CommentEditDto;
import dto.CommentShowDto;
import dto.CommentWriteDto;
import service.CommentService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommentController {
    private final Session session;
    private final CommentService commentService;
    private final Scanner scanner;
    private final Map<String, String> commentMenu = new LinkedHashMap<>();

    public CommentController(Session session, CommentService commentService) {
        this.session = session;
        this.commentService = commentService;
        this.scanner = new Scanner(System.in);

        this.commentMenu.put("1", "Write Comment");
        this.commentMenu.put("2", "Edit Comment");
        this.commentMenu.put("3", "Delete Comment");
        this.commentMenu.put("4", "Back");
    }

    public void runMenu(Long postId) {
        for (String menuNum : this.commentMenu.keySet()) {
            String menuName = this.commentMenu.get(menuNum);
            System.out.print(menuNum + ". " + menuName + "\t");
        }
        System.out.println();

        System.out.print("Input Comment Menu: ");
        String inputMenu = this.scanner.nextLine();

        try {
            switch (inputMenu) {
                case "1":
                    this.writeComment(postId);
                    break;
                case "2":
                    System.out.print("Input Comment Id: ");
                    String inputEditCommentId = this.scanner.nextLine();
                    Long editCommentId = Long.parseLong(inputEditCommentId);

                    this.editComment(editCommentId);
                    break;
                case "3":
                    System.out.print("Input Comment Id: ");

                    String inputDeleteCommentId = this.scanner.nextLine();
                    Long deleteCommentId = Long.parseLong(inputDeleteCommentId);

                    this.deleteComment(deleteCommentId);
                    break;
                case "4":
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCommentList(Long postId) {
        List<CommentShowDto> commentList = this.commentService.getCommentList(postId);

        System.out.println("Show Comment List.");
        for (CommentShowDto commentShowDto : commentList) {
            System.out.println("Comment ID: " + commentShowDto.commentId());
            System.out.println("Writer name: " + commentShowDto.writerName());
            System.out.println("Comment: " + commentShowDto.content());
            System.out.println("CreatedAt: " + commentShowDto.createdAt());
        }

        this.runMenu(postId);
    }

    public void writeComment(Long postId) {
        System.out.println("Write Comment.");

        System.out.print("Input comment: ");
        String inputComment = this.scanner.nextLine();

        CommentWriteDto commentWriteDto = new CommentWriteDto(postId, inputComment);

        this.commentService.writeComment(commentWriteDto, this.session.getUser());
    }

    public void editComment(Long commentId) {
        System.out.println("Edit Comment.");

        System.out.print("Input comment: ");
        String inputComment = this.scanner.nextLine();

        CommentEditDto commentEditDto = new CommentEditDto(commentId, inputComment);
        this.commentService.editComment(commentEditDto, this.session.getUser());
    }

    public void deleteComment(Long commentId) {
        System.out.println("Delete Comment: ");
        this.commentService.deleteComment(commentId, this.session.getUser());
    }
}
