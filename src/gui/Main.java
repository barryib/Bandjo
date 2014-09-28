package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import network.Canal;
import codec.Binary;
import codec.Data;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel JMainPanel = null;

	private JButton Btn_Simulation = null;

	private JButton Btn_Network = null;

	private JButton Btn_Recepteur = null;
	
	public Simulateur s=null;

	/**
	 * This is the default constructor
	 */
	public Main() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(257, 171);
		this.setName("main");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJMainPanel());
		this.setTitle("BAN'DJO | Main");
		this.setResizable(false);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				//System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes JMainPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJMainPanel() {
		if (JMainPanel == null) {
			JMainPanel = new JPanel();
			JMainPanel.setLayout(null);
			JMainPanel.add(getBtn_Simulation(), null);
			JMainPanel.add(getBtn_Network(), null);
			JMainPanel.add(getBtn_Recepteur(), null);
		}
		return JMainPanel;
	}

	/**
	 * This method initializes Btn_Simulation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Simulation() {
		if (Btn_Simulation == null) {
			Btn_Simulation = new JButton();
			Btn_Simulation.setBounds(new Rectangle(12, 7, 227, 41));
			Btn_Simulation.setText("Simulation");
			Btn_Simulation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					s = new Simulateur(null);
					s.setBounds(JMainPanel.getX()+JMainPanel.getWidth()+20, JMainPanel.getY()+JMainPanel.getHeight()+20, s.getWidth(), s.getHeight());
					s.setEnabled(true);
					//s.setModal(true);
					s.setVisible(true);
				}
			});
		}
		return Btn_Simulation;
	}

	/**
	 * This method initializes Btn_Network	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Network() {
		if (Btn_Network == null) {
			Btn_Network = new JButton();
			Btn_Network.setBounds(new Rectangle(12, 51, 227, 41));
			Btn_Network.setText("Emetteur");
			Btn_Network.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					Emetteur emeteur = new Emetteur(null);
					emeteur.setBounds(JMainPanel.getX()+JMainPanel.getWidth()+20, JMainPanel.getY()+JMainPanel.getHeight()+20, emeteur.getWidth(), emeteur.getHeight());
					emeteur.setEnabled(true);
					//emeteur.setModal(true);
					emeteur.setVisible(true);
				}
			});
		}
		return Btn_Network;
	}
	
	/**
	 * This method initializes Btn_Recepteur	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Recepteur() {
		if (Btn_Recepteur == null) {
			Btn_Recepteur = new JButton();
			Btn_Recepteur.setBounds(new Rectangle(12, 98, 227, 41));
			Btn_Recepteur.setText("Récepteur");
			Btn_Recepteur.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					Recepteur r = new Recepteur(null);
					r.setBounds(JMainPanel.getX()+JMainPanel.getWidth()+20, JMainPanel.getY()+JMainPanel.getHeight()+20, r.getWidth(), r.getHeight());
					r.setEnabled(true);
					//r.setModal(true);
					r.setVisible(true);
				}
			});
		}
		return Btn_Recepteur;
	}

	/*public static void main(String[] args){
		(new Main()).setVisible(true);
	}*/

}  //  @jve:decl-index=0:visual-constraint="86,42"
