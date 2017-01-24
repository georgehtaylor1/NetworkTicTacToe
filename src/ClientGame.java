/**
 * The client side game
 * @author George
 *
 */
public class ClientGame {

	public static final String CROSS = "X";
	public static final String NOUGHT = "O";
	private static final String BLANK = " ";
	private ClientPlayer p1;
	private String p2;
	private ClientPlayer currentPlayer;
	private String[][] board;
	private boolean myTurn;
	private boolean isCross;
	private boolean isWon;

	/**
	 * The default constructor for the class
	 * @param _p1 The first player in the game
	 * @param _p2 The second player in the game
	 * @param _isCross Whether this player is playing as cross
	 */
	public ClientGame(ClientPlayer _p1, String _p2, boolean _isCross) {
		p1 = _p1;
		p2 = _p2;
		currentPlayer = p1;
		isCross = _isCross;
		myTurn = isCross;
		board = stringToArray("000000000");
		isWon = false;
	}

	/**
	 * Get the first player in the game
	 * @return The first player in the game
	 */
	public ClientPlayer getP1() {
		return p1;
	}

	/**
	 * Set the first player in the game
	 * @param p1 The new first player
	 */
	public void setP1(ClientPlayer p1) {
		this.p1 = p1;
	}

	/**
	 * Get the second player in the game
	 * @return The second player
	 */
	public String getP2() {
		return p2;
	}

	/**
	 * Set the second player in the game
	 * @param p2 The new second player
	 */
	public void setP2(String p2) {
		this.p2 = p2;
	}

	/**
	 * Get the current player in the game
	 * @return The current player
	 */
	public ClientPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Set the current player in the game
	 * @param currentPlayer The new active player
	 */
	public void setCurrentPlayer(ClientPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Execute a turn in the game
	 * @param x The x position of the turn
	 * @param y The y position of the turn
	 */
	public void turn(int x, int y) {
		myTurn = false;
		board[x][y] = (isCross ? CROSS : NOUGHT);
		currentPlayer.sendMessage(new Message(Message.TURN, p2 + "%" + x + "%" + y));
	}

	/**
	 * Get the board value at the specified position
	 * @param i The x position of the requested cell
	 * @param j The y position of the requested cell
	 * @return The cell value at the specified position
	 */
	public String get(int i, int j) {
		return board[i][j];
	}

	/**
	 * Set the board based on the passed string board
	 * @param _board The board string
	 */
	public void setBoard(String _board) {
		myTurn = true;
		board = stringToArray(_board);
	}

	/**
	 * Convert the string board to an integer array
	 * @param board The string board
	 * @return The integer array board
	 */
	private String[][] stringToArray(String board) {
		String[][] a = new String[3][3];
		char[] c = board.toCharArray();
		a[0][0] = XorO(c[0]);
		a[0][1] = XorO(c[1]);
		a[0][2] = XorO(c[2]);
		a[1][0] = XorO(c[3]);
		a[1][1] = XorO(c[4]);
		a[1][2] = XorO(c[5]);
		a[2][0] = XorO(c[6]);
		a[2][1] = XorO(c[7]);
		a[2][2] = XorO(c[8]);
		return a;
	}

	/**
	 * Convert a character to the relevant integer
	 * @param c The character being converted
	 * @return The integer value corresponding to that character
	 */
	private String XorO(char c) {
		switch (c) {
		case '1':
			return CROSS;
		case '2':
			return NOUGHT;
		default:
			return BLANK;
		}
	}

	/**
	 * Return a boolean indicating whether it is the players turn
	 * @return
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	/**
	 * Return whether the current player 
	 * @return Whether the current player is playing as crosses
	 */
	public boolean getIsCross() {
		return isCross;
	}

	/**
	 * Sets the current players isCross field
	 * @param isCross The new value of 'isCross'
	 */
	public void setIsCross(boolean isCross) {
		this.isCross = isCross;
	}

	/**
	 * Return whether the game is won
	 * @return Whether the game is won
	 */
	public boolean isWon() {
		return isWon;
	}

	/**
	 * Set whether the game is won
	 * @param isWon The new value of the isWon variable
	 */
	public void setWon(boolean isWon) {
		this.isWon = isWon;
	}

	/**
	 * Forfeit the game for the current player
	 */
	public void forfeit() {
		currentPlayer.sendMessage(new Message(Message.CLIENT_FORFEIT, p2));		
	}
}
