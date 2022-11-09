
import services.GameService;

public class Main {
    public static void main(String[] args) throws Exception {

        var game = new GameService();

        game.start();
    }
}