import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * The component for displaying the game to the user
 * @author George
 *
 */
public class ClientGameComponent extends JPanel
{

	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor for the class creates and adds components to the view
	 * @param gameModel The game model on which the component is based
	 */
	public ClientGameComponent(ClientGameModel gameModel)
	{
		super();
		
		BoardView board = new BoardView(gameModel);
		ButtonPanel controls = new ButtonPanel(gameModel);
		PromptLabel prompt = new PromptLabel(gameModel);
		JPanel bottomPanel = new JPanel();
		
		gameModel.addObserver(board);
		gameModel.addObserver(prompt);
		
		setLayout(new BorderLayout());
		bottomPanel.setLayout(new BorderLayout());
		
		bottomPanel.add(controls, BorderLayout.EAST);
		bottomPanel.add(prompt, BorderLayout.CENTER);
		
		add(board, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}