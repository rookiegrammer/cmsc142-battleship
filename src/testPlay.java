import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.JFileChooser;

import battleship.GameInstance;
import battleship.GameInstance.BattleshipResult;
import battleship.Player;
import battleship.players.BruteForcePlayer;
import battleship.players.RandomPlayer;

public class testPlay {
	public static void main(String[] args) {
		final int tests = 500;
		
		Player.pauseIntervals = 0;
		Player.printMoves = false;
		BattleshipResult[] result = new BattleshipResult[tests];
		for (int i=0; i<tests; i++) {
			GameInstance game = new GameInstance(new BruteForcePlayer(), new RandomPlayer(), 10);
			result[i] = game.play();
		}
		System.out.println(Arrays.toString(result));
		
		try {
			EventQueue.invokeAndWait(new Runnable() {
			    @Override
			    public void run() {
			    	JFileChooser fileSelector = new JFileChooser();
			    	fileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileSelector.showOpenDialog(null);
					File selected = fileSelector.getSelectedFile();
					
					if (selected == null) return;
					
					String dir = selected.getAbsolutePath() + "/bs-t-"+tests+".csv";
					File out = new File(dir);
					try {
						PrintWriter printtofile = new PrintWriter(out);
						for (int i=0; i<tests; i++)
							printtofile.println((result[i].win ? 1 : 0) + ", "+ result[i].turns);
						printtofile.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
			    }
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
