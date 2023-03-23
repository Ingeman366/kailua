import controller.AppLogic;

public class App {

    public void run() {
        new AppLogic().runMainMenu();
    }
    public static void main(String[] args) {
        new App().run();
    }
}