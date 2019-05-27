package battleship;

import java.awt.Point;

import battleship.HitBoard.HitState;

public abstract class Player {
	public static int pauseIntervals = 20;
	public static boolean printMoves = true;
	
	protected GameInstance game;
	
	/**
	 * Return my ships
	 * @param game
	 * @return
	 */
	public Ship[] setup(GameInstance game) {
		this.game = game;
		setup();
		return ShipFactory.generateDefaultShips(game.getBoardSize());
	}
	
	protected abstract void setup();
	public abstract Point nextMove();
	public abstract void acknowledge(Point move, HitState state);
}
