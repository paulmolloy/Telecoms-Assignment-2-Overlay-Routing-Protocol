package cs.tcd.ie;
	
public class Message {
	
	
	/*
	 * Message is used to construct and deconstruct the packets that are sent to and from
	 * each router. They possess getter and setter methods.
	 */
	
	private String message;
	private String[] information;
	
	public Message(String message) {
		this.message = message;
		information = message.split(",");
	}
	
	public String getRouterFrom() {
		return information[0];
	}
	
	public Double getDistance() {
		return Double.parseDouble(information[1]);
	}
	
	public String getForwardedRouter() {
		return information[2];
	}
	
	public Double getDistanceFromRouterToForwardedRouter() {
		return Double.parseDouble(information[3]);
	}
	
	public String getUsersOfRouter() {
		return information[4];
	}

	public byte[] getBytes() {
		return message.getBytes();
	}
	
}