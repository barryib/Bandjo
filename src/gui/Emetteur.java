package gui;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.WindowConstants;

import network.Canal;
import network.SimpleWrite;

import codec.Binary;
import codec.CoDec;
import codec.Data;

public class Emetteur extends JDialog {

	private static final long serialVersionUID = 1L;
	/*
	 * D�claration du codec
	 */
	
	private CoDec codec = new CoDec();  //  @jve:decl-index=0:
	private byte[] infoASCII = null; 
	private Binary[] infoCode= null;
	String[] strMatrices = { "Matrices ["+(new Binary(7)).toString(codec.getM())+"] , ["+ (new Binary(5)).toString(codec.getM())+"]",
							 "Matrices ["+(new Binary(7)).toString(codec.getM())+"] , ["+ (new Binary(7)).toString(codec.getM())+"]",
							 "Matrices ["+(new Binary(5)).toString(codec.getM())+"] , ["+ (new Binary(7)).toString(codec.getM())+"]" };

	Binary[][] matrices  = { {(new Binary(7)) ,(new Binary(5))},
			 				 {(new Binary(7)) ,(new Binary(7))},
			 				 {(new Binary(5)) ,(new Binary(7))} };
	
	String[] strPorts = { "COM0", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6" };
	
	Binary[] G = matrices[0];

	/*
	 * D�claration des �l�ments de la fen�tre
	 */
	

	private JPanel JSimuPanel = null;

	private JTextPane Txt_info = null;

	private JTextArea Txt_Code = null;

	private JLabel jLabel = null;

	private JComboBox Select_mat = null;

	private JButton Btn_Coder = null;

	private JButton Btn_Reset = null;

	private JButton Btn_Envoyer = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextArea Txt_Info_ASCII = null;

	private JProgressBar jProgressBar = null;

	private JCheckBox CBox_VerifAuto = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JCheckBox CBox_EtatDecodage = null;

	private JComboBox Select_port = null;

	/**
	 * @param owner
	 */
	public Emetteur(Frame owner) {
		super(owner);
		initialize();
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(587, 345);
		this.setTitle("BAN'DJO | Mode Emetteur");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJSimuPanel());
		this.setResizable(false);
	}

	/**
	 * This method initializes JSimuPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJSimuPanel() {
		if (JSimuPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(304, 111, 184, 20));
			jLabel4.setText("Etre informé sur le décodage");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(52, 111, 199, 20));
			jLabel3.setText("Coloration auto. à la réception");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(16, 136, 280, 19));
			jLabel2.setText("Texte codé en ASCII");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 223, 277, 19));
			jLabel1.setText("Information codée (codage convolutionel)");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 14, 166, 19));
			jLabel.setText("Saisir un texte ici...");
			JSimuPanel = new JPanel();
			JSimuPanel.setLayout(null);
			JSimuPanel.add(getTxt_info(), null);
			JSimuPanel.add(getTxt_Code(), null);
			JSimuPanel.add(jLabel, null);
			JSimuPanel.add(getSelect_mat(), null);
			JSimuPanel.add(getBtn_Coder(), null);
			JSimuPanel.add(getBtn_Reset(), null);
			JSimuPanel.add(getBtn_Envoyer(), null);
			JSimuPanel.add(jLabel1, null);
			JSimuPanel.add(jLabel2, null);
			JSimuPanel.add(getTxt_Info_ASCII(), null);
			JSimuPanel.add(getJProgressBar(), null);
			JSimuPanel.add(getCBox_VerifAuto(), null);
			JSimuPanel.add(jLabel3, null);
			JSimuPanel.add(jLabel4, null);
			JSimuPanel.add(getCBox_EtatDecodage(), null);
			JSimuPanel.add(getSelect_port(), null);
		}
		return JSimuPanel;
	}

	/**
	 * This method initializes Txt_info	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getTxt_info() {
		if (Txt_info == null) {
			Txt_info = new JTextPane();
			Txt_info.setBounds(new Rectangle(16, 40, 555, 31));
		}
		return Txt_info;
	}

	/**
	 * This method initializes Txt_Code	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTextArea getTxt_Code() {
		if (Txt_Code == null) {
			Txt_Code = new JTextArea();
			Txt_Code.setBounds(new Rectangle(16, 243, 555, 66));
			Txt_Code.setEditable(false);
			Txt_Code.setLineWrap(true);
		}
		return Txt_Code;
	}

	/**
	 * This method initializes Select_mat	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSelect_mat() {
		if (Select_mat == null) {
			Select_mat = new JComboBox(strMatrices);
			Select_mat.setBounds(new Rectangle(14, 79, 208, 26));
			Select_mat.setSelectedIndex(0);
			Select_mat.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					JComboBox cb = (JComboBox)e.getSource();
			        codec.setG(matrices[cb.getSelectedIndex()]);
			        if(!Txt_Code.getText().equalsIgnoreCase("")){
			        	Btn_Coder.doClick();
			        }
			        	
			       // for(int i =0; i<G.length; i++)
			        //	System.out.println(codec.getG()[i]);
				}
			});
		}
		return Select_mat;
	}

	/**
	 * This method initializes Btn_Coder	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Coder() {
		if (Btn_Coder == null) {
			Btn_Coder = new JButton();
			Btn_Coder.setBounds(new Rectangle(261, 79, 97, 32));
			Btn_Coder.setText("CODER");
			Btn_Coder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					if(Txt_info.getText().equalsIgnoreCase(""))
						(new Info(null,"Erreur: Aucun texte a coder.")).setVisible(true);
					else{
						try {
							infoASCII = codec.stringToASCII(Txt_info.getText());
							String chaine = "";
							
							for(int i=0;i<infoASCII.length;i++)
								chaine+=Integer.toBinaryString(infoASCII[i])+" ";
							Txt_Info_ASCII.setText(chaine);
							
							infoCode = codec.coder(Binary.byteToBinaryArray(infoASCII));
							chaine="";
							for(int i=0;i<infoCode.length;i++)
								chaine+=infoCode[i].toString(codec.getN())+" ";
							Txt_Code.setText(chaine);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return Btn_Coder;
	}

	/**
	 * This method initializes Btn_Reset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Reset() {
		if (Btn_Reset == null) {
			Btn_Reset = new JButton();
			Btn_Reset.setBounds(new Rectangle(473, 79, 97, 32));
			Btn_Reset.setText("RESET");
			Btn_Reset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					Txt_info.setText("");
					Txt_Info_ASCII.setText("");
					Txt_Code.setText("");
					Select_mat.setSelectedIndex(0);
				}
			});
		}
		return Btn_Reset;
	}

	/**
	 * This method initializes Btn_Envoyer	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Envoyer() {
		if (Btn_Envoyer == null) {
			Btn_Envoyer = new JButton();
			Btn_Envoyer.setBounds(new Rectangle(360, 79, 106, 32));
			Btn_Envoyer.setText("ENVOYER");
			Btn_Envoyer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					if(Txt_Code.getText().equalsIgnoreCase("")){
						(new Info(null,"Erreur: Aucun code à envoyer.")).setVisible(true);
					}
					else{
						SimpleWrite sw = new SimpleWrite();
						sw.write(Txt_Code.getText(), strPorts[getSelect_port().getSelectedIndex()]);
					}
					
				}
			});
		}
		return Btn_Envoyer;
	}

	/**
	 * This method initializes Txt_Info_ASCII	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTextArea getTxt_Info_ASCII() {
		if (Txt_Info_ASCII == null) {
			Txt_Info_ASCII = new JTextArea();
			Txt_Info_ASCII.setBounds(new Rectangle(16, 155, 555, 66));
			Txt_Info_ASCII.setEditable(false);
			Txt_Info_ASCII.setLineWrap(true);
		}
		return Txt_Info_ASCII;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBounds(new Rectangle(16, 310, 555, 10));
		}
		return jProgressBar;
	}

	/**
	 * This method initializes CBox_VerifAuto	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCBox_VerifAuto() {
		if (CBox_VerifAuto == null) {
			CBox_VerifAuto = new JCheckBox();
			CBox_VerifAuto.setBounds(new Rectangle(247, 107, 32, 28));
		}
		return CBox_VerifAuto;
	}

	/**
	 * This method initializes CBox_EtatDecodage	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCBox_EtatDecodage() {
		if (CBox_EtatDecodage == null) {
			CBox_EtatDecodage = new JCheckBox();
			CBox_EtatDecodage.setBounds(new Rectangle(488, 107, 32, 28));
		}
		return CBox_EtatDecodage;
	}

	/**
	 * This method initializes Select_port	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSelect_port() {
		if (Select_port == null) {
			Select_port = new JComboBox(strPorts);
			Select_port.setBounds(new Rectangle(410, 9, 162, 26));
		}
		return Select_port;
	}	
}  //  @jve:decl-index=0:visual-constraint="10,10"
