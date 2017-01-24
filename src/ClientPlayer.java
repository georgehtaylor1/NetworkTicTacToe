import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * A client player with methods for sending and receiving from the server
 * @author George
 *
 */
public class ClientPlayer extends Player {

	/**
	 * The default constructor for the player
	 * @param _name The nickname of the player
	 * @param _fromNetwork The BufferedReader object that receives messages from the server
	 * @param _toNetwork The PrintStream object that sends messages to the server
	 */
	public ClientPlayer(String _name, BufferedReader _fromNetwork, PrintStream _toNetwork) {
		super(_name, _fromNetwork, _toNetwork);
	}

	/**
	 * Responsible for receiving messages from the server and ensuring any errors are handled
	 */
	@Override
	public Message receiveMessage() {
		try {
			String[] read = getFromNetwork().readLine().split("@");
			Message message = new Message(read[0], read[1]);
			System.out.println(
					super.getName() + " received message: " + message.getMessageType() + ", " + message.getMessage());
			return message;
		} catch (IOException e) {
			System.err.println("Error receiving message from server " + getName());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Responsible for sending messages to the server
	 */
	@Override
	public void sendMessage(Message message) {
		System.out.println("Sending message: " + message.getMessageType() + ", " + message.getMessage());
		PrintStream toNetwork = getToNetwork();
		toNetwork.println(message.getMessageType() + "@" + message.getMessage());
	}
}
