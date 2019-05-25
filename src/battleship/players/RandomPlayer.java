package battleship.players;

import java.awt.Point;

import battleship.HitBoard.HitState;
import battleship.Player;
import battleship.ShipFactory;

public class RandomPlayer extends Player {
	
	private int boardsize;
	
	@Override
	protected void setup() {
		boardsize = game.getBoardSize();
	}

	@Override
	public Point nextMove() {
		return ShipFactory.generateLocation(boardsize, boardsize);
	}

	@Override
	public void acknowledge(Point move, HitState state) {
		// Print result
		System.out.println("Player "+(state == HitState.HIT ? "HIT" : "MISS") +" at ("+move.x +", "+move.y+")");
		
		// Pause thread
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
