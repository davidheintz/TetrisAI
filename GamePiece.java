package gamepackage;

public class GamePiece {
	/*
	 * OBJECTS:
	 * - coordinates
	 * - rotation
	 * METHODS:
	 * - set
	 * - rotate
	 * - move
	 */
	
	/* 7 piece types: 
	 * cube: .. straight: .... 	left: ..
	 * 		 ..						   .
	 * 							       .
	 * right: ..  middle: .    S:  ..	 Z: ..	
	 * 		  .			  ..	  ..		 ..
	 * 		  .			  . 
	 */
	
	public byte value;
	int[][] prevcoord = new int[4][2];
	int[][] coordinates = new int[4][2];
	int[][][] rotatechange = new int[4][4][2];
	public int rotation = 0;
	public int maxrot = 4;
	
	public void reset_coord() {
		coordinates = new int[4][2];
		prevcoord = coordinates;
	}
	
	//setting initial coordinates and rotation changes based on the new piece type (0-6)
	public void set(byte piece) {

		value = piece;
		switch(piece) {
		case 0:
			//cube
			int[][] cubecoord = {{-1,5}, {-1,6}, {0,5}, {0,6}};
			int[][][] cuberot = {{{0,0}, {0,0}, {0,0}, {0,0}}};
			maxrot = 1;
			coordinates = cubecoord;
			rotatechange = cuberot;
			
			break;
		case 1:
			//straight 
			int[][] strcoord = {{-3,5}, {-2,5}, {-1,5}, {0,5}};
			int[][][] strrot = {{{2,2}, {1,1}, {0,0}, {-1,-1}}, {{1,-1}, {0,0}, {-1,1}, {-2,2}}, 
					{{-2,-2}, {-1,-1}, {0,0}, {1,1}}, {{-1,1}, {0,0}, {1,-1}, {2,-2}}};
			maxrot = 4;
			coordinates = strcoord;
			rotatechange = strrot;
			break;
		case 2:
			//left
			int[][] leftcoord = {{-2,5}, {-2,6}, {-1,6}, {0,6}};
			int[][][] leftrot = {{{0,2}, {1,1}, {0,0}, {-1,-1}}, {{2,0}, {1,-1}, {0,0}, {-1,1}}, 
					{{0,-2}, {-1,-1}, {0,0}, {1,1}}, {{-2,0}, {-1,1}, {0,0}, {1,-1}}};
			maxrot = 4;
			coordinates = leftcoord;
			rotatechange = leftrot;
			break;
		case 3:
			//right
			int[][] rightcoord = {{-2,6}, {-2,5}, {-1,5}, {0,5}};
			int[][][] rightrot = {{{2,0}, {1,1}, {0,0}, {-1,-1}}, {{0,-2}, {1,-1}, {0,0}, {-1,1}}, 
					{{-2,0}, {-1,-1}, {0,0}, {1,1}}, {{0,2}, {-1,1}, {0,0}, {1,-1}}};
			maxrot = 4;
			coordinates = rightcoord;
			rotatechange = rightrot;
			break;
		case 4:
			//middle
			int[][] midcoord = {{-1,6}, {-2,5}, {-1,5}, {0,5}};
			int[][][] midrot = {{{1,-1}, {1,1}, {0,0}, {-1,-1}}, {{-1,-1}, {1,-1}, {0,0}, {-1,1}}, 
					{{-1,1}, {-1,-1}, {0,0}, {1,1}}, {{1,1}, {-1,1}, {0,0}, {1,-1}}};
			maxrot = 4;
			coordinates = midcoord;
			rotatechange = midrot;
			break;
		case 5:
			//Z
			int[][] zcoord = {{-2,6}, {-1,6}, {-1,5}, {0,5}};
			int[][][] zrot = {{{0,-1}, {-1,0}, {0,1}, {2,-1}}, {{0,1}, {1,0}, {0,-1}, {-2,1}}};
			maxrot = 2;
			coordinates = zcoord;
			rotatechange = zrot;
			break;
		case 6:
			//S
			int[][] scoord = {{-2,5}, {-1,5}, {-1,6}, {0,6}};
			int[][][] srot = {{{0,2}, {-1,1}, {0,0}, {-1,-1}}, {{0,-2}, {1,-1}, {0,0}, {1,1}}};
			maxrot = 2;
			coordinates = scoord;
			rotatechange = srot;
			break;
		default:
		}
	}
	
	public void rotate() {
		if(rotation < maxrot) {
			for(int i = 0; i < 4; i++) {
				prevcoord[i][0] = coordinates[i][0]; 
				prevcoord[i][1] = coordinates[i][1];
				coordinates[i][0] = coordinates[i][0] + rotatechange[rotation][i][0];
				coordinates[i][1] = coordinates[i][1] + rotatechange[rotation][i][1];
			}
		}
		rotation++;
		if(rotation == maxrot) {
			rotation = 0;
		}
	}
	
	public void move_down() {
		for(int i = 0; i < 4; i++) {
			prevcoord[i][0] = coordinates[i][0]; 
			prevcoord[i][1] = coordinates[i][1]; 
		}
		for(int i = 0; i < 4; i++) {
			coordinates[i][0]++;
		}
	}
	
	public void move_right() { 
		for(int i = 0; i < 4; i++) {
			prevcoord[i][0] = coordinates[i][0]; 
			prevcoord[i][1] = coordinates[i][1]; 
			coordinates[i][1]++;
		}
	}
	
	public void move_left() {
		for(int i = 0; i < 4; i++) {
			prevcoord[i][0] = coordinates[i][0]; 
			prevcoord[i][1] = coordinates[i][1]; 
			coordinates[i][1]--;
		}
	}
}