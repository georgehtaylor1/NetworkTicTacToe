import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

/**
 * Code sourced from: http://stackoverflow.com/questions/18309113/jtable-how-to-force-user-to-select-exactly-one-row
 * @author George
 * Ensures that a row is selected
 */
public class ForcedListSelectionModel extends DefaultListSelectionModel{

	private static final long serialVersionUID = 1L;

	public ForcedListSelectionModel () {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
	
}
