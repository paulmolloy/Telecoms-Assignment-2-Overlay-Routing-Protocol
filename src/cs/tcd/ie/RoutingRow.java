package cs.tcd.ie;

import java.io.Serializable;
import java.util.ArrayList;

public class RoutingRow implements Serializable {

	private String routerDestination, routerIntermediate;
	private ArrayList<User> users;
	private int hops;

	public RoutingRow(ArrayList<User> users, String routerDestination, String routerIntermediate, int hops) {
		this.users = users;
		this.routerDestination = routerDestination;
		this.routerIntermediate = routerIntermediate;
		this.hops = hops;
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
	public String getRouterDestination() {
		return routerDestination;
	}
	
	public String getRouterChoice() {
		return routerIntermediate;
	}
	
	public int getHops() {
		return hops;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void setRouterDestination(String routerDestination) {
		this.routerDestination = routerDestination;
	}
	
	public void setRouterChoice(String routerChoice) {
		this.routerIntermediate = routerChoice;
	}
	
	public void setHops(int hops) {
		this.hops = hops;
	}

}
