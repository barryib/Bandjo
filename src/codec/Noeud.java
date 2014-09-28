package codec;

/**
 * @author N'DIAYE Ouseynou & BARRY Ibrahima
 * Cette class d�finit les differents �tats et comportement d'un noeud.
 */

public class Noeud implements java.lang.Comparable{
	
	/*
	 * pere: 	est le noeud parant
	 * etatReg: est l'�tat du registre � la cr�ation du noeud
	 * contenu:	est le contenu du Noeud � sa cr�ation
	 * arrete:	est la branche par la quelle on � acced� au Noeud (soit 1 ou 0)
	 * ambigue:	est � 1 (true) si il existe un Noeud poss�dant le m�me poids au m�me instant
	 */
	
	private Noeud pere;
	private Binary etatReg, contenu;
	private boolean arrete;
	private int poids;
	private boolean ambigue; 
	
	public Noeud(Noeud pere, Binary reg, Binary contenu, boolean val){
		this.pere 	 = pere;
		this.etatReg = reg;
		this.contenu = contenu;
		this.arrete  = val;
		this.poids 	 = pere==null ? 0:pere.poids;	
		this.ambigue = false;
	}
	
	public Noeud(){
		this(null, null, null, false);
	}
	
	/*
	 * d�finition des getters et setters
	 */
	
	public Binary getContenu() {
		return contenu;
	}

	public void setContenu(Binary contenu) {
		this.contenu = contenu;
	}

	public int getPoids() {
		return poids;
	}

	public void addPoids(int poids) {
		this.poids += poids;
	}
	
	/*public void addArrete(boolean val){
		boolean [] tmp = new boolean[this.arrete.length+1];
		int index=0;
		for(index=0; index<this.arrete.length; index++){
			tmp[index] = this.arrete[index];
		}
		tmp[index] = val;
		this.arrete = tmp;
	}*/

	public boolean getArrete() {
		return arrete;
	}

	public Binary getEtatReg() {
		return etatReg;
	}

	public Noeud getPere() {
		return pere;
	}
	

	public boolean isAmbigue() {
		return ambigue;
	}
	/*
	 * M�thode pour comparer deux objet de type Noeud
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object obj) {
		int nombre1 = ((Noeud) obj).getPoids(); 
	    int nombre2 = this.getPoids(); 
	    
	    if 		(nombre1 >  nombre2) return -1; 
	    else if (nombre1 == nombre2){
	    	this.ambigue = true;
	    	((Noeud)obj).ambigue = true;
	    	return  0; 
	    }
	    else 						 return  1; 

	}
}
