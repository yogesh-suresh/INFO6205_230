/**
 * 
 */
package RoboticController;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author yogesh
 *
 */
public class Maze {

	private final int maze[][];
	private int startPosition[] = { -1, -1 };
	private static final Logger log = Logger.getLogger(Maze.class.getName());

	public Maze(int newmaze[][]) {
		this.maze = newmaze;
	}

	public int[] getStartCoordinates() {
		// Return the coordinates if value is present

		if (this.startPosition[0] != -1 && this.startPosition[1] != -1) {
			return this.startPosition;
		}
		// Default Value
		int startPosition[] = { 0, 0 };

		// Loop over array to get start position of Maze value = 2
		for (int i = 0; i < this.maze.length; i++) {
			for (int j = 0; j < this.maze[i].length; j++) {
				// 2 is the type for start position
				if (this.maze[i][j] == 2) {
					this.startPosition = new int[] { j, i };
					log.info("Start Position set , Value = " + "{"+this.startPosition[0] + "," + this.startPosition[1] + "}");
					return new int[] { j, i };
				}
			}
		}
		log.info("Start Position not set , Default Value = " + this.startPosition);
		return startPosition;
	}

	public int getPositionValue(int x, int y) {
		if (x < 0 || y < 0 || x >= this.maze.length || y >= this.maze[0].length) {
			return 1;
		}
		return this.maze[y][x];
	}

	public boolean isWall(int x, int y) {
		return (this.getPositionValue(x, y) == 1);
	}

	public int getMaxX() {
		return this.maze[0].length - 1;
	}

	public int getMaxY() {
		return this.maze.length - 1;
	}

	public int scoreRoute(ArrayList<int[]> route) {
		int score = 0;
		int boundaryY = this.getMaxY() + 1;
		int boundaryX = this.getMaxX() + 1;
		boolean marked[][] = new boolean[boundaryY][boundaryX];
		
		for (Object routeStep : route) {
			int step[] = (int[]) routeStep;
			if (this.maze[step[1]][step[0]] == 3 && marked[step[1]][step[0]] == false) {
				score++;
				marked[step[1]][step[0]] = true;
			}
		}
		return score;

	}
}
