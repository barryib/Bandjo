package gui;

import gnu.io.CommPortIdentifier;

import java.awt.Frame;
import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import network.ModeEvenement;
import codec.Binary;
import codec.CoDec;

public class Recepteur extends JDialog {

	private static final long serialVersionUID = 1L;
	/*
	 * D�claration du codec
	 */
	
	private CoDec codec = new CoDec();  //  @jve:decl-index=0:
	private byte[] infoASCII = null; 
	private Binary[] infoDecode= null;

	Binary[] seqRecue=null;
	String[] strMatrices = { "Matrices ["+(new Binary(7)).toString(codec.getM())+"] , ["+ (new Binary(5)).toString(codec.getM())+"]",
							 "Matrices ["+(new Binary(7)).toString(codec.getM())+"] , ["+ (new Binary(7)).toString(codec.getM())+"]",
							 "Matrices ["+(new Binary(5)).toString(codec.getM())+"] , ["+ (new Binary(7)).toString(codec.getM())+"]" };

	Binary[][] matrices  = { {(new Binary(7)) ,(new Binary(5))},
			 				 {(new Binary(7)) ,(new Binary(7))},
			 				 {(new Binary(5)) ,(new Binary(7))} };
	
	Binary[] G = matrices[0];
	
	String[] strPorts = { "COM0", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6" };
	
	/*
	 * D�claration des �l�ments de la fen�tre
	 */
	

	private JPanel JSimuPanel = null;

	private JTextArea Txt_info = null;

	private JTextArea Txt_SeqRecue = null;

	private JLabel jLabel = null;

	private JComboBox Select_mat = null;

	private JButton Btn_Recevoir = null;

	private JButton Btn_Reset = null;

	private JButton Btn_Decoder = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextArea Txt_Info_ASCII = null;

	private JLabel jLabel3 = null;

	private JTextArea Txt_Decode_ASCII = null;

	private JProgressBar jProgressBar = null;

	private JComboBox Select_port = null;
	
	ModeEvenement modeEve=null;

	/**
	 * @param owner
	 */
	public Recepteur(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(587, 382);
		this.setTitle("BAN'DJO | Mode Récepteur");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJSimuPanel());
		this.setResizable(false);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				//System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				if(modeEve!=null)
					modeEve.stopThread();
			}
		});
		
	}

	/**
	 * This method initializes JSimuPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJSimuPanel() {
		if (JSimuPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(16, 207, 279, 19));
			jLabel3.setText("Texte décodé en ASCII (Par le récepteur)");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(16, 121, 280, 19));
			jLabel2.setText("Texte codé en ASCII (Par l'émetteur)");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 9, 277, 19));
			jLabel1.setText("Séquence reçue");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 296, 485, 19));
			jLabel.setText("Texte décodé");
			JSimuPanel = new JPanel();
			JSimuPanel.setLayout(null);
			JSimuPanel.add(getTxt_info(), null);
			JSimuPanel.add(getTxt_SeqRecue(), null);
			JSimuPanel.add(jLabel, null);
			JSimuPanel.add(getSelect_mat(), null);
			JSimuPanel.add(getBtn_Recevoir(), null);
			JSimuPanel.add(getBtn_Reset(), null);
			JSimuPanel.add(getBtn_Decoder(), null);
			JSimuPanel.add(jLabel1, null);
			JSimuPanel.add(jLabel2, null);
			JSimuPanel.add(getTxt_Info_ASCII(), null);
			JSimuPanel.add(jLabel3, null);
			JSimuPanel.add(getTxt_Decode_ASCII(), null);
			JSimuPanel.add(getJProgressBar(), null);
			JSimuPanel.add(getSelect_port(), null);
		}
		return JSimuPanel;
	}

	/**
	 * This method initializes Txt_info	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextArea getTxt_info() {
		if (Txt_info == null) {
			Txt_info = new JTextArea();
			Txt_info.setBounds(new Rectangle(16, 315, 555, 31));
			Txt_info.setEditable(false);
			Txt_info.setLineWrap(true);
		}
		return Txt_info;
	}

	/**
	 * This method initializes Txt_SeqRecue	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JTextArea getTxt_SeqRecue() {
		if (Txt_SeqRecue == null) {
			Txt_SeqRecue = new JTextArea();
			Txt_SeqRecue.setBounds(new Rectangle(16, 29, 555, 66));
			Txt_SeqRecue.setEditable(false);
			Txt_SeqRecue.setLineWrap(true);
		}
		return Txt_SeqRecue;
	}

	/**
	 * This method initializes Select_mat	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSelect_mat() {
		if (Select_mat == null) {
			Select_mat = new JComboBox(strMatrices);
			Select_mat.setBounds(new Rectangle(16, 95, 208, 26));
			Select_mat.setSelectedIndex(0);
			Select_mat.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					JComboBox cb = (JComboBox)e.getSource();
			        codec.setG(matrices[cb.getSelectedIndex()]);
			        if(!Txt_Decode_ASCII.getText().equalsIgnoreCase("")){
			        	Btn_Decoder.doClick();
			        }
			        	
			       // for(int i =0; i<G.length; i++)
			        //	System.out.println(codec.getG()[i]);
				}
			});
			
			
		}
		return Select_mat;
	}

	/**
	 * This method initializes Btn_Recevoir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Recevoir() {
		if (Btn_Recevoir == null) {
			Btn_Recevoir = new JButton();
			Btn_Recevoir.setBounds(new Rectangle(365, 95, 115, 32));
			Btn_Recevoir.setText("RECEPTION");
			Btn_Recevoir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					modeEve=new ModeEvenement(strPorts[getSelect_port().getSelectedIndex()], getTxt_SeqRecue());
					modeEve.start();
					//while(modeEve.isRunning()){}
					//System.out.println(modeEve.data);
				}
			});
		}
		return Btn_Recevoir;
	}

	/**
	 * This method initializes Btn_Reset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Reset() {
		if (Btn_Reset == null) {
			Btn_Reset = new JButton();
			Btn_Reset.setBounds(new Rectangle(475, 95, 97, 32));
			Btn_Reset.setText("RESET");
			Btn_Reset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					Txt_SeqRecue.setText("");
					Txt_info.setText("");
					Txt_Info_ASCII.setText("");
					Txt_Decode_ASCII.setText("");
					Select_mat.setSelectedIndex(0);
				}
			});
		}
		return Btn_Reset;
	}

	/**
	 * This method initializes Btn_Decoder	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Decoder() {
		if (Btn_Decoder == null) {
			Btn_Decoder = new JButton();
			Btn_Decoder.setBounds(new Rectangle(263, 95, 106, 32));
			Btn_Decoder.setText("DECODER");
			Btn_Decoder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					if(Txt_SeqRecue.getText().equalsIgnoreCase(""))
						System.out.println("vide");
					else{
						String [] strTmp = Txt_SeqRecue.getText().split(" ");
						seqRecue = new Binary[strTmp.length];
						for(int i=0; i<seqRecue.length; i++){
							seqRecue[i] = new Binary(strTmp[i]);
						}
						infoDecode = codec.decoder(seqRecue);
						String chaine="";
						for(int i=0;i<infoDecode.length;i++)
							chaine+=infoDecode[i].toString(7)+" ";
						
						Txt_Decode_ASCII.setText(chaine);
						
						chaine = chaine.replaceAll(" ", "");
						byte[] tmp = new byte[(chaine.length()/7)];
						String tmpStr="";
						int cpt=0;
						for(int i=0; i<tmp.length; i++){
							tmpStr=chaine.substring(cpt, cpt+7);
							cpt+=7;
							tmp[i]=(byte)Integer.parseInt(tmpStr, 2);
						}
						Txt_info.setText(codec.asciiToString(tmp));
					}
				}
			});
		}
		return Btn_Decoder;
	}

	/**
	 * This method initializes Txt_Info_ASCII	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTextArea getTxt_Info_ASCII() {
		if (Txt_Info_ASCII == null) {
			Txt_Info_ASCII = new JTextArea();
			Txt_Info_ASCII.setBounds(new Rectangle(16, 140, 555, 66));
			Txt_Info_ASCII.setEditable(false);
			Txt_Info_ASCII.setLineWrap(true);
		}
		return Txt_Info_ASCII;
	}

	/**
	 * This method initializes Txt_Decode_ASCII	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTextArea getTxt_Decode_ASCII() {
		if (Txt_Decode_ASCII == null) {
			Txt_Decode_ASCII = new JTextArea();
			Txt_Decode_ASCII.setBounds(new Rectangle(16, 228, 555, 66));
			Txt_Decode_ASCII.setEditable(false);
			Txt_Decode_ASCII.setLineWrap(true);
		}
		return Txt_Decode_ASCII;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBounds(new Rectangle(16, 350, 555, 10));
		}
		return jProgressBar;
	}

	/**
	 * This method initializes Select_port	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSelect_port() {
		if (Select_port == null) {
			Select_port = new JComboBox(strPorts);
			Select_port.setBounds(new Rectangle(425, 4, 147, 23));
		}
		return Select_port;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
