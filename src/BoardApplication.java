import controller.MainController;

public class BoardApplication {

    public static void main(String[] args) {
        System.out.println("Terminal Board Program Start");

        MainController mainController = new MainController();
        mainController.runProgram();

        System.out.println("Terminal Board Program End");
    }
}
