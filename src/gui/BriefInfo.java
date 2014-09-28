package gui;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JWindow;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class BriefInfo extends JWindow {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel Txt_Info = null;

	/**
	 * @param owner
	 */
	public BriefInfo(Frame owner, String info) {
		super(owner);
		initialize();
		Txt_Info.setText(info);
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setBounds(new Rectangle(390, 230, 301, 86));
		this.setContentPane(getJContentPane());
		this.setEnabled(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			Txt_Info = new JLabel();
			Txt_Info.setBounds(new Rectangle(14, 14, 271, 56));
			Txt_Info.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(Txt_Info, null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
