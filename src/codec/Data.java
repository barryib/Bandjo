package codec;

import java.io.Serializable;

public class Data implements Serializable{
	private Binary[] infoCode;
	private byte[] infoASCII;
	
	public Data(Binary[] infoCode, byte[] infoASCII){
		this.infoCode = infoCode;
		this.infoASCII = infoASCII;
	}
	
	
	public byte[] getInfoASCII() {
		return infoASCII;
	}


	public void setInfoASCII(byte[] infoASCII) {
		this.infoASCII = infoASCII;
	}


	public Binary[] getInfoCode() {
		return infoCode;
	}

	public void setInfoCode(Binary[] infoCode) {
		this.infoCode = infoCode;
	}
	
	
}
