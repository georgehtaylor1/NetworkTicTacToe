import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

/**
 * The class responsible for handling messages from the server
 * @author George
 *
 */
public class ClientListener extends Thread {

	private ClientPlayer player;
	private Lobby lobby;
	private Map<String, ClientGameModel> gameMap;
	private Map<String, ClientGameGUI> GUIMap;

	/**
	 * Default constructor for the listener
	 * @param _player The corresponding player for the listener
	 * @param _lobby The corresponding lobby for the listener
	 */
	public ClientListener(ClientPlayer _player, Lobby _lobby) {
		player = _player;
		lobby = _lobby;
		gameMap = new TreeMap<String, ClientGameModel>();
		GUIMap = new TreeMap<String, ClientGameGUI>();
	}

	/**
	 * Start the listener thread
	 */
	public void run() {

		System.out.println("Listener started");
		try {
			while (!lobby.isClosed()) {
				Message message = player.receiveMessage();
				String messageType = message.getMessageType();

				if (messageType.equals(Message.CLIENT_REQUEST_RESPONSE))
					clientRequestResponse(message.getMessage());

				else if (messageType.equals(Message.REFRESH_CLIENTS))
					newClient(message.getMessage());

				else if (messageType.equals(Message.SC_GAME_REQUEST))
					gameRequest(message.getMessage());

				else if (messageType.equals(Message.SC_ACCEPT_REQUEST))
					requestAccepted(message.getMessage());

				else if (messageType.equals(Message.BOARD))
					getBoard(message.getMessage());

				else if (messageType.equals(Message.GAME_OVER))
					gameWon(message.getMessage());

				else if (messageType.equals(Message.GAME_OPPONENT_FORFEIT))
					gameOpponentForfeit(message.getMessage());

				else if (messageType.equals(Message.GAME_PLAYER_FORFEIT))
					gamePlayerForfeit(message.getMessage());
				
				else if (messageType.equals(Message.CLIENT_SCORE))
					setClientScore(message.getMessage());

			}
			
			System.out.println("Lobby closed, listener closing");
			
		} catch (Exception e) {
			System.out.println("Server communication failed");
		} finally {
			JOptionPane.showMessageDialog(null, "Communication with the server has failed, the application will now exit.", "Communication failed", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Set the current score for the client
	 * @param message message containing the new score
	 */
	private void setClientScore(String message) {
		lobby.setScore(Integer.parseInt(message));
	}

	/**
	 * Inform the player that they forfeit the game
	 * @param message The opponents name
	 */
	private void gamePlayerForfeit(String message) {
		ClientGameGUI gui = GUIMap.get(message);
		gui.hide();
		// Remove the game and corresponding GUI from the maps
		GUIMap.remove(message);
		gameMap.remove(message);
	}

	/**
	 * Inform the player that the opponent forfeit the game
	 * @param message The name of the opponent
	 */
	private void gameOpponentForfeit(String message) {
		JOptionPane.showMessageDialog(null, "The opponent forfeited, you have won the game!", "Game Over!",
				JOptionPane.PLAIN_MESSAGE);
		// Get the corresponding 
		ClientGameGUI gui = GUIMap.get(message);
		gui.hide();
		GUIMap.remove(message);
		gameMap.remove(message);
	}

	/**
	 * Inform the player that they have won the game
	 * @param message The opponent and whether they won or lost concatenated together
	 */
	private void gameWon(String message) {
		String[] split = message.split("%");
		String opponent = split[0];
		String won = split[1];

		ClientGameModel game = gameMap.get(opponent);
		game.setWon(true);

		JOptionPane.showMessageDialog(null, (won.equals("won") ? "You won!" : "You lost"), "Game Over!",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Inform the player that the opponent accepted their game request and start the game
	 * @param message The opponents name
	 */
	private void requestAccepted(String message) {
		ClientGame game = new ClientGame(player, message, true);
		ClientGameModel gameModel = new ClientGameModel(game);
		ClientGameGUI clientGameGUI = new ClientGameGUI(gameModel);
		clientGameGUI.show();
		gameMap.put(message, gameModel);
		GUIMap.put(message, clientGameGUI);
	}

	/**
	 * Inform the lobby that there is a new client connected
	 * @param message No content
	 */
	private void newClient(String message) {
		lobby.resetClients();
	}

	/**
	 * Inform the game that a board has been received
	 * @param message The opponent and the new board concatenated together  
	 */
	private void getBoard(String message) {
		String[] split = message.split("%");
		String opponent = split[0];
		String board = split[1];
		ClientGameModel model = gameMap.get(opponent);
		model.setBoard(board);
	}

	/**
	 * Inform the player that a game has been requested with them
	 * @param message The name of the opponent
	 */
	private void gameRequest(String message) {
		int response = JOptionPane.showConfirmDialog(null,
				message + " has requested to play a game with you, would you like to play?", "New game request",
				JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
			player.sendMessage(new Message(Message.CS_ACCEPT_REQUEST, message));
			ClientGame game = new ClientGame(player, message, false);
			ClientGameModel gameModel = new ClientGameModel(game);
			ClientGameGUI clientGameGUI = new ClientGameGUI(gameModel);
			clientGameGUI.show();
			GUIMap.put(message, clientGameGUI);
			gameMap.put(message, gameModel);
		}
	}

	/**
	 * Inform the lobby that a client has been sent to it
	 * @param message the name and score of the client concatenated together
	 */
	private void clientRequestResponse(String message) {
		lobby.addClient(message);
	}

}
