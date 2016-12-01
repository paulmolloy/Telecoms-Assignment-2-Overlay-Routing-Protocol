package cs.tcd.ie;

import java.net.DatagramPacket;
import java.util.ArrayList;


public class Router extends Node{

	ArrayList<Router> listOfRouters;
	RoutingTable table;
	private int x, y;
	private String routerName;
	
	
	public Router(int x, int y, ArrayList<User> users, String routerName) {
		listOfRouters  = new ArrayList<Router>();
		table = new RoutingTable(this, users);
		this.x = x;
		this.y = y;
		this.routerName = routerName;
	}
	
	/*
	 * Add the routers that are connected to this router in the network 
	 */
	public void addConnectedRouter(Router router) {
		listOfRouters.add(router);
	}

	/*
	 * Recieves a Packet, and directs to the correct user
	 */
	@Override
	public void onReceipt(DatagramPacket packet) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Sends the distance vectors of the routers from the routing table
	 */
	public void ping() {
		// add pinging method
	}
	
	public String getName() {
		return routerName;
	}
	
	public int getxPos() {
		return x;
	}
	
	public int getyPos() {
		return y;
	}
	
}