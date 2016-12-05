package cs.tcd.ie;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

import tcdIO.Terminal;

public class Router extends Node{

	private ArrayList<Router> listOfRouters; 
	private RoutingTable table;
	private int x, y;
	private String routerName;
	private int port;
	static final String DEFAULT_DST_NODE = "localhost";	
	
	public Router(int x, int y, ArrayList<User> users, String routerName, int port) { 	//possibly add adjacent ports to constuctor
		listOfRouters  = new ArrayList<Router>();
		table = new RoutingTable(this, users);
		this.setX(x);
		this.setY(y);
		this.setRouterName(routerName);
		this.setPort(port);
		try {
			socket= new DatagramSocket(this.getPort());
			listener.go();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public void sendMessage(String user, String message) {
		DatagramPacket packet = null;
		String routerDestination = table.getRouterToSendTo(user);
		Router routerToSendTo = null;
		for(Router routerToSend: listOfRouters) {
			if(routerToSend.getName().equals(routerDestination)) {
				routerToSendTo = routerToSend;
			}
		}
		
		/*
		 * need to change the mesage to include the router that needs to receive the message as well!!!!!!!!!!!!!!
		 */
		InetSocketAddress dstAddress = new InetSocketAddress(DEFAULT_DST_NODE, routerToSendTo.getPort());
		packet = new DatagramPacket(message.getBytes(),message.getBytes().length, dstAddress);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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