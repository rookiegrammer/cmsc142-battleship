package battleship.players;

import java.awt.Point;

import battleship.HitBoard.HitState;
import battleship.Player;
import battleship.ShipFactory;

public class RandomPlayer extends Player {
	
	private int boardsize;
	
	private int searchState = 0;
	private Point curPoint = null;
	private int orientCheck = 0;
	private boolean horizontal = true;
	
	@Override
	protected void setup() {
		boardsize = game.getBoardSize();
	}

	@Override
	public Point nextMove() {
		// Brute force method here
		Point next = null;
		
		if (searchState == 0) {
			return ShipFactory.generateLocation(boardsize, boardsize);
		} else if (searchState == 1) {
			if (orientCheck == 0) {
				next = new Point(curPoint.x, curPoint.y-1);
			} else if (orientCheck == 1) {
				next = new Point(curPoint.x-1, curPoint.y);
			} else if (orientCheck == 2) {
				next = new Point(curPoint.x, curPoint.y+1);
			} else if (orientCheck == 3) {
				next = new Point(curPoint.x+1, curPoint.y);
			}
			orientCheck++;
			if (orientCheck > 3)
				searchState = 0;
		} else if (searchState == 2) {
			if (horizontal)
				next = new Point(curPoint.x-1, curPoint.y);
			else
				next = new Point(curPoint.x, curPoint.y-1);
			curPoint = next;
		} else if (searchState == 3) {
			if (horizontal)
				next = new Point(curPoint.x+1, curPoint.y);
			else
				next = new Point(curPoint.x, curPoint.y+1);
			curPoint = next;
		}

		return next;
	}

	@Override
	public void acknowledge(Point move, HitState state) {
		if (state == HitState.HIT) {
			if (searchState == 0) {
				curPoint = move;
				searchState = 1;
				orientCheck = 0;
			} else if (searchState == 1) {
				horizontal = orientCheck == 2 || orientCheck == 4;
				searchState = 2;
			}
		} else {
			if (searchState == 2) {
				searchState = 3;
			} else if (searchState == 3)
				searchState = 0;
		}
		
		try {
			Thread.sleep(pauseIntervals);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
