package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import codec.Binary;

public class Canal {
	private Binary noise;
	private int[] indexBruit;
	public ObjectInputStream in;
	public ObjectOutputStream out;
	public Socket s;
	
	public Canal(){
		noise = null;
	}
	
	public Canal(Socket sock) throws IOException{
		noise = null;
		s=sock;
		in=new ObjectInputStream(s.getInputStream());
	 	out=new ObjectOutputStream(s.getOutputStream());
	}
	
	/*
	 * Getters and Setters
	 */

	public Binary getNoise() {
		return noise;
	}

	public void setNoise(Binary noise) {
		this.noise = noise;
	}
	
	
	/*
	 * Définition des méthodes
	 */
	
	public int[] getIndexBruit() {
		return indexBruit;
	}

	public Binary addNoise(Binary signal, int pourcentage){
		int tailleBruit = Math.round(((signal.length()*pourcentage)/100)+1);
		indexBruit = new int[tailleBruit];
		for(int i=0; i<tailleBruit; i++){
			indexBruit[i] = (int)Math.round(Math.random()*tailleBruit);
			signal.setBooleanAt(indexBruit[i], !signal.toBooleanAt(indexBruit[i]));
		}
		return signal;
	}
	
}
