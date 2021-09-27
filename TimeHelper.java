package gamepackage;
import java.util.TimerTask;

public class TimeHelper extends TimerTask{

	public GamePiece piece;
	public void run() {
		if(piece != null) {
			piece.move_down();
		}
	}
	
}
