import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

/**
 * A JLabel object for displaying prompts to the user at the bottom of the game screen
 * @author George
 *
 */
public class PromptLabel extends JLabel implements Observer {

	private static final long serialVersionUID = 1L;

	private ClientGameModel model;

	/**
	 * The default constructor for the class
	 * @param _model The game model on which the prompt is based
	 */
	public PromptLabel(ClientGameModel _model) {
		super("");
		model = _model;
		update(null, null);
	}

	/**
	 * Called when something changes in the model in order to update the prompt
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

		if (model.isMyTurn()) {
			setText("It's your move");
		} else {
			setText("Waiting for opponents move");
		}

	}

}
