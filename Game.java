package gamepackage;

public class Game {
	
	public boolean[][] gamemap;
	public GamePiece piece;
	public GamePiece nextpiece;
	public Stack order;
	public boolean end;
	public boolean newmove;
	
	//public Robot myrobot;
	
	public Game() {
		gamemap = new boolean[20][10];
		piece = null;
		nextpiece = null;
		order = new Stack();
		end = false;
	}

	public void refresh() { 
		for(int i = 0; i < 4; i++) {
			//checking gamemap boundaries
			if((piece.prevcoord[i][0] >= 0 && piece.prevcoord[i][0] < 20) &&
					(piece.prevcoord[i][1] >= 0 && piece.prevcoord[i][1] < 10)) {
				// setting all previous coordinates to false
				gamemap[piece.prevcoord[i][0]][piece.prevcoord[i][1]] = false;
			}
		}
		for(int i = 0; i < 4; i++) {
			//checking gamemap boundaries
			if((piece.coordinates[i][0] >= 0 && piece.coordinates[i][0] < 20) &&
					(piece.coordinates[i][1] >= 0 && piece.coordinates[i][1] < 10)) {
				// setting all new coordinates to true
				gamemap[piece.coordinates[i][0]][piece.coordinates[i][1]] = true;
			}
		}
	}
	
	public void play() {
		if(piece == null) {
			if(order.first == null) {
				order = order.make_order_random();
			}
			piece = new GamePiece(); 
			piece.set(order.get_node().value);
			nextpiece = new GamePiece();
			nextpiece.set(order.get_node().value);
		}
		// movedown based on timer
		// 
		//if piece is touching build on gamemap then call place method
		//check for collisions
	}
	
	// set entire row to false in gamemap, increment the ones above it downward
	public void destory_layer(int layer) { 
		
		//destroy entire row @ position layer
		for(int i = 0; i < 10; i++) {
			gamemap[layer][i] = false;
		}
		
		//move all elements above layer down one increment
		int i = layer-1;
		int empty = 0;
		while(empty != 10) {
			for(int j = 0; j < 10; j++) {
				if(gamemap[i][j] == true) {
					gamemap[i][j] = false; //erase higher block
					gamemap[i+1][j] = true; //create block one space lower
				}
				else {
					empty++;
				}
			}
			if(empty != 10) {
				empty = 0;
			}
			i--;
			
		}
	}

	public void place() {
		
		// checking layers piece was added to for full (all 10 have blocks)
		// destroy_layer if full 
		for(int i = 0; i < 4; i++) {
			int layer = 0;
			if(piece.coordinates[i][0] <= 0) {
				end = true;
				i = 4;
			}
			else {
				for(int j = 0; j < 10; j++) {
					if(gamemap[piece.coordinates[i][0]][j] == true) {
						layer++;
					}
				}
				if(layer == 10) {
					destory_layer(piece.coordinates[i][0]);
				}
				layer = 0;
			}
		}
		if(end != true) {
			piece.reset_coord();
			piece.set(nextpiece.value); 
			if(order.first == null) {
				order = order.make_order_random();
			}
			nextpiece.set(order.get_node().value);
		}
		newmove = true; 
		// set piece equal to nextpiece and set nextpiece to new piece from order
	}
	
	public void check_landing() {
		int floor = 0;
		Stack lowest = new Stack();
		for(byte i = 0; i < 4; i++) {
			if(piece.coordinates[i][0] > floor) {
				lowest.empty();
				lowest.add_node(new Node(i));
				floor = piece.coordinates[i][0];
			}
			else if(piece.coordinates[i][0] == floor) {
				lowest.add_node(new Node(i));
			}
		}
		Node coord = lowest.get_node();
		while(coord != null) {
			if(piece.coordinates[coord.value][0] == 19) {
				place();
			}
			else if(gamemap[piece.coordinates[coord.value][0]+1]
					[piece.coordinates[coord.value][1]] == true) {
				place();
			}
			coord = lowest.get_node();
		}
	}
	
	public void move(boolean movetype) {
		//check if all coordinates of next position are on gamemap and not occupied
		boolean can_move = true;
		
		// move left
		if(movetype == false) {
			//check if moving left is possible
			for(int i = 0; i < 4; i++) {
				if(piece.coordinates[i][1] < 1) {
					can_move = false;
				}
			}
			// if so, adjust coordinates of piece and gamemap accordingly
			if(can_move == true) {
				piece.move_left();
				refresh();
			}
		}
		
		//move right
		if(movetype == true) {
			// check if moving right is possible
			for(int i = 0; i < 4; i++) {
				if(piece.coordinates[i][1] > 8) {
					can_move = false;
				}
			}
			// if so, adjust coordinates of piece and gamemap accordingly
			if(can_move == true) {
				piece.move_right();
				refresh();
			}
		}
	}
	
	public void displayboard() {
		System.out.println("............");
		for(int i = 0; i < 20; i++) {
			System.out.print("|");
			for(int j = 0; j < 10; j++) {
				if(gamemap[i][j] == true) {
					System.out.print(".");
				}
				else {
					System.out.print(" ");
				}
			}
			System.out.println("|");
		}
		System.out.println("............");
	}
}
