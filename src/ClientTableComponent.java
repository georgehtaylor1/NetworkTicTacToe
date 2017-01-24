import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;

/**
 * A JTable that observes the lobby model in order to display the active clients
 * @author George
 *
 */
public class ClientTableComponent extends JTable implements Observer{

	private static final long serialVersionUID = 961580743600551925L;
	
	private Lobby lobby;
	
	/**
	 * The default constructor for the class
	 * @param _lobby The lobby object it is based on
	 */
	public ClientTableComponent(Lobby _lobby){
		super();
		lobby = _lobby;
		this.setSelectionModel(new ForcedListSelectionModel());
	}	
	
	/**
	 * Update the table based on the data provided by the lobby
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		this.setModel(lobby.getClients());
		this.repaint();
	}

	/**
	 * Get the name of the user selected from the table
	 * @return the name of the user selected in the table
	 */
	public String getSelectedUser() {
		return (String) this.getValueAt(getSelectedRow(), 0);
	}

}