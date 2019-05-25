package battleship.players;

import java.awt.Point;

import battleship.HitBoard.HitState;
import battleship.Player;

public class BruteForcePlayer extends Player {
	
	// Add your own variables
	
	@Override
	protected void setup() {
		// Setup your own variables
		// Access the variable game.getBoardSize() to get board size
	}

	@Override
	public Point nextMove() {
		// Brute force method here
		return null;
	}

	@Override
	public void acknowledge(Point move, HitState state) {
		// Record move here to the state of Hit
		// states: HitState.HIT or HitState.MISS
	}

}
