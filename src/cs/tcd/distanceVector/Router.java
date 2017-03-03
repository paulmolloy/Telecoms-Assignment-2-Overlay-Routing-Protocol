package cs.tcd.distanceVector;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;


import tcdIO.Terminal;

/*
	Router class is used to create Router Objects. As soon as a Router Object is created, in order
	to find out the Topology of the Network, it beings the process of pinging(). It stops pinging when the corresponding RoutingTable
	is unchanged 20 times or more, by incoming routing tables. Then when a message is being sent, the sendMessage method must be called.
	It decides which router to send to, based on the Routing Table.
 */

public class Router extends Node {

	private ArrayList<Router> listOfRouters;
	private RoutingTable table;
	private String routerName;
	private int port;
	private ArrayList<User> users;
	private Terminal terminal;
	static final String DEFAULT_DST_NODE = "localhost";

	public Router(ArrayList<User> users, String routerName, int port) { 	//possibly add adjacent ports to constuctor
		this.terminal = new Terminal(routerName);
		listOfRouters  = new ArrayList<Router>();
		this.setRouterName(routerName);
		this.setPort(port);
		this.users = users;
		this.setUsers(users);
		this.table = new RoutingTable(this, routerName);
		table.setRouterName(routerName);
		table.setPort(port);
		try {
			socket = new DatagramSocket(this.getPort());
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
		int type = checkPacketType(packet);
		Message message;
		switch(type){
			case RoutingTable.ROUTING_TABLE_CODE:
				RoutingTable rt = new RoutingTable(packet);
				terminal.println("Received Ping from: " + rt.getRouterName());
				table.updateRoutingTable(rt);
				if(RoutingTable.TIMES_TO_BE_UNCHANGED <= table.getTimesNotChanged()) {
				}	else	{
					ping();
				}
				break;
				
			case Message.MESSAGE_CODE:
				terminal.println("recieved message!");
				message = new Message(packet);
				if(routerContainsUser(message.getUserTo())) {
					//terminal.println(message.getType())
					terminal.println("From: " + message.getUserFrom() + ",To: " + message.getUserTo() + ", Normal Message:" + message.getMessage());
				}	else	{
					terminal.println("Message not for user on this Router.");
					terminal.println("Recieved from: Router " + table.getRouterToSendTo(message.getUserFrom()) + " Sending on to: Router " + table.getRouterToSendTo(message.getUserTo()));
					
					sendMessage(message);
				}
				break;
			case Message.FILE_CODE:
				terminal.println("recieved  file message!");
				message = new Message(packet);
				if(routerContainsUser(message.getUserTo())) {
					terminal.println("From: " + message.getUserFrom() + ",To: " + message.getUserTo() + ", FIleName:" + message.getMessage());
					
					//Save file

					try {
						byte[] fileBytes =message.getFileBytes();
						String fileName = message.getMessage();
						System.out.println("FileName: " + "1"+fileName ); //one added so it doesnt overwrite original file
						FileOutputStream fos = new FileOutputStream("1"+fileName);
						fos.write(fileBytes);
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}	else	{
					terminal.println("Message not for user on this Router.");
					terminal.println("Recieved from: Router " + table.getRouterToSendTo(message.getUserFrom()) + " Sending on to: Router " + table.getRouterToSendTo(message.getUserTo()));
					
					sendMessage(message);
				}
				break;

			default:
				break;
		}
	}

	/*
	 * Sends normal messages from router A to B.
	 */

	@Override
	public void sendMessage(Message message) {
		terminal.println("Sending Message to: " + message.getUserTo());
		DatagramPacket packet = null;
		String routerDestination = table.getRouterToSendTo(message.getUserTo());
		Router routerToSendTo = null;
		for(Router routerToSend: listOfRouters) {
			if(routerToSend.getName().equals(routerDestination)) {
				routerToSendTo = routerToSend;
			}
		}

		if(routerToSendTo == null) {
			terminal.println("User not found on network.");
		}	else {
			/*
			 * need to change the message to include the router that needs to receive the message as well!!!!!!!!!!!!!!
			 */
			InetSocketAddress dstAddress = new InetSocketAddress(DEFAULT_DST_NODE, routerToSendTo.getPort());
			packet = message.toDatagramPacket();
			packet.setSocketAddress(dstAddress);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**Checks which type of packet is sent returns its code.
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

	/*
	 * Sends the distance vectors of the routers from the routing table
	 */
	@Override
	public synchronized void ping() {
		DatagramPacket packet = table.toDatagramPacket();
		for(Router routerSendingTo : listOfRouters){
			InetSocketAddress dstAddress = new InetSocketAddress(DEFAULT_DST_NODE, routerSendingTo.getPort());
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

	public boolean routerContainsUser(String userName) {
		for(User user: users) {
			if(user.getName().equals(userName)) {
				return true;
			}
		}
		return false;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;

	}
	public ArrayList<User> getUsers() {
		return this.users;
	}

}