package battleship;

import java.awt.Point;
import java.util.Arrays;

public class HitBoard {
	private static final int isHIT = 1;
	private static final int isMISS = -1;
	
	public static enum HitState {
		HIT,
		MISS,
		NONE
	}
	
	public final int size;
	private final int[][] board;
	
	public HitBoard(int size) {
		this.board = new int[size][size];
		this.size = size;
	}
	
	public synchronized HitState getHit(Point location) {
		if (location.y < 0 && location.y >= size && location.x < 0 && location.x >= size) return HitState.NONE;
		return getState(board[location.y][location.x]);
	}
	
	public synchronized void setHit(Point location, HitState state) {
		switch(state) {
		case HIT:
			board[location.y][location.x] = isHIT;
			break;
		case MISS:
			board[location.y][location.x] = isMISS;
			break;
		default:
			break;
		}
	}
	
	private HitState getState(int x) {
		switch(x) {
		case isHIT:
			return HitState.HIT;
		case isMISS:
			return HitState.MISS;
		default:
			return HitState.NONE;
		}
	}
	
	public String toString() {
		String sb = "";
		for (int[] s: board)
			sb+=Arrays.toString(s)+'\n';
		return sb;
	}
}
