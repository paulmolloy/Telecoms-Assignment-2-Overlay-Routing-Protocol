package cs.tcd.ie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

import tcdIO.Terminal;

public class Router extends Node{

	private ArrayList<Router> listOfRouters; 
	private RoutingTable table;
	private String routerName;
	private int port;
	private ArrayList<User> users;
	static final String DEFAULT_DST_NODE = "localhost";	
	
	public Router(ArrayList<User> users, String routerName, int port) { 	//possibly add adjacent ports to constuctor
		listOfRouters  = new ArrayList<Router>();
		this.table = new RoutingTable(this);
		this.setRouterName(routerName);
		this.setPort(port);
		this.setUsers(users);
		try {
			socket= new DatagramSocket(this.getPort());
			listener.go();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	/**
	 * Add the routers that are connected to this router in the network 
	 */
	public void addConnectedRouter(Router router) {
		listOfRouters.add(router);
	}

	/**
	 * Recieves a Packet, and directs to the correct user
	 */
	@Override
	public synchronized void onReceipt(DatagramPacket packet) {
		int type = checkPacketType(packet);
		switch(type){
			case RoutingTable.ROUTING_TABLE_CODE:
				//what happens when a RoutingTableDatagramPacket is received
				RoutingTable table = new RoutingTable(packet);
				//table.updateRoutingTable(table);
				break;
			case Message.MESSAGE_CODE:
				//what happens when a MessageDatagramPacket is received
				Message message = new Message(packet);
				break;
			default:
				break;	
		}
	}

	
	/**
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
		
		/**
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
	/**Checks which type of packet is sent returns its code.
	 * @param Datagrampacket packet
	 * @return int packetTypeCode
	 */
	private int checkPacketType(DatagramPacket packet){
		try {
			

			byte[] data;
			ByteArrayInputStream bin;
			ObjectInputStream oin;

			data= packet.getData();  // use packet content as seed for stream
			bin= new ByteArrayInputStream(data);
			oin= new ObjectInputStream(bin);
			
			int packetType = oin.readInt();  // read type from beginning of packet

			
			oin.close();
			bin.close();
			return packetType;

		}
		catch(Exception e) {e.printStackTrace();}

		return -1;
	}
	
	/**
	 * Sends the distance vectors of the routers from the routing table
	 */
	@Override
	public void ping() {
		for(Router routerSendingTo : listOfRouters){
			InetSocketAddress dstAddress = new InetSocketAddress(DEFAULT_DST_NODE, routerSendingTo.getPort());
			DatagramPacket packet = table.toDatagramPacket();
			packet.setSocketAddress(dstAddress);
			try {
			socket.send(packet);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	}
	
	public String getName() {
		return routerName;
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


	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}
	private void setUsers(ArrayList<User> users) {
		this.users = users;
		
	}
	private ArrayList<User> getUsers() {
		return this.users;
	}

}