import java.util.Map;

/**
 * The class responsible for listening to a specific client and responding to it's messages
 * @author George
 *
 */
public class ServerListener extends Thread {

	private ServerPlayer player;
	private ClientTable clients;
	private ServerGameMap gameMap;
	private boolean clientConnected;

	/**
	 * The default constructor for the class
	 * @param _player The player that the thread listens to
	 * @param _clients A map of the connected clients and their associated Player objects
	 * @param _gameMap A map of the games and the associated ServerGame object
	 */
	public ServerListener(ServerPlayer _player, ClientTable _clients, ServerGameMap _gameMap) {
		player = _player;
		clients = _clients;
		gameMap = _gameMap;
	}

	/**
	 * Run the listener thread
	 */
	public void run() {

		System.out.println("Listener started for: " + player.getName());
		clientConnected = true;
		while (clientConnected) {

			Message message = player.receiveMessage();
			String messageType = message.getMessageType();

			if (messageType.equals(Message.REQUEST_CLIENTS))
				requestClients(message.getMessage());

			else if (messageType.equals(Message.CS_GAME_REQUEST))
				gameRequest(message.getMessage());

			else if (messageType.equals(Message.TURN))
				turn(message.getMessage());

			else if (messageType.equals(Message.CS_ACCEPT_REQUEST))
				acceptGameRequest(message.getMessage());

			else if (messageType.equals(Message.CLIENT_DISCONNECTED))
				clientConnected = false;

			else if (messageType.equals(Message.CLIENT_FORFEIT))
				clientForfeit(message.getMessage());
		}

		System.out.println(player.getName() + " has disconnected");
		for (Map.Entry<String, ServerPlayer> entry : clients.getClientTable().entrySet()) {
			entry.getValue().sendMessage(new Message(Message.REFRESH_CLIENTS, "x"));
		}
		clients.remove(player);
		player.close();
	}

	/**
	 * Inform the server that a client has forfeit
	 * @param message The opponent of the forfeiting client 
	 */
	private void clientForfeit(String message) {
		ServerPlayer opponentPlayer = clients.getClient(message);

		player.sendMessage(new Message(Message.GAME_PLAYER_FORFEIT, message));
		opponentPlayer.sendMessage(new Message(Message.GAME_OPPONENT_FORFEIT, player.getName()));
		gameMap.remove(player.getName(), opponentPlayer.getName());
		opponentPlayer.won();
		for (Map.Entry<String, ServerPlayer> entry : clients.getClientTable().entrySet()) {
			entry.getValue().sendMessage(new Message(Message.REFRESH_CLIENTS, "x"));
		}
	}

	/**
	 * Inform the server that a game request has been accepted
	 * @param message The opponent (the player that sent the original request)
	 */
	private void acceptGameRequest(String message) {
		ServerPlayer opponentPlayer = clients.getClient(message);
		opponentPlayer.sendMessage(new Message(Message.SC_ACCEPT_REQUEST, player.getName()));

		ServerGame serverGame = new ServerGame(player, opponentPlayer);
		gameMap.add(serverGame);
	}

	/**
	 * Receive a turn from a player
	 * @param message the opponent and th x, y moves concatenated together
	 */
	private void turn(String message) {
		String[] split = message.split("%");

		System.out.println("Turn for: " + split[0]);
		System.out.println("x : " + split[1]);
		System.out.println("y : " + split[2]);

		String opponent = split[0];
		int x = Integer.parseInt(split[1]);
		int y = Integer.parseInt(split[2]);
		ServerGame game = gameMap.getClient(player.getName(), opponent);
		game.turn(x, y);

		ServerPlayer opponentPlayer = clients.getClient(opponent);
		ServerPlayer whoWon = game.whoWon();
		opponentPlayer.sendMessage(new Message(Message.BOARD, player.getName() + "%" + game.getBoard()));

		// Is the game won?
		if (whoWon != null) {
			// game won
			player.sendMessage(new Message(Message.GAME_OVER, opponent + "%won"));
			opponentPlayer.sendMessage(new Message(Message.GAME_OVER, player.getName() + "%lost"));
			gameMap.remove(player.getName(), opponentPlayer.getName());
			player.won();
			for (Map.Entry<String, ServerPlayer> entry : clients.getClientTable().entrySet()) {
				entry.getValue().sendMessage(new Message(Message.REFRESH_CLIENTS, "x"));
			}
		}
	}

	/**
	 * Forward a game request to the relevant client
	 * @param message The target client of the request
	 */
	private void gameRequest(String message) {
		ServerPlayer opponent = clients.getClient(message);
		opponent.sendMessage(new Message(Message.SC_GAME_REQUEST, player.getName()));
	}

	/**
	 * The client has requested a list of other connected clients
	 * @param message
	 */
	private void requestClients(String message) {
		System.out.println("Sending clients... ");
		String[] clientList = clients.getClientNames(player.getName());
		for (String client : clientList) {
			player.sendMessage(new Message(Message.CLIENT_REQUEST_RESPONSE, client));
		}
		player.sendMessage(new Message(Message.CLIENT_SCORE, "" + player.getScore()));
		System.out.println("Done");
	}
}
