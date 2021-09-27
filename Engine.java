package gamepackage;
import java.util.Timer;

public class Engine {
	public static void main(String args[]) {
		Robot myrobot = new Robot();
		Game thisgame = new Game();
		Timer timer = new Timer();
		TimeHelper clock = new TimeHelper();     
        timer.schedule(clock, 100, 500);
        thisgame.play();
        clock.piece = thisgame.piece;
        thisgame.newmove = true;
		while(thisgame.end == false) {
			
			if(thisgame.newmove == true) {
				int best[] = myrobot.pick_spot(thisgame.gamemap, thisgame.piece);
				for(int i = 0; i < best[1]; i++) {
					thisgame.piece.rotate();
					thisgame.refresh();
					//thisgame.check_landing();
					thisgame.displayboard();
				}
				if(best[0] > 0) {
					for(int i = 0; i < best[0]; i++) {
						thisgame.piece.move_right();
						thisgame.refresh();
				thisgame.check_landing();
						thisgame.displayboard();
					}
				}
				else if(best[0] < 0) {
					for(int i = 0; i > best[0]; i--) {
						thisgame.piece.move_left();
						thisgame.refresh();
						thisgame.check_landing();
						thisgame.displayboard();
					}
				}
				thisgame.newmove = false;
			}
			
			thisgame.refresh();
			thisgame.check_landing();
			thisgame.displayboard();
		}
	}
}

