package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MazeGenerator {
	private static final Random rand = new Random();
	
	private static final char WALL = '*';
	private static final char PATH = ' ';
	private static final char ENTRANCE = '1';
	private static final char EXIT = '2';
	
	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	public static char[][] generate(int height, int width, boolean entry) {
		if((height % 2 == 0) || (width % 2 == 0))
			throw new IllegalArgumentException();
		
		char[][] maze = new char[height][width];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				maze[i][j] = WALL;
			}
		}
		
		int row = start(height);
		int column = start(width);
		
		maze[row][column] = PATH; 
		
		recursion(row, column, maze);
		
		if(entry)
			locate(maze);
		
		return maze;
	}
	
	private static int start(int lim) {
		int ans;
		
		do {
			ans = rand.nextInt(lim);
		}while(ans % 2 == 0);
	     
	    return ans;
	}
		 
	private static void recursion(int r, int c, char[][] maze) {
		Direction[] directions = movements();
		     
		for(int i = 0; i < directions.length; i++) {
			switch(directions[i]){
				case UP: 
					if(r - 2 <= 0) continue;
		             
					if(maze[r - 2][c] != PATH) {
						maze[r - 2][c] = PATH;
		                maze[r - 1][c] = PATH;
		                recursion(r - 2, c, maze);
		            }
		             
		            break;
				case DOWN: 
		             if(r + 2 >= maze.length - 1) continue;
		             
		             if(maze[r + 2][c] != PATH) {
		                 maze[r+2][c] = PATH;
		                 maze[r+1][c] = PATH;
		                 recursion(r + 2, c,maze);
		             }

		             break;
		         case LEFT:
		             if(c - 2 <= 0) continue;
		             
		             if(maze[r][c - 2] != PATH) {
		                 maze[r][c - 2] = PATH;
		                 maze[r][c - 1] = PATH;
		                 recursion(r, c - 2, maze);
		             }
		             
		             break;
		         case RIGHT:
		             if(c + 2 >= maze[0].length - 1) continue;
		             
		             if(maze[r][c + 2] != PATH) {
		                 maze[r][c + 2] = PATH;
		                 maze[r][c + 1] = PATH;
		                 recursion(r, c + 2, maze);
		             }
		        	 
		             break;
			}
		}
	}
	
	public static void main(String[] args) {
		char[][] maze = MazeGenerator.generate(31, 31, true);
		
		for(int i = 0; i < 31; i++) {
			System.out.println(new String(maze[i]));
		}
	}
	
	public static void locate(char[][] maze) {
		maze[rand.nextInt(maze.length)][0] = ENTRANCE;
		maze[rand.nextInt(maze.length)][maze[0].length - 1] = EXIT;
	}
		 
	private static Direction[] movements() {
		Direction[] directions = Direction.values();
		
		Collections.shuffle(Arrays.asList(directions));
		
		return directions;
	}
}