
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import battleship.GameInstance;
import battleship.HitBoard;
import battleship.HitBoard.HitState;
import battleship.Ship.ShipOrientation;
import battleship.Ship;
import battleship.players.RandomPlayer;

public class SimulationUI extends Canvas implements Runnable{
	
	// Change players here
	public void setup() {
		game = new GameInstance(new RandomPlayer(), new RandomPlayer(), B_SIZE);
	}
	
	private final Color[] colors = {Color.CYAN, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.ORANGE};

	private static final long serialVersionUID = 1L;
	
	public static final int S_SIDE = 20;
	public static final int H_SPACE = 1;
	
	public static final int B_SIZE = 10;
	
	public static final int WIDTH = B_SIZE*S_SIDE*2+H_SPACE*S_SIDE;
	public static final int HEIGHT = B_SIZE*S_SIDE;
	public static final int SCALE = 3;
	public static final String NAME = "Battleships";
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	
	GameInstance game;
	
	
	
	public SimulationUI(){
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
	
	public synchronized void start() {
		setup();
		running = true;
		new Thread(this).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(game.play() ? "Player 1 Wins" : "Player 2 Wins" );
				running = false;
			}
		}).start();
	}
	
    public synchronized void stop() {
		running = false;
	}
	
	public void run() {	
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while (running){
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while(delta >= 1)
			{
				ticks++;
				// tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender)
			{
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				// System.out.println(ticks + " updates, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick(){
		
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawFrame(g);
		
		g.dispose();
		bs.show();
	}
	
	private void drawFrame(Graphics g) {
		HitBoard curboard = game.p2State.hitBoard;
		drawShips(0, 0, game.p2State.ships, g);
		drawBoard(0, 0, curboard, g);
		
		curboard = game.p1State.hitBoard;
		drawShips(squares(B_SIZE+H_SPACE), 0, game.p1State.ships, g);
		drawBoard(squares(B_SIZE+H_SPACE), 0, curboard, g);
	}
	
	private void drawShips(int xOff, int yOff, Ship[] ships, Graphics g) {
		if (ships == null) return;
		
		final int SQUARE = squares(1);
		
		for (int i=0; i<ships.length; i++) {
			Ship cur = ships[i];
			boolean isHorizontal = cur.orientation == ShipOrientation.HORIZONTAL;
			g.setColor(colors[i%colors.length]);
			for (int j=0; j<cur.length; j++) {
				g.fillRect(xOff+squares(cur.location.x+(isHorizontal ? j: 0)), yOff+squares(cur.location.y+(isHorizontal ? 0: j)), SQUARE, SQUARE);
			}
		}
	}
	
	private void drawBoard(int xOff, int yOff, HitBoard board, Graphics g) {
		final int SQUARE = squares(1);
		
		for (int i = 0; i < board.size; i++) {
			for (int j = 0; j < board.size; j++) {
				final int posX = xOff+squares(i);
				final int posY = yOff+squares(j);
				
				HitState state = board.getHit(new Point(i,j));
				switch (state) {
				case HIT:
					g.setColor(Color.RED);
					g.fillRect(posX, posY, SQUARE, SQUARE);
					break;
				case MISS:
					g.setColor(Color.DARK_GRAY);
					g.fillRect(posX, posY, SQUARE, SQUARE);
					break;
				default: break;
				}
				
				g.setColor(Color.BLACK);
				g.drawRect(posX, posY, SQUARE, SQUARE);
			}
		}
	}
	
	private int squares(int val) {
		return val*S_SIDE*SCALE;
	}
	
	public static void main(String[] args){ 
		new SimulationUI().start();		
	}

}
