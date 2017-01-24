import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * The GUI for displaying the game to the user
 * @author George
 *
 */
public class ClientGameGUI {

	private JFrame frame;

	/**
	 * The default constructor creates and adds event listeners to the component
	 * @param gameModel The game model on which the GUI is based
	 */
	public ClientGameGUI(ClientGameModel gameModel) {

		ClientGameComponent comp = new ClientGameComponent(gameModel);

		frame = new JFrame("Noughts and Crosses - " + gameModel.getP1().getName() + " v. " + gameModel.getP2());
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				gameModel.forfeit();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

		});

		frame.add(comp);
	}

	/**
	 * Display the frame
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Hide the frame
	 */
	public void hide() {
		frame.setVisible(false);
	}
}
