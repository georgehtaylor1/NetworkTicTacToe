import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * The class responsible for creating server listeners when clients connect
 * @author George
 *
 */
public class Server {

	static ClientTable clients = null;
	
	/**
	 * Wait for a client to connect then assign it a valid name then start a listener
	 * object for that client
	 * @param args
	 */
	public static void main(String[] args) {

		if(args.length != 1){
			System.err.println("Usage: java Server <port number>");
			System.exit(0);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		
		clients = new ClientTable();
		ServerGameMap gameMap = new ServerGameMap();

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.err.println("Couldn't listen on port " + portNumber);
			System.exit(1);
		}

		try {
			while (true) {
				System.out.println("Waiting for client");
				Socket socket = serverSocket.accept();
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream toClient = new PrintStream(socket.getOutputStream());
				String[] message = fromClient.readLine().split("@");
				String clientName;
				if (message[0].equals(Message.REFRESH_CLIENTS)) {
					clientName = message[1];
					
					int i = 0;
					String originalName = clientName;
					while(!isNameValid(clientName)){
						i++;
						clientName = originalName + " (" + i + ")";
					}
					
					System.out.println(clientName + " connected");
					ServerPlayer player = new ServerPlayer(clientName, fromClient, toClient);	
					
					player.sendMessage(new Message(Message.CLIENT_NAME_RESPONSE, clientName));
					
					clients.add(player);
					ServerListener listener = new ServerListener(player, clients, gameMap);
					listener.start();
					
					for(Map.Entry<String, ServerPlayer> entry : clients.getClientTable().entrySet()){
						entry.getValue().sendMessage(new Message(Message.REFRESH_CLIENTS, "x"));
					}
					
				} else {
					fromClient.readLine();
					System.err.println("Invalid message received when waiting for clients");
					toClient.println(Message.INVALID_MESSAGE);
					toClient.println("Invalid message received when waiting for clients");
				}
			}
		} catch (IOException e) {
			System.err.println("IO error " + e.getMessage());
		}
	}

	/**
	 * Determine if the name is valid
	 * @param clientName The proposed name
	 * @return A boolean value indicating whether the name is valid
	 */
	private static boolean isNameValid(String clientName) {
		for(Map.Entry<String, ServerPlayer> entry : clients.getClientTable().entrySet()){
			if(entry.getKey().equals(clientName)) return false;
		}
		return true;
	}

}
