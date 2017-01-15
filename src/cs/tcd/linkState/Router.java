package cs.tcd.linkState;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

import cs.tcd.distanceVector.Message;
import tcdIO.Terminal;

/*
	Router class is used to create Router Objects. As soon as a Router Object is created, in order
	to find out the Topology of the Network, it beings the process of pinging() it's Topology table, as well as the TopologyTables it receives as well.
	It stops pinging when it has received a similar TopologyTable to the ones it has over 1000 times. This is the point at which all of the network has each of the Topology tables.
	Then, ForwardingTables are constructed.
	Then when a message is being sent, the sendMessage method must be called. It decides which router to send to, based on the ForwardingTable.
 */

public class Router extends Node {

	private ArrayList<Router> listOfConnectedRouters;
	private String routerName;
	private int port;
	private ArrayList<User> users;
	private Terminal terminal;
	private ArrayList<TopologyTable> tables;
	private TopologyTable table;
	private ForwardingTable forwardingTable;
	private Coordinate coord;
	private int numOfTimesTablesTheSame;
	static final String DEFAULT_DST_NODE = "localhost";
	static final Integer MAX_NUM_OF_TIMES_TABLES_THE_SAME = 1000;

	public Router(ArrayList<User> users, Coordinate coord, String routerName, int port) { 	//possibly add adjacent ports to constuctor
		this.routerName = routerName;
		this.terminal = new Terminal(routerName);
		listOfConnectedRouters  = new ArrayList<Router>();
		this.numOfTimesTablesTheSame = 0;
		this.coord = coord;
		this.setRouterName(routerName);
		this.setPort(port);
		this.users = users;
		this.setUsers(users);
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
		listOfConnectedRouters.add(router);
		createTopologyTable();
	}

	/*
	 * Recieves a Packet, and directs to the correct user
	 */
	@Override
	public synchronized void onReceipt(DatagramPacket packet) {
		int type = checkPacketType(packet);
		switch(type){
			case TopologyTable.TOPOLOGY_TABLE_CODE:
				TopologyTable tt = new TopologyTable(packet);
				terminal.println("Received Ping from: " + tt.getRouterName());
				boolean tableAlreadyInList = false;
				int index = 0;
				while(!tableAlreadyInList && index < tables.size()) {
					if(tables.get(index++).isSameTable(tt)) {
						tableAlreadyInList = true;
					}
				}
				if(!tableAlreadyInList) {
					tables.add(tt);
				}	else	{
					numOfTimesTablesTheSame++;
				}
				if(numOfTimesTablesTheSame <= MAX_NUM_OF_TIMES_TABLES_THE_SAME) {
					ping();
				}	else	{
					forwardingTable = new ForwardingTable(tables, routerName);
				}
				break;
			case cs.tcd.linkState.Message.MESSAGE_CODE:
				terminal.println("recieved message!");
				cs.tcd.linkState.Message message = new cs.tcd.linkState.Message(packet);
				
				if(routerContainsUser(message.getUserTo())) {
					terminal.println("From: " + message.getUserFrom() + ", To: " + message.getUserTo() + ", Message:" + message.getMessage());
				}	else	{
					terminal.println("Message not for user on this Router.");
					terminal.println("Recieved from: Router " + forwardingTable.getRouterTo(message.getUserFrom()) + " Sending on to: Router " + forwardingTable.getRouterTo(message.getUserTo()));

					sendMessage(message);
				}
				break;
			case Message.FILE_CODE:
				terminal.println("recieved  file message!");
				message = new cs.tcd.linkState.Message(packet);
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
					terminal.println("Recieved from: Router " + forwardingTable.getRouterTo(message.getUserFrom()) + " Sending on to: Router " + forwardingTable.getRouterTo(message.getUserTo()));
					
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
	public void sendMessage(cs.tcd.linkState.Message message) {
		terminal.println("Sending Message to: " + message.getUserTo());
		DatagramPacket packet = null;
		String routerDestination = forwardingTable.getRouterTo(message.getUserTo());
		Router routerToSendTo = null;
		for(Router routerToSend: listOfConnectedRouters) {
			if(routerToSend.getName().equals(routerDestination)) {
				routerToSendTo = routerToSend;
			}
		}

		if(routerToSendTo == null) {
			terminal.println("Router: " + routerToSendTo + " is not found in Connected Routers.");
		}
		else {
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

			data = packet.getData();  // use packet content as seed for stream
			bin = new ByteArrayInputStream(data);
			oin = new ObjectInputStream(bin);

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
		for(TopologyTable tableToSend: tables) {
			DatagramPacket packet = tableToSend.toDatagramPacket();
			for (Router routerSendingTo : listOfConnectedRouters) {
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
	}

	public String getName() {
		return routerName;
	}

	public ArrayList<Router> getListOfRouters() {
		return listOfConnectedRouters;
	}

	public void setListOfRouters(ArrayList<Router> listOfRouters) {
		this.listOfConnectedRouters = listOfRouters;
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

	public Coordinate getCoord() {
		return coord;
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

	public void createTopologyTable() {
		tables = new ArrayList<TopologyTable>();
		ArrayList<String> routers = new ArrayList<String>();
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		for(Router router: listOfConnectedRouters) {
			routers.add(router.getName());
			coords.add(router.getCoord());
		}
		table = new TopologyTable(routers, coords, users, routerName, coord);
		tables.add(table);
	}

}