import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * A class representing a player on the server side
 * @author George
 *
 */
public class ServerPlayer extends Player{

	/**
	 * The default constructor for the player
	 * @param _name The nickname of the player
	 * @param _fromNetwork The BufferedReader object responsible for reading messages from the client
	 * @param _toNetwork The PrintStream object that sends messages to the client
	 */
	public ServerPlayer(String _name, BufferedReader _fromNetwork, PrintStream _toNetwork) {
		super(_name, _fromNetwork, _toNetwork);
	}

	/**
	 * Wait for a message from a client then return it
	 */
	@Override
	public Message receiveMessage() {
		try {
			String[] read  = getFromNetwork().readLine().split("@");
			Message message = new Message(read[0], read[1]);
			System.out.println(super.getName() + " received message: " + message.getMessageType() + ", " + message.getMessage());
			return message;
		} catch (IOException e) {
			System.err.println("Error receiving message from client " + getName());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Send a message to the client
	 */
	@Override
	public void sendMessage(Message message) {
		System.out.println("Sending message: " + message.getMessageType() + ", " + message.getMessage());
		getToNetwork().println(message.getMessageType() + "@" + message.getMessage());
	}
	
}
