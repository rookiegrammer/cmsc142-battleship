package battleship;

import java.awt.Color;
import java.awt.Point;

public class Ship {
	public static enum ShipOrientation {
		VERTICAL,
		HORIZONTAL
	}
	
	public final ShipOrientation orientation;
	public final Point location;
	public final int length;
	public Color color = Color.WHITE;

	public Ship(int length, Point location, ShipOrientation orientation) {
		this.length = length;
		this.location = location;
		this.orientation = orientation;
	}
	
	public boolean checkHit(Point target) {
		if (orientation == null || location == null || length <= 0) return false;
		switch(orientation) {
		case HORIZONTAL:
			return target.y == location.y && target.x >= location.x && target.x < location.x+length;
		case VERTICAL:
			return target.x == location.x && target.y >= location.y && target.y < location.y+length;
		default:
			return false;
		}
	}
	
	public static boolean checkCollision(Ship s1, Ship s2) {
		if (s1 == null || s2 == null) return false;
		
		boolean s1o = s1.orientation == ShipOrientation.HORIZONTAL;
		boolean s2o = s2.orientation == ShipOrientation.HORIZONTAL;
		
		return s1.location.x < (s2.location.x + (s2o ? s2.length : 1)) &&
			  (s1.location.x + (s1o ? s1.length : 1)) > s2.location.x  &&
			   s1.location.y < (s2.location.y + (s2o ? 1 : s2.length)) &&
			  (s1.location.y + (s1o ? 1 : s1.length)) > s2.location.y  ;
	}
	
	public String toString() {
		return "( "+location.x+", "+location.y+" ) "+(orientation == ShipOrientation.HORIZONTAL?"H":"V")+" length "+length;
	}
	
}
