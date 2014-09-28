package network;

/*
 * @(#)SimpleWrite.java	1.12 98/06/25 SMI
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */
import gnu.io.*;
import gui.BriefInfo;
import gui.Info;

import java.io.*;
import java.util.*;


/**
 * Class declaration
 *
 *
 * @author
 * @version 1.10, 08/04/00
 */
public class SimpleWrite {
    static Enumeration	      portList;
    static CommPortIdentifier portId;
    static String	      messageString = "Hello, world!";
    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static boolean	      outputBufferEmptyFlag = false;
    /**
     * Method declaration
     *
     *
     * @param args
     *
     * @see
     */
	public void write(String data, String port){
		boolean portFound = false;
		
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
		    portId = (CommPortIdentifier) portList.nextElement();

		    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

			if (portId.getName().equals(port)) {
				BriefInfo info = new BriefInfo(null,"Found port " + port);
				info.setVisible(true);
			   // System.out.println("Found port " + port);

			    portFound = true;

			    try {
				serialPort = 
				    (SerialPort) portId.open("Emetteur", 2000);
			    } catch (PortInUseException e) {
			    	info.dispose();
			    	(new Info(null,"Port en cours d'utilisation")).setVisible(true);

				continue;
			    } 

			    try {
				outputStream = serialPort.getOutputStream();
			    } catch (IOException e) {}

			    try {
				serialPort.setSerialPortParams(9600, 
							       SerialPort.DATABITS_8, 
							       SerialPort.STOPBITS_1, 
							       SerialPort.PARITY_EVEN);
			    } catch (UnsupportedCommOperationException e) {
			    	info.dispose();
			    	(new Info(null,"Opération non permis sur le port")).setVisible(true);

			    }
		

			    try {
			    	serialPort.notifyOnOutputEmpty(true);
			    } catch (Exception e) {
			    	info.dispose();
			    	(new Info(null,"Paramètres de notification incorrecte")).setVisible(true);

				//System.out.println("Error setting event notification");
				//System.out.println(e.toString());
				//System.exit(-1);
			    }
			    
			    
			   /* System.out.println(
			    	"Writing \""+data+"\" to "
				+serialPort.getName());*/

			    try {
				outputStream.write(data.getBytes());
				info.dispose();
				(new Info(null,"Données correctement envoyées")).setVisible(true);
			    } catch (IOException e) {
			    	(new Info(null,"Erreur d'écriture sur le port")).setVisible(true);
			    }

			    try {
			       Thread.sleep(2000);  // Be sure data is xferred before closing
			    } catch (Exception e) {}
			    serialPort.close();
			   // System.exit(1);
			} 
		    } 
		} 

		if (!portFound) {
			(new Info(null,"Le port "+port+" n'a pas été trouvé")).setVisible(true);
		    //System.out.println("port " + port + " not found.");
		} 
	} 
}