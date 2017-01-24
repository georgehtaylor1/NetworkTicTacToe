import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * The class that sets up and starts the lobby and client listeners
 * @author George
 *
 */
public class Client {

	/**
	 * Connect to the server and provide the clients name, wait for
	 * the server to respond with the assigned name of the client then
	 * start the listener object for the client.
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Usage: java Client <user-nickname> <port number> <hostname>");
			System.exit(1);
		} else if (args[0].contains("%")) {
			System.err.println("Usage: nickname cannot contain '%'");
			System.exit(1);
		}

		String nickname = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String hostname = args[2];

		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;

		try {
			server = new Socket(hostname, portNumber);
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1);
		}

		ClientPlayer player = new ClientPlayer(nickname, fromServer, toServer);
		player.sendMessage(new Message(Message.REFRESH_CLIENTS, nickname));

		Message message = player.receiveMessage();
		player.setName(message.getMessage());

		JOptionPane.showMessageDialog(null, "You have been assigned the name: " + player.getName(), "Name assigned",
				JOptionPane.PLAIN_MESSAGE);

		Lobby lobby = new Lobby(player);
		LobbyGUI clientLobby = new LobbyGUI(player, lobby);
		clientLobby.show();

		ClientListener clientListener = new ClientListener(player, lobby);
		clientListener.start();
	}
}
