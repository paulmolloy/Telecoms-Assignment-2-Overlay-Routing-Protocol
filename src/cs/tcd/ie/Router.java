package cs.tcd.ie;

import java.net.DatagramPacket;
import java.util.ArrayList;


public class Router extends Node{

	private ArrayList<Router> listOfRouters;
	private RoutingTable table;
	private int x, y;
	private String routerName;
	private int port;
	
	


	public Router(int x, int y, ArrayList<User> users, String routerName, int port) {
		listOfRouters  = new ArrayList<Router>();
		table = new RoutingTable(this, users);
		this.setX(x);
		this.setY(y);
		this.setRouterName(routerName);
		
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
	public synchronized void onReceipt(DatagramPacket packet) {
		table.updateRoutingTable(new Message(packet.getData().toString()));
	}
	
	/*
	 * Sends normal messages from router A to B.
	 */
	@Override
	public synchronized void sendMessage(String message) {
		
	}
	
	/*
	 * Sends the distance vectors of the routers from the routing table
	 */
	@Override
	public void ping() {
		
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
	public ArrayList<Router> getListOfRouters() {
		return listOfRouters;
	}

	public void setListOfRouters(ArrayList<Router> listOfRouters) {
		this.listOfRouters = listOfRouters;
	}

	public RoutingTable getTable() {
		return table;
	}

	public void setTable(RoutingTable table) {
		this.table = table;
	}
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

}