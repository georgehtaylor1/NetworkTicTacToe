import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Stores the map of names to game servers
 * @author George
 *
 */
public class ServerGameMap {

	private Map<String, ServerGame> gameMap;
	
	/**
	 * The default constructor for the map
	 */
	public ServerGameMap(){
		gameMap = new TreeMap<String, ServerGame>();
	}
	
	/**
	 * Add a server game to the map
	 * @param game The game to add to the map
	 */
	public void add(ServerGame game) {
		gameMap.put(game.getP1().getName() + game.getP2().getName(), game);
		gameMap.put(game.getP2().getName() + game.getP1().getName(), game);
	}

	/**
	 * Get the game corresponding to the two provided names
	 * @param n1 The first name
	 * @param n2 The second name
	 * @return The corresponding game
	 */
	public ServerGame getClient(String n1, String n2) {
		System.out.println("Searching for: " + n1 + n2);
		
		ArrayList<String> clients = new ArrayList<String>();
		for(Map.Entry<String, ServerGame> game : gameMap.entrySet()){
			System.out.println(game.getKey() + " - " + game.getValue().getBoard());
		}
		
		return gameMap.get(n1 + n2);
	}
	
	/**
	 * Remove the game from the map that corresponds to the two provided players
	 * @param n1 The first name
	 * @param n2 The second name
	 */
	public void remove(String n1, String n2){
		gameMap.remove(n1 + n2);
		gameMap.remove(n2 + n1);
	}
	
}
