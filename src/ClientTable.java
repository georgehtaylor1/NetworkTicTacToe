import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Stores and manages a mapping of the clients to their relevant ServerPlayer objects
 * @author George
 *
 */
public class ClientTable {

	private Map<String, ServerPlayer> clientTable = new TreeMap<String, ServerPlayer>();

	/**
	 * Add a player to the mapping
	 * @param player The new player
	 */
	public void add(ServerPlayer player) {
		clientTable.put(player.getName(), player);
	}

	/**
	 * Get the ServerPlayer object corresponding to the provided nickname
	 * @param nickname The nickname of the player
	 * @return The relevant ServerPlayer object
	 */
	public ServerPlayer getClient(String nickname) {
		return clientTable.get(nickname);
	}

	/**
	 * Retrieve the entire mapping
	 * @return The nickname-ServerPlayer map
	 */
	public Map<String, ServerPlayer> getClientTable() {
		return clientTable;
	}

	/**
	 * Get all of the names of the clients in the map
	 * @param ignore A name to ignore from the map (i.e. the player that requested the clients)
	 * @return A list of the nicknames in the map
	 */
	public String[] getClientNames(String ignore) {
		ArrayList<String> clients = new ArrayList<String>();
		for (Map.Entry<String, ServerPlayer> client : clientTable.entrySet()) {
			if (!ignore.equals(client.getKey()))
				clients.add(client.getKey() + "%" + client.getValue().getScore());
		}
		return clients.toArray(new String[clients.size()]);
	}

	/**
	 * Remove a client from the map
	 * @param player Which player to remove
	 */
	public void remove(ServerPlayer player) {
		clientTable.remove(player.getName(), player);
	}
}
