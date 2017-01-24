import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The abstract player class, inherited by both ClientPlayer and ServerPlayer
 * @author George
 *
 */
public abstract class Player {

	private String name;
	private BufferedReader fromNetwork;
	private PrintStream toNetwork;
	private int score;

	/**
	 * The default constructor for the class
	 * @param _name
	 * @param _fromNetwork
	 * @param _toNetwork
	 */
	public Player(String _name, BufferedReader _fromNetwork, PrintStream _toNetwork) {
		setName(_name);
		setFromNetwork(_fromNetwork);
		setToNetwork(_toNetwork);
		setScore(0);
	}

	/**
	 * Get the players name
	 * @return The name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the player
	 * @param name The new name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the BufferedReader object that stores the incoming messages
	 * @return The BufferedReader object for the player
	 */
	public BufferedReader getFromNetwork() {
		return fromNetwork;
	}

	/**
	 * Set the BufferedReader object
	 * @param fromNetwork The new object
	 */
	public void setFromNetwork(BufferedReader fromNetwork) {
		this.fromNetwork = fromNetwork;
	}

	/**
	 * Get the PrintStream that sends messages to the network
	 * @return The PrintStream that sends messages to the network
	 */
	public PrintStream getToNetwork() {
		return toNetwork;
	}

	/**
	 * Set the PrintStream object
	 * @param toNetwork The new PrintStream object
	 */
	public void setToNetwork(PrintStream toNetwork) {
		this.toNetwork = toNetwork;
	}

	/**
	 * Close the connection to the network for the player
	 */
	public void close() {
		try {
			fromNetwork.close();
			toNetwork.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Send a message to the network
	 * @param message The message to be sent
	 */
	public abstract void sendMessage(Message message);

	/**
	 * Receive a message from the network
	 * @return The received message
	 */
	public abstract Message receiveMessage();

	/**
	 * Get the score of the player
	 * @return The players score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Set the players score
	 * @param score The new score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Increase the players score when they win a game
	 */
	public void won(){
		setScore(getScore() + 1);
	}

}
