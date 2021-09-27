package gamepackage;

public class Robot {
	// see the board, current, and next shapes
	// determine best location for shape
	// give instructions on how to get there
	
	public int[] pick_spot(boolean[][] gamemap, GamePiece c) {
		//int[] best = {0, 0, 60, 0, 19}; 
		/*
		 * steps: 
		 * [x] get row position of the highest filled spot in each column 
		 * [ ] 
		 * [ ] for each position...
		 */
		int best[] = {0, 0, 60, 0, 0};
		int[][] spaceholder = new int[4][2];
		int[] floor = new int[10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 20; j++) {
				if(gamemap[j][i] == true) {
					floor[i] = j-1;
					j = 20;
				}
				else if (j == 19) {
					floor[i] = j;
				}
			}
		}
		for(int i = 0; i < 4; i++) { // for each rotation
			for(int j = 0; j < 4; j++) { // for setting each coordinate in new rotation 
				spaceholder[j][0] = c.coordinates[j][0];
				spaceholder[j][1] = c.coordinates[j][1];
			}
			int lowest = -5;
			int lowcoord = 0;
			/*for(int j = 0; j < 4; j++) { // for each coordinate as lowest
				if(c.coordinates[j][0] >= lowest) {
					lowest = c.coordinates[j][0]; 
					lowcoord = j;
				}
			}*/
			for(int coor = 0; coor < 4; coor++) { // check each piece coord separately in floor spot
				for(int j = 0; j < 10; j++) { // for putting in lowest spot possible of each column 
					boolean can_place = true;
					for(int k = 0; k < 4; k++) { // for checking if each coord fits in related spot
						spaceholder[k][0] = floor[j] - c.coordinates[coor][0] + c.coordinates[k][0];
						spaceholder[k][1] = j - c.coordinates[coor][1] + c.coordinates[k][1];
						if(spaceholder[k][0] < 0 || spaceholder[k][0] >= 20 ||
							spaceholder[k][1] < 0 || spaceholder[k][1] >= 10) {
							can_place = false;
							k = 4;
						}
						else if(gamemap[spaceholder[k][0]][spaceholder[k][1]] == true) { 
							can_place = false;
							k = 4;
						}
								
					}
					if(can_place == true) {
						// save coordinates 
						for(int k = 0; k < 4; k++) {
							System.out.print("( " + spaceholder[k][0] + ", ");
							System.out.print(spaceholder[k][1] + " )");
						}
						System.out.println();
						
						int covered = 0;
						int destroys = 0;
						int height = 19; 
						for(int k = 0; k < 4; k++) {
							//gamemap[c.coordinates[m][0]][c.coordinates[m][1]] = true;
							if(spaceholder[k][0] < height) {
								height = spaceholder[k][0];
							}
							if(spaceholder[k][0] < 19) {
								if(gamemap[spaceholder[k][0]+1][spaceholder[k][1]] == false) {
									covered++;
									
								}
							}
							if(height >= best[4] && covered <= best[2]) {
								best[0] = spaceholder[0][1] - c.coordinates[0][1]; // displacement (right/left) 
								best[1] = i; // rotation 
								best[2] = covered;
								best[3] = destroys;
								best[4] = height;
							}
						}
					}
				}
			}
			c.rotate();
		}
		return best;
	}
	
	public int[] choose_spot(boolean[][] gamemap, GamePiece c) {
		
		/*
		 * to have at the end:
		 * all four coordinates of best position for piece
		 * rotation of best position for piece
		 * distance (right/left), and rotation to get from current to ideal 
		 */
		boolean[][] endmap = new boolean[20][10];
		int[] floor = new int[10];
		int[] best = {0, 0, 4, 0, 19}; //position and rotation difference of best (also has covered, destroys, height)
		int[][] spaceholder = new int[4][2];
		int[][] endpiece = new int[4][2];
		// get first instance in each column of 
		for(int i = 0; i < 10; i++) {
			boolean done = false;
			for(int j = 0; j < 20; j++) {
				endmap[j][i] = gamemap[j][i];
				if(gamemap[j][i] == true && done == false) {
					floor[i] = j-1;
					done = true;
				}
				else if (j == 19) {
					floor[i] = j;
				}
			}
		}
		for(int i = 0; i < 4; i++) {
			endpiece[i][0] = c.coordinates[i][0];
			endpiece[i][1] = c.coordinates[i][1];
		}
		// find best overall position (right/left) and best orientation
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				spaceholder[j][0] = c.coordinates[j][0];
				spaceholder[j][1] = c.coordinates[j][1];
			}
			// put lowest at each of the 10 spots on the floor
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 4; k++) {
					int difcol = 0; 
					int difrow = 0;
					difcol = floor[j] - c.coordinates[k][0];
					difrow = j - c.coordinates[k][1];
					boolean can_place = true;
					for(int m = 0; m < 4; m++) {
						if(m != k) {
							
							if((c.coordinates[m][0]+difcol < 0 | c.coordinates[m][0]+difcol >= 20) |
									(c.coordinates[m][1]+difrow < 0 | c.coordinates[m][1]+difrow >= 10)) {
								m = 4;
								can_place = false;
							}
							else if(gamemap[c.coordinates[m][0]+difcol][c.coordinates[m][1]+difrow] == true) {
								m = 4;
								can_place = false;
							}
						}
					}
					
					if(can_place == true) {
						//set coordinates to true on gamemap to loop through it easily
						int covered = 0;
						int destroys = 0;
						int height = 19; 
						for(int m = 0; m < 4; m++) {
							//gamemap[c.coordinates[m][0]][c.coordinates[m][1]] = true;
							if(c.coordinates[m][0]+difcol < height) {
								height = c.coordinates[m][0]+difcol;
							}
						}
						for(int m = 0; m < 4; m++) {
							if(c.coordinates[m][0]+difcol < 19) {
								if(gamemap[c.coordinates[m][0]+difcol+1][c.coordinates[m][1]+difrow] == false) {
									covered++;
									
								}
							}
						}
						//comparison:
						if(height > best[4]) {
							best[0] = difrow; // displacement (right/left) 
							best[1] = i; // rotation 
							best[2] = covered;
							best[3] = destroys;
							best[4] = height;
						}
						
						for(int m = 0; m < 4; m++) {
							//gamemap[c.coordinates[m][0]][c.coordinates[m][1]] = false;
						}
						//check how good the position is and choose if best
						//check rows 
						
					}
					
				}
				// get position of lowest and difference between it and current
				// use this to get other coordinates for this position 
			}
			// go back to original coordinates, rotate
			for(int j = 0; j < 4; j++) {
				c.coordinates[j][0] = spaceholder[j][0];
				c.coordinates[j][1] = spaceholder[j][1];
				c.rotate();
			}
			// check statistics for each to determine best 
		}
		for(int i = 0; i < 4; i++) {
			c.coordinates[i][0] = endpiece[i][0];
			c.coordinates[i][1] = endpiece[i][1];
		}
		gamemap = endmap;
		return best;
	}
}
