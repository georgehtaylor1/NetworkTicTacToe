import java.util.ArrayList;
import java.util.Observable;

import javax.swing.table.DefaultTableModel;

/**
 * The lobby class contains methods relevant to the operation of the lobby 
 * @author George
 *
 */
public class Lobby extends Observable {

	private ClientPlayer player;
	private ArrayList<String> clients;
	private boolean closed;
	private int score;

	/**
	 * The default constructor for the lobby
	 * @param _player The player for whom the lobby is operating
	 */
	public Lobby(ClientPlayer _player) {
		player = _player;
		clients = new ArrayList<String>();
		setClosed(false);
	}

	/**
	 * Send a game request to the server
	 * @param opponent The opponent being requested
	 */
	public void requestGame(String opponent) {
		player.sendMessage(new Message(Message.CS_GAME_REQUEST, opponent));
	}

	/**
	 * Ass a client to the list of clients and notify any observers
	 * @param client The new client
	 */
	public void addClient(String client) {
		clients.add(client);
		setChanged();
		notifyObservers();
	}

	/**
	 * Refresh the list of clients
	 */
	public void resetClients() {
		clients.clear();
		player.sendMessage(new Message(Message.REQUEST_CLIENTS, player.getName()));
	}

	/**
	 * Populates the client table model
	 * @return The new client table model
	 */
	public DefaultTableModel getClients() {
		DefaultTableModel table = new DefaultTableModel() {

			private static final long serialVersionUID = 4659287938730002085L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		table.setColumnIdentifiers(new String[] { "Player:", "Score:" });
		System.out.println("Creating client table");
		clients.remove(player.getName());
		for (String c : clients) {
			String[] split = c.split("%");
			table.addRow(new String[] { split[0], split[1] });
		}
		return table;
	}

	/**
	 * indicates whether the lobby has closed
	 * @return Whether or not the lobby has closed
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * Set the lobby to either closed or open
	 * @param closed Whether the lobby should be closed or open
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	/**
	 * Set the score stored by the lobby
	 * @param _score The new score
	 */
	public void setScore(int _score) {
		System.out.println("Setting score");
		score = _score;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get the score stored by the lobby
	 * @return The score
	 */
	public int getScore() {
		return score;
	}
}
