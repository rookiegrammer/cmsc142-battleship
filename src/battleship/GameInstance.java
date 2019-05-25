package battleship;

import java.awt.Point;

import battleship.HitBoard.HitState;

public class GameInstance {
	protected int boardsize;
	
	protected final static boolean pursueMoves = true;
	
	protected class PlayerState {
		public boolean ready = false;
		public Player instance;
		public String id;
		public HitBoard hitBoard;
		private Ship[] ships;
		private int[] shipsLife;
		public void setupPlay(Player instance, int size) {
			this.instance = instance;
			hitBoard = new HitBoard(size);
		}
		public void setupShips(GameInstance game) {
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
	
	protected PlayerState p1State = new PlayerState();
	protected PlayerState p2State = new PlayerState();
	
	public GameInstance(Player p1, Player p2, int size) {
		p1State.setupPlay(p1, size);
		p2State.setupPlay(p2, size);
		boardsize = size < 10 ? 10 : size;
	}
	
	public void play() {
		p1State.setupShips(this);
		p2State.setupShips(this);
		
		boolean p1Alive = true;
		boolean p2Alive = true;
		
		while(p1Alive && p2Alive) {
			System.out.print("1 ");
			
			Point move = p1State.instance.nextMove();
			if (pursueMoves)
				while(p2State.hitBoard.getHit(move) != HitState.NONE)
					move = p1State.instance.nextMove(); // Continue asking for moves if move already done
			
			p1State.instance.acknowledge(move, p2State.shoot(move));
			if (!(p2Alive = p2State.isAlive())) break;
			
			System.out.print("2 ");
			move = p2State.instance.nextMove();
			if (pursueMoves)
				while(p1State.hitBoard.getHit(move) != HitState.NONE)
					move = p2State.instance.nextMove();
			
			p2State.instance.acknowledge(move, p1State.shoot(move));
			p1Alive = p1State.isAlive();
		}
		
		if (p1Alive) {
			System.out.println("Player 1 Wins");
		} else if (p2Alive) {
			System.out.println("Player 2 Wins");
		}
	}
	
	public int getBoardSize() {
		return boardsize;
	}
	
}
