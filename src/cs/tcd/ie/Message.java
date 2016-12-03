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
	
	public String getType() {
		return information[0];
	}
	
	public String getRouterFrom() {
		return information[1];
	}
	
	public Double getDistance() {
		return Double.parseDouble(information[2]);
	}
	
	public String getForwardedRouter() {
		return information[3];
	}
	
	public Double getDistanceFromRouterToForwardedRouter() {
		return Double.parseDouble(information[4]);
	}
	
	public String getUsersOfRouter() {
		return information[5];
	}
	
	public String getUserSentFrom() {
		return information[1];
	}
	
	public String getRouterDestination() {
		return information[2];
	}
	
	public String getMessage() {
		return information[3];
	}

	public byte[] getBytes() {
		return message.getBytes();
	}
	
}