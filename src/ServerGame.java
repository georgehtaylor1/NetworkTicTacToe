import java.io.Serializable;

/**
 * The game as seen by the server
 * @author George
 *
 */
public class ServerGame implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean p1Turn;
	private int[][] board;

	private static final int BLANK = 0;
	private static final int CROSS = 1;
	private static final int NOUGHT = 2;

	private ServerPlayer p1, p2;
	private ServerPlayer currentPlayer;

	/**
	 * The default constructor for the server game
	 * @param _p1 The first server player
	 * @param _p2 The second server player
	 */
	public ServerGame(ServerPlayer _p1, ServerPlayer _p2) {
		setP1(_p1);
		setP2(_p2);
		currentPlayer = getP1();

		board = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = BLANK;
			}
		}
	}

	/**
	 * get the current server player
	 * @return the current server player
	 */
	public ServerPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * returns whether it's crosses turn
	 * @return Whether it is crosses turn
	 */
	public boolean isCrossTurn() {
		return p1Turn;
	}

	/**
	 * Return the board string
	 * @return the board string
	 */
	public String getBoard() {
		String r = "";
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				r = r + board[y][x];
			}
		}
		return r;
	}

	/**
	 * Create a print out of the board
	 */
	public String toString() {
		String s = (currentPlayer == getP1() ? "Cross : " : "Nought : ");
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				s = s + board[y][x];
			}
			s = s + " ";
		}
		return s;
	}

	/**
	 * Process a turn in the server
	 * @param i The x position of the turn
	 * @param j The y position of the turn
	 */
	public void turn(int i, int j) {
		if (board[i][j] == BLANK) {
			if (currentPlayer == getP1()) {
				board[i][j] = CROSS;
			} else {
				board[i][j] = NOUGHT;
			}
			if (currentPlayer == getP1())
				currentPlayer = getP2();
			else
				currentPlayer = getP1();
		} else {
			throw new IllegalArgumentException("Board not empty at (" + i + ", " + j + ")");
		}
	}

	/**
	 * Determine if the given player has won the game
	 * @param player The given player to check
	 * @return Whether the given player has won
	 */
	private boolean winner(int player) {
		return (board[0][0] == player && board[0][1] == player && board[0][2] == player)
				|| (board[1][0] == player && board[1][1] == player && board[1][2] == player)
				|| (board[2][0] == player && board[2][1] == player && board[2][2] == player)
				|| (board[0][0] == player && board[1][0] == player && board[2][0] == player)
				|| (board[0][1] == player && board[1][1] == player && board[2][1] == player)
				|| (board[0][2] == player && board[1][2] == player && board[2][2] == player)
				|| (board[0][0] == player && board[1][1] == player && board[2][2] == player)
				|| (board[0][2] == player && board[1][1] == player && board[2][0] == player);
	}

	/**
	 * Get the game winner (if one exists)
	 * @return The winner of the game
	 */
	public ServerPlayer whoWon() {
		if (winner(CROSS)) {
			return getP1();
		} else if (winner(NOUGHT)) {
			return getP2();
		} else {
			return null;
		}
	}

	/**
	 * Return whether the game has been won
	 * @return Whether the game has been won
	 */
	public boolean isWon() {
		if (winner(CROSS) || winner(NOUGHT))
			return true;
		else
			return false;
	}

	/**
	 * Print the board to the console
	 * @param b The board to print
	 */
	public static void printBoard(String b) {
		char[] c = b.toCharArray();
		System.out.println(c[0] + "|" + c[1] + "|" + c[2]);
		System.out.println(c[3] + "|" + c[4] + "|" + c[5]);
		System.out.println(c[6] + "|" + c[7] + "|" + c[8]);
	}

	/**
	 * Get the first player
	 * @return the first player
	 */
	public ServerPlayer getP1() {
		return p1;
	}

	/**
	 * Set the first player
	 * @param p1 The first player
	 */
	public void setP1(ServerPlayer p1) {
		this.p1 = p1;
	}

	/**
	 * Get the second player
	 * @return The second player
	 */
	public ServerPlayer getP2() {
		return p2;
	}

	/**
	 * Set the second player
	 * @param p2 The second player
	 */
	public void setP2(ServerPlayer p2) {
		this.p2 = p2;
	}

}
