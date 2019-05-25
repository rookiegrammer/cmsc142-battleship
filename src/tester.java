import java.util.Arrays;

import battleship.Ship;
import battleship.ShipFactory;

public class tester {
	public static void main(String[] args) {
		Ship[] ships = ShipFactory.generateDefaultShips(10);
		System.out.println(Arrays.toString(ships));
		
	}
}
