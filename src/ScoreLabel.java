import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

/**
 * A label for displaying the score that will observer the lobby object and update appropriately
 * @author George
 *
 */
public class ScoreLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;

	private Lobby lobby;
	
	/**
	 * The default constructor for the class
	 * @param initialString The initial string that should be displayed
	 * @param _lobby The lobby object on which it is based
	 */
	public ScoreLabel(String initialString, Lobby _lobby){
		super(initialString);
		lobby = _lobby;
	}
	
	/**
	 * When the score changes in the lobby, update the label
	 */
	@Override
	public void update(Observable o, Object arg) {
		super.setText("Your score is: " + lobby.getScore());	
		super.repaint();
	}

}
