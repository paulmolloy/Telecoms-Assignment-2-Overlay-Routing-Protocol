package cs.tcd.distanceVector;

import java.io.Serializable;
import java.util.ArrayList;

/*
	RoutingRow is used by RoutingTable, to associate together a destination router with a set of users of that router, and the corresponding
	router that the message must be sent to in order to achieve the shortest path/number of hops.
 */

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
