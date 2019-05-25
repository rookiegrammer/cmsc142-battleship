import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IGameLoop;
import de.gurkenlabs.litiengine.IUpdateable;

public class testUI {
	public static void main(String[] args) {
		
		// DO NOT USE YET!!
		IUpdateable funs = new IUpdateable() {

			@Override
			public void update() {
			}
			
		};
		
		Game.init();
		Game.start();
		IGameLoop loop = Game.loop();
		loop.attach(funs);
		
		
	}
}
