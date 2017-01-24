import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * The GUI object for displaying the lobby
 * @author George
 *
 */
public class LobbyGUI {

	private JFrame frame;
	
	/**
	 * The default constructor for the class
	 * @param player The active player in the lobby
	 * @param lobby The lobby object that the GUI is based on
	 */
	public LobbyGUI(ClientPlayer player, Lobby lobby) {

		LobbyComponent comp = new LobbyComponent(lobby);

		frame = new JFrame(player.getName() + " - Lobby");
		frame.setSize(200, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {		
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Disconnecting");
				lobby.setClosed(true);
				player.sendMessage(new Message(Message.CLIENT_DISCONNECTED, player.getName()));				
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
	 * Show the frame
	 */
	public void show() {
		frame.setVisible(true);
	}

}
