import java.util.Observable;

/**
 * A model for the client side game
 * @author George
 *
 */
public class ClientGameModel extends Observable {

	private ClientGame game;

	/**
	 * the default constructor for the game model
	 * @param _game The game the model is based on
	 */
	public ClientGameModel(ClientGame _game) {
		game = _game;
	}

	/**
	 * Register a turn in the game
	 * @param x The x position of the move
	 * @param y The y position of the move
	 */
	public void turn(int x, int y) {
		game.turn(x, y);
		setChanged();
		notifyObservers();
	}

	/**
	 * Indicates whether the game is won
	 * @return Whether the game is won
	 */
	public boolean isWon() {
		return game.isWon();
	}

	/**
	 * Sets the game being won
	 * @param won The new value of the won variable
	 */
	public void setWon(boolean won) {
		game.setWon(won);
	}

	/**
	 * Get the value of the board at that position
	 * @param i The x value of the position on the board
	 * @param j The y value of the position on the board
	 * @return The value at that board position
	 */
	public String get(int i, int j) {
		return game.get(i, j);
	}

	/**
	 * Set the board based on the board string
	 * @param board The string representation of the board
	 */
	public void setBoard(String board) {
		System.out.println("Setting board");
		game.setBoard(board);
		System.out.println("Board set");
		System.out.println(super.countObservers());
		setChanged();
		notifyObservers();

	}

	/**
	 * Returns whether it is the current players turn in the game
	 * @return Whether it is the current players turn
	 */
	public boolean isMyTurn() {
		return game.isMyTurn();
	}

	/**
	 * Get the first player
	 * @return the first player
	 */
	public ClientPlayer getP1() {
		return game.getP1();
	}

	/**
	 * Get the second player
	 * @return the second player
	 */
	public String getP2() {
		return game.getP2();
	}

	/**
	 * Mark the game as forfeit
	 */
	public void forfeit() {
		if (!isWon())
			game.forfeit();

	}

}
