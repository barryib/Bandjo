package gui;

import codec.Binary;
import codec.CoDec;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Frame;
import javax.swing.JDialog;
import java.awt.Rectangle;

import javax.swing.JTextArea;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.JSlider;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;

import network.Canal;
import javax.swing.JTextPane;

public class Simulateur extends JDialog {

	private static final long serialVersionUID = 1L;
	/*
	 * Déclaration du codec
	 */
	
	private CoDec codec = new CoDec();  //  @jve:decl-index=0:
	private byte[] infoASCII = null; 
	private Binary[] infoCode= null, infoDecode=null;
	String[] strMatrices = { "Matrices ["+(new Binary(7)).toString(codec.getM())+"] , ["+ (new Binary(5)).toString(codec.getM())+"]",
							 "Matrices ["+(new Binary(7)).toString(codec.getM())+"] , ["+ (new Binary(7)).toString(codec.getM())+"]",
							 "Matrices ["+(new Binary(5)).toString(codec.getM())+"] , ["+ (new Binary(7)).toString(codec.getM())+"]" };

	Binary[][] matrices  = { {(new Binary(7)) ,(new Binary(5))},
			 				 {(new Binary(7)) ,(new Binary(7))},
			 				 {(new Binary(5)) ,(new Binary(7))} };
	
	Binary[] G = matrices[0];
	
	/*
	 * Déclaration des éléments de la fenêtre
	 */
	
	private JPanel JSimuPanel = null;

	private JTextArea Txt_info = null;

	private JTextArea Txt_Code = null;

	private JLabel jLabel = null;

	private JComboBox Select_mat = null;

	private JButton Btn_Coder = null;

	private JButton Btn_Reset = null;

	private JButton Btn_Decoder = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextPane Txt_Info_ASCII = null;

	private JLabel jLabel3 = null;

	private JTextPane Txt_Decode = null;

	private JSlider Slider_Noise = null;

	private JLabel jLabel_Noise = null;

	private JLabel jLabel6 = null;

	private JTextPane Txt_InfoDecode = null;

	private JLabel jLabel4 = null;

	private JButton Btn_Bruiter = null;

	private JLabel Label_Bruit = null;
	
	/**
	 * @param owner
	 */
	public Simulateur(Frame owner) {
		super(owner);
		initialize();
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(587, 512);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("BAN'DJO | Mode simulateur");
		this.setContentPane(getJSimuPanel());
		//getContentPane().add(scrollPane, "Center");
		this.setResizable(false);
	}

	/**
	 * This method initializes JSimuPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJSimuPanel() {
		if (JSimuPanel == null) {
			
			//scrollPane = new JScrollPane(getTxt_info());
		   // scrollPane.validate();
			Label_Bruit = new JLabel();
			Label_Bruit.setBounds(new Rectangle(374, 288, 97, 19));
			Label_Bruit.setText("0 % de bruit");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(16, 383, 113, 19));
			jLabel4.setText("Texte décodé");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(442, 105, 113, 19));
			jLabel6.setText("de bruit alléatoire");
			jLabel_Noise = new JLabel();
			jLabel_Noise.setBounds(new Rectangle(411, 105, 25, 19));
			jLabel_Noise.setText("50%");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(16, 291, 279, 19));
			jLabel3.setText("Information décodée");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(16, 110, 280, 19));
			jLabel2.setText("Texte codé en ASCII");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 199, 277, 19));
			jLabel1.setText("Information codée (codage convolutionel)");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 14, 485, 19));
			jLabel.setText("Saisir un texte ici...");
			JSimuPanel = new JPanel();
			JSimuPanel.setLayout(null);
			JSimuPanel.add(getTxt_info(), null);
			JSimuPanel.add(getTxt_Code(), null);
			JSimuPanel.add(jLabel, null);
			JSimuPanel.add(getSelect_mat(), null);
			JSimuPanel.add(getBtn_Coder(), null);
			JSimuPanel.add(getBtn_Reset(), null);
			JSimuPanel.add(getBtn_Decoder(), null);
			JSimuPanel.add(jLabel1, null);
			JSimuPanel.add(jLabel2, null);
			JSimuPanel.add(getTxt_Info_ASCII(), null);
			JSimuPanel.add(jLabel3, null);
			JSimuPanel.add(getTxt_Decode(), null);
			JSimuPanel.add(getSlider_Noise(), null);
			JSimuPanel.add(jLabel_Noise, null);
			JSimuPanel.add(jLabel6, null);
			//JSimuPanel.add(scrollPane, BorderLayout.CENTER);
			JSimuPanel.add(getTxt_InfoDecode(), null);
			JSimuPanel.add(jLabel4, null);
			JSimuPanel.add(getBtn_Bruiter(), null);
			JSimuPanel.add(Label_Bruit, null);
			
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
			Txt_info = new JTextArea(555,3);
			//scrollPane = new JScrollPane(Txt_info);
			Txt_info.setBounds(new Rectangle(16, 40, 555, 31));
			Txt_info.setLineWrap(true);
			//Txt_info.setSize(200, 30);
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
			Txt_Code.setBounds(new Rectangle(16, 219, 555, 66));
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
		    //Create the combo box, select the item at index 4.
		    //Indices start at 0, so 4 specifies the pig.
		    //petList.setSelectedIndex(4);
		    //petList.addActionListener(this);
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
	 * This method initializes Btn_Coder	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_Coder() {
		if (Btn_Coder == null) {
			Btn_Coder = new JButton();
			Btn_Coder.setBounds(new Rectangle(261, 75, 97, 32));
			Btn_Coder.setText("CODER");
			Btn_Coder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					if(Txt_info.getText().equalsIgnoreCase(""))
						System.out.println("vide");
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
							Slider_Noise.setValue(0);
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
			Btn_Reset.setBounds(new Rectangle(473, 75, 97, 32));
			Btn_Reset.setText("RESET");
			Btn_Reset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					Txt_info.setText("");
					Txt_Info_ASCII.setText("");
					Txt_Code.setText("");
					Txt_Decode.setText("");
					Txt_InfoDecode.setText("");
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
			Btn_Decoder.setBounds(new Rectangle(360, 75, 106, 32));
			Btn_Decoder.setText("DECODER");
			Btn_Decoder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					if(Txt_Code.getText().equalsIgnoreCase(""))
						System.out.println("vide");
					else{
						infoDecode = codec.decoder(infoCode);
						String chaine="";
						for(int i=0;i<infoDecode.length;i++)
							chaine+=infoDecode[i].toString(codec.getN())+" ";
						
						Txt_Decode.setText(chaine);
						
						chaine = chaine.replaceAll(" ", "");
						byte[] tmp = new byte[(chaine.length()/7)];
						String tmpStr="";
						int cpt=0;
						for(int i=0; i<tmp.length; i++){
							tmpStr=chaine.substring(cpt, cpt+7);
							cpt+=7;
							tmp[i]=(byte)Integer.parseInt(tmpStr, 2);
						}
						Txt_InfoDecode.setText(codec.asciiToString(tmp));
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
	private JTextPane getTxt_Info_ASCII() {
		if (Txt_Info_ASCII == null) {
			Txt_Info_ASCII = new JTextPane();
			Txt_Info_ASCII.setBounds(new Rectangle(16, 129, 555, 66));
			Txt_Info_ASCII.setEditable(false);
		//	Txt_Info_ASCII.setLineWrap(true);
		}
		return Txt_Info_ASCII;
	}

	/**
	 * This method initializes Txt_Decode	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTextPane getTxt_Decode() {
		if (Txt_Decode == null) {
			Txt_Decode = new JTextPane();
			Txt_Decode.setBounds(new Rectangle(16, 312, 555, 66));
			Txt_Decode.setEditable(false);
			//Txt_Decode.setLineWrap(true);
		}
		return Txt_Decode;
	}

	/**
	 * This method initializes Slider_Noise	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getSlider_Noise() {
		if (Slider_Noise == null) {
			Slider_Noise = new JSlider();
			Slider_Noise.setBounds(new Rectangle(265, 107, 143, 23));
			Slider_Noise.setMajorTickSpacing(50);
			Slider_Noise.setMinorTickSpacing(5);
			Slider_Noise.setPaintTicks(true);
			Slider_Noise.setMinimum(0);
			Slider_Noise.setMaximum(50);
			Slider_Noise.setValue(0);
			jLabel_Noise.setText(Slider_Noise.getValue()+"%");
			Slider_Noise.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					//System.out.println(Slider_Noise.getValue()); // TODO Auto-generated Event stub stateChanged()
					jLabel_Noise.setText(Slider_Noise.getValue()+"%");
					//Txt_Info_ASCII.setToolTipText("jijijik");
					String chaineInfo = Txt_Code.getText().replaceAll(" ", "");
					Canal canal = new Canal();
					Binary signalBruite = canal.addNoise(new Binary(chaineInfo), Slider_Noise.getValue());
					Vector<Binary> tmp = new Vector<Binary>();
					//System.out.println(signalBruite.toString());
					String chaine="";
					for(int i=0; i<signalBruite.length(); i++){
						chaine += signalBruite.toBooleanAt(i) ? "1":"0";
						if((i!=0) && ((i+1)%codec.getN()==0)){
							tmp.add(new Binary(chaine));
							chaine="";
						}
					}
					infoCode = tmp.toArray(new Binary[tmp.size()]);
					//Txt_Code.setText(signalBruite.toString(codec.getN()));
					chaine="";
					for(int i=0;i<infoCode.length;i++)
						chaine+=infoCode[i].toString(codec.getN())+" ";
					Txt_Code.setText(chaine);
					/*for(int i=0; i<chaine.length(); i++){
						//Txt_Code.append(""+chaine.charAt(i));
						if(chaineInfo.charAt(i)==chaine.charAt(i))
							appendStr(Txt_Code, Color.black, ""+chaine.charAt(i));
						else
							appendStr(Txt_Code, Color.red, ""+chaine.charAt(i));
						
					}*/
					
				}
			});
		}
		return Slider_Noise;
	}
	
	 public void appendNaive( JTextPane jText, Color c, String s) { // naive implementation
		    // bad: instiantiates a new AttributeSet object on each call
		    SimpleAttributeSet aset = new SimpleAttributeSet();
		    StyleConstants.setForeground(aset, c);
		  
		    int len = jText.getText().length();
		    jText.setCaretPosition(len); // place caret at the end (with no selection)
		    jText.setCharacterAttributes(aset, false);
		    jText.replaceSelection(s); // there is no selection, so inserts at caret
		  }

		  public void appendStr(JTextPane jText, Color c, String s) { // better implementation--uses StyleContext
		    StyleContext sc = StyleContext.getDefaultStyleContext();
		    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
		                                        StyleConstants.Foreground, c);
		  
		    int len = jText.getDocument().getLength(); // same value as getText().length();
		    jText.setCaretPosition(len);  // place caret at the end (with no selection)
		    jText.setCharacterAttributes(aset, false);
		    jText.replaceSelection(s); // there is no selection, so inserts at caret
		   // jText.setVisible(true);
		  }

		/**
		 * This method initializes Txt_InfoDecode	
		 * 	
		 * @return javax.swing.JTextPane	
		 */
		private JTextPane getTxt_InfoDecode() {
			if (Txt_InfoDecode == null) {
				Txt_InfoDecode = new JTextPane();
				Txt_InfoDecode.setBounds(new Rectangle(16, 406, 555, 66));
			}
			return Txt_InfoDecode;
		}

		/**
		 * This method initializes Btn_Bruiter	
		 * 	
		 * @return javax.swing.JButton	
		 */
		private JButton getBtn_Bruiter() {
			if (Btn_Bruiter == null) {
				Btn_Bruiter = new JButton();
				Btn_Bruiter.setBounds(new Rectangle(474, 285, 97, 28));
				Btn_Bruiter.setText("BRUITER");
				Btn_Bruiter.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						
						String str = "";
						str = getTxt_Code().getSelectedText();
						//Binary b = new Binary(Integer.reverse(Integer.parseInt(str, 2)));
						String strAff="";
						for(int i=0; i<str.length(); i++){
							if(str.charAt(i)!=' ')
								strAff += (""+(str.charAt(i)=='1' ? '0':'1'));
							else 
								strAff+=" ";
						}
						
						//System.out.println(strAff);
						//getTxt_Code().setSelectedTextColor(Color.red);
						getTxt_Code().replaceSelection(strAff);
						String chaineInfo = Txt_Code.getText().replaceAll(" ", "");
						strAff = strAff.replaceAll(" ", "");
						//str = chaineInfo.replaceAll(" ", "");
						if(str.length()!=0)	
							Label_Bruit.setText(((strAff.length()*100)/chaineInfo.length())+"% de bruit");
						Canal canal = new Canal();
						Binary signalBruite = canal.addNoise(new Binary(chaineInfo), Slider_Noise.getValue());
						Vector<Binary> tmp = new Vector<Binary>();
						//System.out.println(signalBruite.toString());
						String chaine="";
						for(int i=0; i<signalBruite.length(); i++){
							chaine += signalBruite.toBooleanAt(i) ? "1":"0";
							if((i!=0) && ((i+1)%codec.getN()==0)){
								tmp.add(new Binary(chaine));
								chaine="";
							}
						}
						infoCode = tmp.toArray(new Binary[tmp.size()]);
	                	
					}
				});
			}
			return Btn_Bruiter;
		}

}  //  @jve:decl-index=0:visual-constraint="10,10"
