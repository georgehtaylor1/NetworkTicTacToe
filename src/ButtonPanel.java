import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The button panel on the client game GUI
 * @author George
 *
 */
public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 4681321756466834835L;

	/**
	 * The default constructor for the button panel
	 * @param model The model on which it is based
	 */
	public ButtonPanel(ClientGameModel model) {
		super();

		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.forfeit();
			}

		});
		add(exit);
	}
}
