package controller;

import domain.Post;
import dto.PostEditDto;
import dto.PostListDto;
import dto.PostShowDto;
import dto.PostWriteDto;
import service.BoardService;

import java.util.*;

public class BoardController {
    private final Map<Integer, String> menuMap = new LinkedHashMap<>();
    private final List<String> MenuList = new ArrayList<>();
    private final BoardService boardService = new BoardService();

    public BoardController() {
        this.setMenuMap();
    }

    public void setMenuMap() {

        this.menuMap.put(1, "리스트 조회");
        this.menuMap.put(2, "글 등록");
        this.menuMap.put(3, "글 상세보기");
        this.menuMap.put(4, "글 수정");
        this.menuMap.put(5, "글 삭제");
        this.menuMap.put(6, "프로그램 종료");
    }

    public String getMenuString() {
        return "1. Post List\t2. Write Post\t3. Show Post\t4. Edit Post\t5. Delete Post\t6. Exit";
    }

    public void runProgram() {
        boolean run = true;
        String menu = this.getMenuString();

        Scanner scanner = new Scanner(System.in);

        try {
            while (run) {
                // -- show menu
                System.out.println(menu);

                System.out.print("Select Menu > ");
                String inputMenu = scanner.nextLine();

                switch (inputMenu) {
                    case "1":
                        System.out.println("Show Post List");

                        List<PostListDto> postListDtoList = boardService.getPostList();
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
                        boardService.storePost(postWriteDto);

                        break;
                    case "3":
                        System.out.println("Show Post Detail");

                        System.out.print("Input Post Id: ");

                        String strId = scanner.nextLine();
                        Long id = Long.parseLong(strId);

                        boardService.showPost(id);
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

                        boardService.editPost(postEditDto);

                        break;
                    case "5":
                        System.out.println("Delete Post");
                        break;
                    case "6":
                    default:
                        run = false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
