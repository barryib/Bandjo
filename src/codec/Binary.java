package codec;

/**
 * @author N'DIAYE Ouseynou & BARRY Ibrahima
 * Cette class défnit un objet binaire. Soit un tableau de 0 et 1.
 * La class est une amélioration d'une class du même nom issue
 * de http://www.codes-sources.com
 */

public class Binary {
	
	/*
	 * _value: 	est un tableau de boolean représentant 1 ou 0.
	 * poids:	est le nombre de bits à 1 dans le tableau _value.
	 */
	private boolean[] _value;
	private int poids;

	public Binary(char[] val) {
		_value = new boolean[val.length];
		poids = 0;
		for (int index = 0; index != val.length; ++index) {
			int tmp = -1;
			try {
				tmp = Integer.parseInt(new String(""+val[index]));
			} catch (Exception e) {
				throw new IllegalArgumentException("ERROR : This is not a binary String!");
			}
			switch (tmp) {
			case 0 :
				_value[index] = false;
				break;
			case 1:
				_value[index] = true;
				poids++;
				break;
			default:
				throw new IllegalArgumentException("ERROR : This is not a binary String!");
			}
		}
	}

	public Binary(boolean[] value) {
		_value = value.clone();
	}

	public Binary(String val) {
		this(val.toCharArray());
	}

	public Binary(int val) {
		this(Integer.toBinaryString(val));
	}

	public Binary() {
		this(0);
	}
	
	/*
	 * Définition des méthodes
	 */
	
	public boolean[] toBooleanArray() {
		return _value;
	}
	public boolean toBooleanAt(int index) {
		return index<_value.length ? _value[index]:false;
	}
	public void setBooleanAt(int index, boolean val) {
		_value[index] = val;
	}
	public char[] toCharArray() {
		char[] res = new char[_value.length];
		for ( int index = 0; index != _value.length; ++index)
			if (_value[index])
				res[index] = '1';
			else
				res[index] = '0';
		return res;
	}
	public char[] toCharArray(int nbBits) {
		char[] res = new char[nbBits];
		if(nbBits > _value.length){
			int cpt;
			for ( cpt = 0; cpt < (nbBits-_value.length); cpt++)
					res[cpt] = '0';
			
			for ( int index=0; index < _value.length; index++)
				res[cpt++] = _value[index] ? '1':'0';
		}
		else	res = this.toCharArray();
		
		return res;
	}

	public String toString() {
		return new String(this.toCharArray());
	}
	public String toString(int nbBits) {
		return new String(this.toCharArray(nbBits));
	}

	public int toInteger() {
		int res = 0;
		for (int index = 0; index != this.length(); ++index)
			if (_value[index])
				res += Math.pow(2, this.length() - index -1);
		return res;
	}
	public int toInteger(int index) {
		/*int res = 0;
		if (_value[index])
			res = (int) Math.pow(2, this.length() - index -1);*/
		return Binary.booleanToInt(this.toBooleanAt(index));
	}

	public int length() {
		return _value.length;
	}
	
	public int getPoids() {
		return poids;
	}
	
	public int distance(Binary binary){
		//return Math.abs(this.poids - binary.poids);
		return this.toInteger()^binary.toInteger();
	}

	public void setLength(int len) {
		boolean[] tmp = new boolean[len];
		for (int index = 0; index != len - this.length(); ++index)
			tmp[index] = false;
		for (int index = len - this.length(); index != len; ++index)
			tmp[index] = _value[index - (len - this.length())];
		_value = tmp;
	}

	public Binary and(Binary bin) {
		int size = Math.max(this.length(), bin.length());
		this.setLength(size);
		bin.setLength(size);
		boolean[] res = new boolean[size];
		for (int index = 0; index != size; ++index) {
			res[index] = this.toBooleanArray()[index] && bin.toBooleanArray()[index];
		}
		return new Binary(res);
	}

	public Binary or(Binary bin) {
		int size = Math.max(this.length(), bin.length());
		this.setLength(size);
		bin.setLength(size);
		boolean[] res = new boolean[size];
		for (int index = 0; index != size; ++index) {
			res[index] = this.toBooleanArray()[index] || bin.toBooleanArray()[index];
		}
		return new Binary(res);
	}

	public Binary xor(Binary bin) {
		int size = Math.max(this.length(), bin.length());
		this.setLength(size);
		bin.setLength(size);
		boolean[] res = new boolean[size];
		for (int index = 0; index != size; ++index) {
			res[index] = this.toBooleanArray()[index] ^ bin.toBooleanArray()[index];
		}
		return new Binary(res);
	}

	public Binary not() {
		boolean[] res = new boolean[this.length()];
		for (int index = 0; index != this.length(); ++index) {
			res[index] = ! _value[index];
		}
		return new Binary(res);
		
	}
	//decalage à droite
	public void rRigth(boolean val, int distance){
		boolean[] tmp = this._value;
		this._value = new boolean[tmp.length];
		for (int index = 0; index < distance; index++){
			this._value[index] = val; 
		}
		for (int index = distance; index < this.length(); index++){
			this._value[index] = tmp[index-distance]; 
		}
	}
	public void rRigth(){
		this.rRigth(false, 1);
	}
	//décalage à gauche
	/*public void rLeft(boolean val, int distance){
		boolean[] tmp = this._value;
		this._value = new boolean[tmp.length];
		for (int index = this.length(); index != distance; ++index){
			this._value[index] = val; 
		}
		for (int index = distance+1; index != this.length(); ++index){
			this._value[index] = tmp[index]; 
		}
	}
	public void rLeft(){
		this.rLeft(false, 1);
	}
	*/
	public void concat(Binary binary){
		boolean[] tmp = new boolean[this.length() + binary.length()];
		int cpt=0;
		for(int index=0; index<this.length(); index++)
			tmp[cpt++] = this._value[index];
		
		for(int index=0; index<binary.length(); index++)
			tmp[cpt++] = binary._value[index];
		
		this._value = tmp;
	}
	/* Méthodes statiques */

	static public int booleanToInt(boolean val){
		return val ? 1:0;
	}
	static public Binary[] byteToBinaryArray(byte[] val){
		Binary[] binaryArray = new Binary[val.length];
		for(int index=0; index<val.length; index++)
			binaryArray[index] = new Binary(val[index]);
		
		return binaryArray;
	}
	static public String binaryArrayToString(Binary[] val){
		String tmp="";
		Binary b;
		for(int index=0; index<val.length; index++){

		    b = new Binary(val[index].toString(7));
			
			tmp+=b.toString();
		}
		
		return tmp;
	}
}
