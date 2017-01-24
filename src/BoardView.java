import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Displays the Noughts and Crosses board in the game GUI
 * @author George
 *
 */
public class BoardView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private ClientGameModel model;
	private JButton[][] cell;

	/**
	 * The default constructor for the view, creates the buttons and sets up the screen
	 * @param model The model on which the GUI will be based
	 */
	public BoardView(ClientGameModel model) {
		super();

		// initialise model
		this.model = model;

		// create array of buttons
		cell = new JButton[3][3];

		// set layout of panel
		setLayout(new GridLayout(3, 3));

		// for each square in grid:create a button; place on panel
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cell[i][j] = new JButton(" ");
				final int x = i;
				final int y = j;
				cell[i][j].addActionListener(e -> model.turn(x, y));
				add(cell[i][j]);
			}
		}

		update(null, null);
	}

	/**
	 * when the model changes, update the board appropriately
	 */
	public void update(Observable obs, Object obj) {
		System.out.println("Starting board update");
		if (model.isMyTurn() && !model.isWon()) {
			System.out.println("My turn, updating board");
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (model.get(i, j) == ClientGame.CROSS) {
						cell[i][j].setText("X");
						cell[i][j].setEnabled(false);
					} else if (model.get(i, j) == ClientGame.NOUGHT) {
						cell[i][j].setText("O");
						cell[i][j].setEnabled(false);
					} else {
						cell[i][j].setText(" ");
						cell[i][j].setEnabled(true);
					}
				}
			}
		} else {
			System.out.println("Not my turn, disabling board");
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (model.get(i, j) == ClientGame.CROSS)
						cell[i][j].setText("X");
					else if (model.get(i, j) == ClientGame.NOUGHT)
						cell[i][j].setText("O");
					else
						cell[i][j].setText(" ");
					cell[i][j].setEnabled(false);
				}
			}
		}
		repaint();
	}
}
