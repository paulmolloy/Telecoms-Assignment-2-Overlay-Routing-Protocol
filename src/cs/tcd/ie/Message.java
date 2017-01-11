package cs.tcd.ie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;

public class Message {
	
	
	/**
	 * Message is used to construct and deconstruct the packets that are sent to and from
	 * each router. They possess getter and setter methods.
	 */
	
	public static final int MESSAGE_CODE = 0;
	private String message;
	private String[] information;
	
	public Message() {
		this.message = "";
	}
	
	public Message(String message) {
		this.message = message;
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
			
			int packetType = oin.readInt();  // read type from beginning of packet

			switch(packetType) {   // depending on type create content object 
			case MESSAGE_CODE:

				this.message = oin.readUTF();
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
			
			oout.writeInt(MESSAGE_CODE);
			oout.writeUTF(this.message);
			
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