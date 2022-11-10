
import services.GameService;
import utils.ConsolePrints;

public class Main {
    public static void main(String[] args) throws Exception {

        var game = new GameService();

        game.start();

    }
}