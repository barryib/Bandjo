package gui;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class Info extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton Btn_OK = null;

	private JLabel Txt_info = null;

	/**
	 * @param owner
	 */
	public Info(Frame owner, String info) {
		super(owner);
		initialize();
		Txt_info.setText(info);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Info");
		this.setBounds(new Rectangle(390, 230, 345, 121));
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		
		this.setModal(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			Txt_info = new JLabel();
			Txt_info.setBounds(new Rectangle(12, 14, 322, 48));
			Txt_info.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtn_OK(), null);
			jContentPane.add(Txt_info, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes Btn_OK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_OK() {
		if (Btn_OK == null) {
			Btn_OK = new JButton();
			Btn_OK.setBounds(new Rectangle(204, 64, 127, 31));
			Btn_OK.setText("OK");
			Btn_OK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					dispose();
				}
			});
		}
		return Btn_OK;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
