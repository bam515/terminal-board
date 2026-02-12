package controller;

import common.Session;
import domain.User;
import dto.*;
import repository.MemoryBoardRepository;
import service.BoardService;

import java.util.*;

public class BoardController {
    private final Session session;
    private final BoardService boardService = new BoardService(new MemoryBoardRepository());

    public BoardController(Session session) {
        this.session = session;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Store Post");

        System.out.print("title: ");
        String title = scanner.nextLine();

        System.out.print("content: ");
        String content = scanner.nextLine();

        User loginedUser = this.session.getUser();
        String writer = loginedUser.getUserId();

        PostWriteDto postWriteDto = new PostWriteDto(title, content, writer);
        this.boardService.storePost(postWriteDto);
    }

    public void showPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Show Post Detail");

        System.out.print("Input Post Id: ");

        String strId = scanner.nextLine();
        Long id = Long.parseLong(strId);

        PostShowDto postShowDto = this.boardService.showPost(id);
        System.out.println("title: " + postShowDto.getTitle());
        System.out.println("content: " + postShowDto.getContent());
        System.out.println("writer: " + postShowDto.getWriter());
        System.out.println("createdAt: " + postShowDto.getCreatedAt());
    }

    public void editPost() {
        Scanner scanner = new Scanner(System.in);
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
    }

    public void deletePost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Delete Post");

        System.out.print("Input Post Id: ");
        String deleteStrId = scanner.nextLine();
        Long deleteId = Long.parseLong(deleteStrId);

        this.boardService.deletePost(deleteId);
    }
}
