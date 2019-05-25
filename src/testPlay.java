import battleship.GameInstance;
import battleship.players.RandomPlayer;

public class testPlay {
	public static void main(String[] args) {
		GameInstance game = new GameInstance(new RandomPlayer(), new RandomPlayer(), 10);
		game.play();
	}
}
