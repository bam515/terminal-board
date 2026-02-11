import controller.BoardController;

public class BoardApplication {

    public static void main(String[] args) {
        System.out.println("Terminal Board Program Start");

        BoardController boardController = new BoardController();
        boardController.runProgram();

        System.out.println("Terminal Board Program End");
    }
}
