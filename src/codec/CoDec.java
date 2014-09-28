package codec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;


/**
 * @author N'DIAYE Ouseynou & BARRY Ibrahima
 * Dans cette class, nous definissons le codeur convolutionnel et le decodeur de viterbi.
 * 
 * Bugs connus � ce jour:
 *  - D�codage du "?": Le d�codeur rajoute un 0 � la fin de la s�quence d�cod�e.
 *  				   Le "?" est cod� sur 6 bits et non 7.
 *  
 *  - D�codage du "?": R�solu par l'ajout de 0 � tous les �l�ments cod�s sur moins
 *  				   de 7 bits (ASCII) dans le codeur (avant codage convolutif).
 *                   :::: Le Samedi 11 Avril 2009 � 01h:00mn
 */
public class CoDec {
	
	/* **************************************************************************** *
	 * k: 		est le nombre de bits en entr�e du codeur.							*
	 * m: 		est l'ordre du codeur. Par d�faut, il vaut 3.						*
	 * n: 		est le nombre de bits en sortie du codeur ou nombre des matrices g.	*
	 * nbBits: 	nombre de bits pour chaque �l�ment (ASCII est cod� sur 7 bits).		*
	 * G:		est un tablau des matrices de convolution.							*
	 * 			Par d�faut (111; 101) = (7; 5)										*
	 * **************************************************************************** */
	
	private int k; 
	private int m; 
	private int n; 
	private int nbBits = 7; 
	private Binary[] G = {new Binary(7), new Binary(5)}; 
	private Binary registre;
	
	public CoDec(){
		k = 1;
		n = G.length;
		m = 3;
		registre = this.init(m); // initialisation du registre
	}
	public CoDec(int k, int n, int m, Binary[] mat){
		//if((k*(m-1)) > 127) k=Math.round(127/(m-1));
		this.k = k;
		this.m = m;
		this.G = mat;
		this.n = this.G.length;
		this.registre = this.init(m); // initialisation du registre
	}
	
	/* *********************************** *
	 * 		  Getters and Setters
	 * *********************************** */
	
	public void setG(Binary[] g) {
		G = g;
	}
	public Binary[] getG() {
		return G;
	}
	public int getK() {
		return k;
	}
	public int getM() {
		return m;
	}
	public int getN() {
		return n;
	}
	
	/* *********************************** *
	 * D�finition des m�thodes de la class *
	 * *********************************** */
	
	public byte[] stringToASCII(String chaine) throws UnsupportedEncodingException{
		
		return chaine.getBytes("ASCII");
	}
	
	public String asciiToString(byte[] bits){
	
		return new String(bits);
	}
	
	public Binary init(int resolution){
		boolean[] tmp = new boolean[resolution];
		for(int index=0; index<resolution; index++)
			tmp[index]=false;
		
		return new Binary(tmp);
	}
	
	public Binary convoluer(boolean bit){
		int c = 0, code=0;
		registre.rRigth(bit, 1);
		for(int j=0; j<n; j++){
			c=0;
			for(int i=0; i<m; i++){
				c ^= registre.toBooleanAt(i) ? G[j].toInteger(i):0;
			}
			code += (c<<(n-j-1));
		}
		//System.out.println("--code "+Integer.toBinaryString(code));
		return new Binary(code);
	}
	/*
	 * Codeur convolutionel
	 */
	public Binary[] coder(Binary[] bitsInfo) {
		/*int taille = 0;
		for(int i=0; i<bitsInfo.length; i++)
			taille+=bitsInfo[i].length();
		int cptCode=0;
		*/
		Vector<Binary> bitsCode = new Vector<Binary>();
		registre = init(m);
		for(int cpt=0; cpt<bitsInfo.length; cpt++){
			
			if(bitsInfo[cpt].length()<nbBits)
				bitsInfo[cpt] = new Binary(bitsInfo[cpt].toString(nbBits));
			
			for(int cptBit=0; cptBit < bitsInfo[cpt].length(); cptBit++){
				bitsCode.add(convoluer(bitsInfo[cpt].toBooleanAt(cptBit)));
				//System.out.println("--bit "+bitsCode[cptCode-1].toString(2));
			}
		}
		return bitsCode.toArray(new Binary[bitsCode.size()]);
	}
	/*
	 * D�codeur de Viterbi
	 */
	public Binary [] decoder(Binary[] seqRecue){
		ArrayList<Noeud>[] instants = new ArrayList[seqRecue.length];
		Binary contenu = new Binary();
		Noeud un, zero;
	
		// on r�initialise les registres � 00...0
		registre = this.init(m);
		contenu = this.init(n);
		// cr�ation de la racine soit l'�tat zero
		Noeud racine = new Noeud(null, this.registre, contenu, false);
		// cr�ation des deux fils de la racine
		instants[0] = new ArrayList<Noeud>();
		
		this.registre = new Binary(racine.getEtatReg().toBooleanArray());
		contenu=this.convoluer(false);
		zero   = new Noeud(racine, new Binary(this.registre.toBooleanArray()), contenu, false);
		zero.addPoids(contenu.distance(seqRecue[0]));
		
		this.registre = new Binary(racine.getEtatReg().toBooleanArray());
		contenu=this.convoluer(true);
		un   = new Noeud(racine, new Binary(this.registre.toBooleanArray()), contenu, true);
		un.addPoids(contenu.distance(seqRecue[0]));
		
		instants[0].add(zero);
		instants[0].add(un);
		
		// cr�ation des fils pour les prochains noeuds
		Iterator<Noeud> it;
		for(int cpt=1; cpt<seqRecue.length; cpt++){
			instants[cpt] = new ArrayList<Noeud>();
			
			it=instants[cpt-1].iterator();
			
			while(it.hasNext()){
				Noeud pere = it.next();
				
				this.registre = new Binary(pere.getEtatReg().toBooleanArray());
				contenu=this.convoluer(false);
				zero   = new Noeud(pere, new Binary(this.registre.toBooleanArray()), contenu, false);
				zero.addPoids(contenu.distance(seqRecue[cpt]));
				
				this.registre = new Binary(pere.getEtatReg().toBooleanArray());
				contenu=this.convoluer(true);
				un   = new Noeud(pere, new Binary(this.registre.toBooleanArray()), contenu, true);
				un.addPoids(contenu.distance(seqRecue[cpt]));

				instants[cpt].add(zero);
				instants[cpt].add(un);			
			}
			
			/*
			 * Nous trions la list � chaque instant afin d'�liminer les Noeuds
			 * de poids �lev�s (distance Haming).
			 * Si des Noeuds sont consid�r�s comme ambigue, ils ne seront pas supprim�s afin 
			 * de d�cider ult�rieurment.
			 */
			Collections.sort(instants[cpt]);
			
			// il existe une ambiguit�
			
			if(instants[cpt].get(0).isAmbigue())
				// si ambigue, on supprime tous ceux qui ne le sont pas.
				// car seul les Noeuds du haut de la list sont de poids faible 
				for(int i=1; i<instants[cpt].size(); ){
					Noeud tmp = instants[cpt].get(i);
					if(!tmp.isAmbigue())
						instants[cpt].remove(i);
					else i++;
					//System.out.println("--" +(tmp.getArrete()?'1':'0')+"--"+tmp.getPoids());
				}
			else
				// si aucune ambiguit�, on supprime tous les Noeuds de poids �lev�s.
				for(int i=1; i<instants[cpt].size(); ){
					instants[cpt].remove(i);
				}
		}
		//Collections.sort(instants[seqRecue.length-1]);
		Noeud tmp = instants[seqRecue.length-1].get(0);
		//if(tmp.isAmbigue()) System.out.println("matrice ambigue");
	
		boolean[] cheminSurvivant = new boolean[seqRecue.length];
		for(int i=seqRecue.length-1; i>=0; i--){
			//System.out.println(" temp "+i+" - "+tmp.getContenu()+" reg "+(tmp.getEtatReg())+" poids "+tmp.getPoids() +" seqRecue "+seqRecue[i]);
			cheminSurvivant[i] = tmp.getArrete();
			tmp = tmp.getPere();
		}
		Vector<Binary> infoDecode = new Vector<Binary>();
		String chaine = "";
		for(int i=0; i<cheminSurvivant.length; i++){
			chaine += cheminSurvivant[i] ? "1":"0";
			if((i!=0) && ((i+1)%7==0)){
				infoDecode.add(new Binary(chaine));
				chaine="";
			}
		}
				
		return infoDecode.toArray(new Binary[infoDecode.size()]);
	}

}
