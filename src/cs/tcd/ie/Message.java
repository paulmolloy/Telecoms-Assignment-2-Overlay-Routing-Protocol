package cs.tcd.ie;

import java.util.ArrayList;

public class Message {
	
	
	/*
	 * Message is used to construct and deconstruct the packets that are sent to and from
	 * each router. They possess getter and setter methods.
	 */
	
	private String message;
	private String[] information;
	
	public Message() {
		this.message = "";
	}
	
	public Message(String message) {
		this.message = message;
		information = message.split(",");
	}
	
	/*
	 * Getters
	 */
	
	public String getType() {
		return information[0];
	}
	
	public String getRouterFrom() {
		return information[1];
	}
	
	public String getRouterConnected() {
		return information[3];
	}
	
	public int getRouterHops() {
		return Integer.parseInt(information[4]);
	}
	
	public int getUserSize() {
		return Integer.parseInt(information[5]);
	}
	
	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		int size = getUserSize();
		for(int i = 0; i <= size; i++) {
			users.add(new User(information[6 + i]));
		}
		return users;
	}
	
	public byte[] getBytes() {
		return message.getBytes();
	}
	
	/*
	 * Setters
	 */
	
	public void setType(String type) {
		message += type + ",";
	}
	
	public void setRouterFrom(String routerFrom) {
		message += routerFrom + ",";
	}
	
	public void setRouterConnected(String routerConnected) {
		message += routerConnected + ",";
	}
	
	public void setRouterHops(int routerHops) {
		message += Integer.toString(routerHops) + ",";
	}
	
	public void setUserSize(int size) {
		message += Integer.toString(size) + ",";
	}
	
	public void setUsers(ArrayList<User> users) {
		int size = users.size();
		for(int i = 0; i <= size; i++) {
			message += users.get(i);
			if(i != size) {
				message += ",";
			}
		}
	}
}