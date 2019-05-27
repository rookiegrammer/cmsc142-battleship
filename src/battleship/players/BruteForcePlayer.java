package battleship.players;

import java.awt.Point;

import battleship.HitBoard.HitState;
import battleship.Player;

public class BruteForcePlayer extends Player {
	
	// Add your own variables
	private int y = 0;
	private int x = 0;
	private int size = 0;
	
	private int searchState = 0;
	private Point curPoint = null;
	private int orientCheck = 0;
	private boolean horizontal = true;
	
	@Override
	protected void setup() {
		// Setup your own variables
		// Access the variable game.getBoardSize() to get board size
		size = game.getBoardSize();
	}

	@Override
	public Point nextMove() {
		// Brute force method here
		Point next = null;
		
		if (searchState == 0) {
			next = new Point(x,y);
			x++;
			if (x >= size) {
				y++;
				x = 0;
				if (y >= size) return null;
			}
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
		// Record move here to the state of Hit
		// states: HitState.HIT or HitState.MISS
		
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
