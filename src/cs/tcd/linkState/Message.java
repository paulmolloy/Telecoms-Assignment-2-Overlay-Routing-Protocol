package cs.tcd.linkState;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

public class Message {


	/**
	 * Message is used to construct and deconstruct the packets that are sent to and from
	 * each router. They possess getter and setter methods.
	 */

	public static final int MESSAGE_CODE = 0;
	public static final int FILE_CODE = 2;
	private String message; //message is in form [senderUserName], [destUserName], [messageString if MESSAGE_CODE
							//or fileName String if FILE_CODE] 
	private int packetType;
	private byte[] fData;
	private String[] information; 

	public Message() {
		this.message = "";
		this.packetType = MESSAGE_CODE;
	}

	public Message(String message) {
		this.message = message;
		information = message.split(",");
		this.packetType = MESSAGE_CODE;
	}
	
	public Message(String message, byte[] aFile){
		this.message = message;
		this.fData = aFile;
		this.packetType= FILE_CODE;
		information = message.split(",");
	}


	/**Message constructor that turns a DatagramPacket made from a Message into a Message
	 * @param packet
	 */
	public Message (DatagramPacket packet) {

		try {

			byte[] data;
			ByteArrayInputStream bin;
			ObjectInputStream oin;

			data= packet.getData();  // use packet content as seed for stream
			bin= new ByteArrayInputStream(data);
			oin= new ObjectInputStream(bin);

			this.packetType = oin.readInt();  // read type from beginning of packet

			switch(this.packetType) {   // depending on type create content object 
				case MESSAGE_CODE:

					this.message = oin.readUTF();
					break;
					
				case FILE_CODE:
					this.message = oin.readUTF();
					this.fData = (byte[]) oin.readObject();
					
					
					break;

				default:
					this.message  = null;

					break;
			}
			information = message.split(",");
			oin.close();
			bin.close();

		}
		catch(Exception e) {e.printStackTrace();}

	}


	/**Converts a Message into a DatagramPacket to be sent
	 * @return DatagramPacket
	 */
	public DatagramPacket toDatagramPacket() {
		DatagramPacket packet= null;

		try {
			ByteArrayOutputStream bout;
			ObjectOutputStream oout;
			byte[] data;
			bout= new ByteArrayOutputStream();
			oout= new ObjectOutputStream(bout);
			
			switch(this.packetType) {   // depending on type create content object 
				case MESSAGE_CODE:
					oout.writeInt(MESSAGE_CODE);
					oout.writeUTF(this.message);
					break;
				case FILE_CODE:
					oout.writeInt(FILE_CODE);
					oout.writeUTF(this.message);
					oout.writeObject(this.fData);
					break;
				default:
					this.packetType = -1; //invalid
					break;
			}
			oout.flush();
			data= bout.toByteArray(); // convert content to byte array

			packet= new DatagramPacket(data, data.length); // create packet from byte array
			oout.close();
			bout.close();
		}
		catch(Exception e) {e.printStackTrace();}

		return packet;
	}

	public String toString(){
		return this.message;
	}
	
	/*
	 * Getters
	 */

	public String getUserFrom() {
		return information[0];
	}

	public String getUserTo() {
		return information[1];
	}

	public String getMessage() {
		return information[2];
	}

	public byte[] getBytes() {
		return message.getBytes();
	}
	public int getType(){
		return this.packetType;
	}
	public byte[] getFileBytes(){
		return this.fData;
	}
	
	/*
	 * Setters
	 */

	public void setUserFrom(String string) {
		message += string;
	}

	public void setUserTo(String string) {
		message += string;
	}

	public void setMessage(String string) {
		message += string;
	}
}