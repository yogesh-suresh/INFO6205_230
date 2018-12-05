/**
 * 
 */
package RoboticController;

import java.util.ArrayList;

/**
 * @author yogesh
 *
 */
public class TestRobot {
	private enum Direction {
		NORTH, EAST, SOUTH, WEST
	};

	private int xPos;
	private int yPos;
	private int endPosValue = 4;
	private Direction heading;
	int maxMoves;
	int moves;
	private int sensorVal;
	private final int sensorActions[];
	private Maze maze;
	private ArrayList<int[]> route;

	public TestRobot(int[] sensorActions, Maze maze, int maxMoves) {
		this.sensorActions = this.calcSensorActions(sensorActions);
		this.maze = maze;
		int startCoordinates[] = this.maze.getStartCoordinates();
		this.xPos = startCoordinates[0];
		this.yPos = startCoordinates[1];
		this.sensorVal = -1;
		this.heading = Direction.EAST;
		this.maxMoves = maxMoves;
		this.moves = 0;
		this.route = new ArrayList<int[]>();
		this.route.add(startCoordinates);
	}

	private int[] calcSensorActions(int[] sensorActionsStr) {
		// No of actions for 2 - bit encoded action
		int numActions = (int) sensorActionsStr.length / 2;
		int sensorActions[] = new int[numActions];
		// Loop through actions
		for (int sensorValue = 0; sensorValue < numActions; sensorValue++) {

			int sensorAction = 0;
			if (sensorActionsStr[sensorValue * 2] == 1) {
				sensorAction += 2;
			}
			if (sensorActionsStr[(sensorValue * 2) + 1] == 1) {
				sensorAction += 1;
			}

			sensorActions[sensorValue] = sensorAction;
		}
		return sensorActions;
	}

	public void run() {
		while (true) {
			this.moves++;
			// Break if the robot stops moving
			if (this.getNextAction() == 0) {
				return;
			}
			// End point is reached
			if (this.maze.getPositionValue(this.xPos, this.yPos) == this.endPosValue) {
				return;
			}
			// Edge case for robot movement
			if (this.moves > this.maxMoves) {
				return;
			}
			// make the next needed decision
			this.makeNextAction();
		}
	}

	
	public void makeNextAction() {
		// If move forward
		if (this.getNextAction() == 1) {
			int currentX = this.xPos;
			int currentY = this.yPos;

			// Move depending on current direction
			if (Direction.NORTH == this.heading) {
				this.yPos += -1;
				if (this.yPos < 0) {
					this.yPos = 0;
				}
			} else if (Direction.EAST == this.heading) {
				this.xPos += 1;
				if (this.xPos > this.maze.getMaxX()) {
					this.xPos = this.maze.getMaxX();
				}
			} else if (Direction.SOUTH == this.heading) {
				this.yPos += 1;
				if (this.yPos > this.maze.getMaxY()) {
					this.yPos = this.maze.getMaxY();
				}
			} else if (Direction.WEST == this.heading) {
				this.xPos += -1;
				if (this.xPos < 0) {
					this.xPos = 0;
				}
			}

			// We can't move here
			if (this.maze.isWall(this.xPos, this.yPos) == true) {
				this.xPos = currentX;
				this.yPos = currentY;
			} else {
				if (currentX != this.xPos || currentY != this.yPos) {
					this.route.add(this.getPosition());
				}
			}
		}
		// Move clockwise
		else if (this.getNextAction() == 2) {
			if (Direction.NORTH == this.heading) {
				this.heading = Direction.EAST;
			} else if (Direction.EAST == this.heading) {
				this.heading = Direction.SOUTH;
			} else if (Direction.SOUTH == this.heading) {
				this.heading = Direction.WEST;
			} else if (Direction.WEST == this.heading) {
				this.heading = Direction.NORTH;
			}
		}
		// Move anti-clockwise
		else if (this.getNextAction() == 3) {
			if (Direction.NORTH == this.heading) {
				this.heading = Direction.WEST;
			} else if (Direction.EAST == this.heading) {
				this.heading = Direction.NORTH;
			} else if (Direction.SOUTH == this.heading) {
				this.heading = Direction.EAST;
			} else if (Direction.WEST == this.heading) {
				this.heading = Direction.SOUTH;
			}
		}

		// Reset sensor value
		this.sensorVal = -1;
	}

	public int getSensorValue() {
		// Skip if sensor val is set
		if (this.sensorVal > -1) {
			return this.sensorVal;
		}
		boolean frontSensor, frontLeftSensor, frontRightSensor, leftSensor, rightSensor, backSensor;
		frontSensor = frontLeftSensor = frontRightSensor = leftSensor = rightSensor = backSensor = false;
		// Simulate sensor value depending on the position
		if (this.getHeading() == Direction.NORTH) {
			frontSensor = this.maze.isWall(this.xPos, this.yPos - 1);
			frontLeftSensor = this.maze.isWall(this.xPos - 1, this.yPos - 1);
			frontRightSensor = this.maze.isWall(this.xPos + 1, this.yPos - 1);
			leftSensor = this.maze.isWall(this.xPos - 1, this.yPos);
			rightSensor = this.maze.isWall(this.xPos + 1, this.yPos);
			backSensor = this.maze.isWall(this.xPos, this.yPos + 1);
		} else if (this.getHeading() == Direction.EAST) {
			frontSensor = this.maze.isWall(this.xPos + 1, this.yPos);
			frontLeftSensor = this.maze.isWall(this.xPos + 1, this.yPos - 1);
			frontRightSensor = this.maze.isWall(this.xPos + 1, this.yPos + 1);
			leftSensor = this.maze.isWall(this.xPos, this.yPos - 1);
			rightSensor = this.maze.isWall(this.xPos, this.yPos + 1);
			backSensor = this.maze.isWall(this.xPos - 1, this.yPos);
		} else if (this.getHeading() == Direction.SOUTH) {
			frontSensor = this.maze.isWall(this.xPos, this.yPos + 1);
			frontLeftSensor = this.maze.isWall(this.xPos + 1, this.yPos + 1);
			frontRightSensor = this.maze.isWall(this.xPos - 1, this.yPos + 1);
			leftSensor = this.maze.isWall(this.xPos + 1, this.yPos);
			rightSensor = this.maze.isWall(this.xPos - 1, this.yPos);
			backSensor = this.maze.isWall(this.xPos, this.yPos - 1);
		} else {
			frontSensor = this.maze.isWall(this.xPos - 1, this.yPos);
			frontLeftSensor = this.maze.isWall(this.xPos - 1, this.yPos + 1);
			frontRightSensor = this.maze.isWall(this.xPos - 1, this.yPos - 1);
			leftSensor = this.maze.isWall(this.xPos, this.yPos + 1);
			rightSensor = this.maze.isWall(this.xPos, this.yPos - 1);
			backSensor = this.maze.isWall(this.xPos + 1, this.yPos);
		}
		// Calculate sensor value
		int sensorVal = 0;
		if (frontSensor == true) {
			sensorVal += 1;
		}
		if (frontLeftSensor == true) {
			sensorVal += 2;
		}
		if (frontRightSensor == true) {
			sensorVal += 4;
		}
		if (leftSensor == true) {
			sensorVal += 8;
		}
		if (rightSensor == true) {
			sensorVal += 16;
		}
		if (backSensor == true) {
			sensorVal += 32;
		}
		this.sensorVal = sensorVal;
		return sensorVal;
	}
	
	public int getNextAction() {
		return this.sensorActions[this.getSensorValue()];
	}

	public int[] getPosition() {
		return new int[] { this.xPos, this.yPos };
	}

	private Direction getHeading() {
		return this.heading;
	}

	public ArrayList<int[]> getRoute() {
		return this.route;
	}
}
