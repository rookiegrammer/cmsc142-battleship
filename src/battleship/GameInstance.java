package battleship;

import java.awt.Point;

import battleship.HitBoard.HitState;

public class GameInstance {
	protected int boardsize;
	
	protected final static boolean pursueMoves = true;
	
	public class BattleshipResult {
		public int turns;
		public boolean win;
		public String toString() {
			return (win ? "Won - " : "Lost - ") + turns;
		}
	}
	
	public class PlayerState {
		public boolean ready = false;
		public Player instance;
		public String id;
		public HitBoard hitBoard;
		public Ship[] ships;
		private int[] shipsLife;
		public void setupPlay(Player instance, int size) {
			this.instance = instance;
			hitBoard = new HitBoard(size);
		}
		public synchronized void setupShips(GameInstance game) {
			ships = instance.setup(game);
			shipsLife = new int[ships.length];
			for (int i=0; i<ships.length; i++)
				shipsLife[i] = ships[i].length;
		}
		public boolean isAlive() {
			for (int life: shipsLife)
				if (life > 0) return true;
			return false;
		}
		public HitState shoot(Point location) {
			HitState prevState = hitBoard.getHit(location);
			if (prevState != HitState.NONE)
				return prevState;
			
			for (int i=0; i<ships.length; i++)
				if (ships[i].checkHit(location)) {
					hitBoard.setHit(location, HitState.HIT);
					shipsLife[i]--;
					return HitState.HIT;
				}
			hitBoard.setHit(location, HitState.MISS);
			return HitState.MISS;
		}
	}
	
	public PlayerState p1State = new PlayerState();
	public PlayerState p2State = new PlayerState();
	
	public GameInstance(Player p1, Player p2, int size) {
		p1State.setupPlay(p1, size);
		p2State.setupPlay(p2, size);
		boardsize = size < 10 ? 10 : size;
	}
	
	public BattleshipResult play() {
		p1State.setupShips(this);
		p2State.setupShips(this);
		
		int i = 0;
		
		boolean p1Alive = true;
		boolean p2Alive = true;
		
		while(p1Alive && p2Alive) {
			i++;
			HitState lastState = HitState.NONE;
			Point move = null;
			while (!valid(move)) {
				
				move = p1State.instance.nextMove();
				if (pursueMoves)
					while(p2State.hitBoard.getHit(move) != HitState.NONE)
						move = p1State.instance.nextMove(); // Continue asking for moves if move already done
				lastState = p2State.shoot(move);
				p1State.instance.acknowledge(move, lastState);
			}
			if (Player.printMoves)
				System.out.println("Player 1 "+(lastState == HitState.HIT ? "HIT" : "MISS") +" at ("+move.x +", "+move.y+")");
			if (!(p2Alive = p2State.isAlive())) break;
			
			move = null;
			while (!valid(move)) {
				move = p2State.instance.nextMove();
				if (pursueMoves)
					while(p1State.hitBoard.getHit(move) != HitState.NONE)
						move = p2State.instance.nextMove();
				lastState = p1State.shoot(move);
				p2State.instance.acknowledge(move, lastState);
			}
			if (Player.printMoves)
				System.out.println("Player 2 "+(lastState == HitState.HIT ? "HIT" : "MISS") +" at ("+move.x +", "+move.y+")");
			p1Alive = p1State.isAlive();
		}
		
		BattleshipResult result = new BattleshipResult();
		result.turns = i;
		result.win = p1State.isAlive();
		
		return result;
	}
	
	protected boolean valid(Point loc) {
		if (loc == null) return false;
		return loc.x >= 0 && loc.x < boardsize && loc.y >= 0 && loc.y < boardsize;
	}
	
	public int getBoardSize() {
		return boardsize;
	}
	
}
