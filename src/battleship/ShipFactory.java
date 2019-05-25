package battleship;

import java.awt.Point;

import battleship.Ship.ShipOrientation;

public class ShipFactory {
	public static final int DESTROYER_LENGTH = 2;
	public static final int SUBMARINE_LENGTH = 2;
	public static final int CRUISER_LENGTH = 3;
	public static final int BATTLESHIP_LENGTH = 4;
	public static final int CARRIER_LENGTH = 5;
	
	public static Ship destroyer(int boardsize) {
		return generateRandomShip(DESTROYER_LENGTH, boardsize);
	}
	
	public static Ship submarine(int boardsize) {
		return generateRandomShip(SUBMARINE_LENGTH, boardsize);
	}
	
	public static Ship cruiser(int boardsize) {
		return generateRandomShip(CRUISER_LENGTH, boardsize);
	}
	
	public static Ship battleship(int boardsize) {
		return generateRandomShip(BATTLESHIP_LENGTH, boardsize);
	}
	
	public static Ship carrier(int boardsize) {
		return generateRandomShip(CARRIER_LENGTH, boardsize);
	}
	
	public static Ship generateRandomShip(int shipSize, int boardSize) {
		final int boardLastL = boardSize - shipSize + 1;
		final boolean isHorizontal = randomBoolean();
		final ShipOrientation orientation = isHorizontal ? ShipOrientation.HORIZONTAL : ShipOrientation.VERTICAL;
		final Point location = generateLocation(isHorizontal ? boardLastL : boardSize, isHorizontal ? boardSize : boardLastL);
		
		return new Ship(shipSize, location, orientation);
	}
	
	public static Ship[] generateDefaultShips(int boardsize) {
		Ship[] ships = new Ship[5];
		Ship current;
		
		ships[0] = destroyer(boardsize);
		
		current = submarine(boardsize);
		while (checkShipCollision(current, ships)) // Keep generating ships until there are no collisions.
			current = submarine(boardsize);
		ships[1] = current;
		
		current = cruiser(boardsize);
		while (checkShipCollision(current, ships))
			current = cruiser(boardsize);
		ships[2] = current;
		
		current = battleship(boardsize);
		while (checkShipCollision(current, ships))
			current = battleship(boardsize);
		ships[3] = current;
		
		current = carrier(boardsize);
		while (checkShipCollision(current, ships))
			current = carrier(boardsize);
		ships[4] = current;
		
		return ships;
		
 	}
	
	public static boolean randomBoolean() {
		return Math.round(Math.random()) == 1;
	}
	
	public static Point generateLocation(int sizeX, int sizeY) {
		return new Point((int) Math.floor(Math.random()*sizeX), (int) Math.floor(Math.random()*sizeY));
	}
	
	public static boolean checkShipCollision(Ship ship, Ship[] ships) {
		for (Ship curShip: ships)
			if (curShip != null && Ship.checkCollision(ship, curShip))
				return true;
		return false;
		
	}
}

