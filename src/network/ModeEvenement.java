
package network;

import java.io.*;
import java.util.TooManyListenersException;

import javax.swing.JTextArea;

import gnu.io.*;
import gui.Info;

public class ModeEvenement extends Thread implements SerialPortEventListener {

	private CommPortIdentifier portId;
	private SerialPort serialPort;
	private BufferedReader fluxLecture=null;
	private InputStream inputStream=null;
	private boolean running;
	private JTextArea champText;
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public String data = ""; 

	/**
	 * Constructeur qui récupère l'identifiant du port et lance l'ouverture.
	 */
	public ModeEvenement(String port, JTextArea jText) {
		champText = jText;
	
		//récupération de l'identifiant du port
		try {
			portId = CommPortIdentifier.getPortIdentifier(port);
		} catch (NoSuchPortException e) {
			(new Info(null,"Le port "+port+" n'a pas été trouvé")).setVisible(true);
		}
		
		//ouverture du port
		try {
			serialPort = (SerialPort) portId.open("ModeEvenement", 2000);
		} catch (PortInUseException e) {
			(new Info(null,"Le port "+port+" est en cours d'utilisation")).setVisible(true);
		}
		try {
		    inputStream = serialPort.getInputStream();
		} catch (IOException e) {
			(new Info(null,"Impossible d'écrire sur le port "+port)).setVisible(true);
		}
		//récupération du flux
		/*try {
			fluxLecture =
				new BufferedReader(
					new InputStreamReader(serialPort.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//ajout du listener
		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			//e.printStackTrace();
		}
		//paramétrage du port
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.setSerialPortParams(9600, 
				       SerialPort.DATABITS_8, 
				       SerialPort.STOPBITS_1, 
				       SerialPort.PARITY_EVEN);
		} catch (UnsupportedCommOperationException e) {
			//e.printStackTrace();
			(new Info(null,"Opération non supportée sur "+port)).setVisible(true);
		}
		(new Info(null,"Port ouvert, attente de lecture")).setVisible(true);
		//System.out.println("port ouvert, attente de lecture");
	}
	public void run() {
		running = true;
		while (running) {
			try {
				data="";
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//fermeture du flux et port
		data="";
		try {
			if(fluxLecture!=null)
				fluxLecture.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serialPort.close();
	}
	/**
	 * Méthode de gestion des événements.
	 */
	public void serialEvent(SerialPortEvent event) {
		//gestion des événements sur le port :
		//on ne fait rien sauf quand les données sont disponibles
		switch (event.getEventType()) {
			case SerialPortEvent.BI :
			case SerialPortEvent.OE :
			case SerialPortEvent.FE :
			case SerialPortEvent.PE :
			case SerialPortEvent.CD :
			case SerialPortEvent.CTS :
			case SerialPortEvent.DSR :
			case SerialPortEvent.RI :
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
				break;
			case SerialPortEvent.DATA_AVAILABLE :
				try {
					byte[] readBuffer = new byte[inputStream.available()];
					while (inputStream.available() > 0) {
					    int numBytes = inputStream.read(readBuffer);
					}
					//System.out.println("leng "+readBuffer.length );
				
					data+=new String(readBuffer);	
					champText.setText(data);
						//System.out.println(data);
					//stopThread();
				} catch (IOException e) {
					//e.printStackTrace();
				}
				break;
		}
	}
	/**
	 * Permet l'arrêt du thread
	 */
	public void stopThread() {
		running = false;
	}
	/**
	 * Méthode principale de l'exemple.
	 */
	/*public static void main(String[] args) {
		//Récuperation du port en argument
		String port = "COM3";
		//lancement de l'appli
		ModeEvenement modeEve=new ModeEvenement(port);
		modeEve.start();
		//"interface utilisateur"
		System.out.println("taper q pour quitter");
		//construction flux lecture
		BufferedReader clavier =
			new BufferedReader(new InputStreamReader(System.in));
		//lecture sur le flux entrée.
		try {
			String lu = clavier.readLine();
			while (!lu.equals("q")) {
			}
		} catch (IOException e) {
		}
		modeEve.stopThread();
	}*/
}