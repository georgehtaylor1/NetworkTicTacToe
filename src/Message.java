/**
 * A class for organising the messages that get sent between server and clients
 * @author George
 *
 */
public class Message {

	// Constants being used as message headers
	public static final String CS_GAME_REQUEST = "csgreq"; // c->s :: client requested game to server
	public static final String SC_GAME_REQUEST = "scgreq"; // s->c :: server forwarded game request
	public static final String REFRESH_CLIENTS = "refcli"; // s->c :: tell the client to refresh its client list
	public static final String REQUEST_CLIENTS = "reqcli"; // c->s :: Client requested client list
	public static final String TURN = "turn"; // c->s :: Client sent move to server
	public static final String BOARD = "board"; // s->c :: Server sent board to client
	public static final String NEW_GAME = "newgame"; // s->c :: Game started with opponent
	public static final String CS_ACCEPT_REQUEST = "csaccreq"; // c->s :: Game request accepted by opponent
	public static final String SC_ACCEPT_REQUEST = "scaccreq"; // c->s :: Game request accepted by opponent
	public static final String INVALID_MESSAGE = "invalid"; // any :: Received invalid message
	public static final String CLIENT_REQUEST_RESPONSE = "clireqres"; // s->c :: Sends a client name
	public static final String GAME_OVER = "gameover"; // s->c :: Informs the clients that the game has been won
	public static final String CLIENT_DISCONNECTED = "clientdisconnect"; // c->s :: The client disconnected
	public static final String CLIENT_FORFEIT = "clientforfeit"; // c->s :: The client forfeit the game
	public static final String GAME_OPPONENT_FORFEIT = "gameforfeit"; // s->c :: Tell the client the opponent forfeit
	public static final String GAME_PLAYER_FORFEIT = "gameforfeit2"; // S->C :: Tell the client they forfeit the game
	public static final String CLIENT_NAME_RESPONSE = "clinameres"; //s->c :: Give the client their assigned name
	public static final String CLIENT_SCORE = "clientscore"; // s->c :: Give the client their score
		
	private String messageType;
	private String message;
	
	/**
	 * The default constructor for the class
	 * @param _messageType The type of the message (one of the above constants
	 * @param _message The content of the message
	 */
	public Message(String _messageType, String _message){
		messageType = _messageType;
		message = _message;
	}

	/**
	 * Get the message content
	 * @return The message content
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message content
	 * @param message The message content
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the type of the message
	 * @return The type of the message
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * Set the type of the message
	 * @param messageType The type of the message
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}
