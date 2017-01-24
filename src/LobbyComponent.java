import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The class containing the GUI information and construction code
 * @author George
 *
 */
public class LobbyComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	private Lobby lobby;

	private ClientTableComponent clientTable;

	/**
	 * The default constructor for the component
	 * @param _lobby The lobby on which the component is based
	 */
	public LobbyComponent(Lobby _lobby) {
		super();
		lobby = _lobby;

		clientTable = new ClientTableComponent(lobby);
		clientTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int row = clientTable.getSelectedRow();
				if ((row > -1))
					clientTable.setRowSelectionInterval(row, row);
			}

		});
		lobby.addObserver(clientTable);

		ScoreLabel scoreLabel = new ScoreLabel("Your score is: 0", lobby);
		lobby.addObserver(scoreLabel);
		
		JButton request = new JButton("Request game");
		request.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (clientTable.getSelectedRow() != -1)
					lobby.requestGame(clientTable.getSelectedUser());
			}

		});

		JPanel bottomPanel = new JPanel();	
		bottomPanel.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		this.add(clientTable, BorderLayout.CENTER);
		this.add(clientTable.getTableHeader(), BorderLayout.PAGE_START);
		bottomPanel.add(scoreLabel, BorderLayout.NORTH);
		bottomPanel.add(request, BorderLayout.SOUTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}

}