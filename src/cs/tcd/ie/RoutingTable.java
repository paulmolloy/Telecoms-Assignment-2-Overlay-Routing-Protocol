package cs.tcd.ie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.util.ArrayList;

public class RoutingTable  {

	public static int timesToBeUnchaged = 20;

	private int timesTableUnchanged;
	private String routerName;
	private ArrayList<RoutingRow> distanceVectors;
	private int port;
	public static final int ROUTING_TABLE_CODE = 1;
	
	/**
	 *  The Routing table consists of an ArrayList called distanceVectors.
	 *  Each distanceVector consists of a RoutingRow<String, String, String, Double>, where
	 *  The First String represents the userName on the Router, 
	 *  The Second String is the name of the destination router.
	 *  The Third String is the router that needs to receive the Message in order for the Message to travel the sortest distance.
	 *  The Final int is the number of hops from the Source Router, to the Destination Router.
	 */
	
	public RoutingTable(Router router) {
		this.timesTableUnchanged = 0;
		this.port = router.getPort();
		this.routerName = router.getName();
		distanceVectors = new ArrayList<RoutingRow>();
		distanceVectors.add(new RoutingRow(router.getUsers(), routerName, "-", 0));
	}
	

	
	/*
	 * Update the Routing Table with a Message that is received.
	 * Checks whether new distance is smaller than original. If so
	 * then distance is updated with new distance taken from message which
	 * has been received.
	 */
	public void updateRoutingTable(RoutingTable table) {
		boolean changed = false;
		ArrayList<RoutingRow> distanceVectorsTable = table.getRows();
		//String tableName = table.getRouterName();
		for(RoutingRow vector: distanceVectorsTable) {
			for(RoutingRow vectorOfThisRouterTable: distanceVectors) {
				if(vector.getRouterDestination().equals(vectorOfThisRouterTable.getRouterDestination())) {
					int oldHops = vector.getHops(), newHops = vectorOfThisRouterTable.getHops();
					if ((newHops + 1) < oldHops) {
						vectorOfThisRouterTable.setRouterChoice(vector.getRouterChoice());
						vectorOfThisRouterTable.setHops(newHops + 1);
						changed = true;
					}
				}
			}
		}
		if(!changed) {
			timesTableUnchanged++;
		}
	}
	
	/*
	 * Gets the router name of a specific user on the router.
	 */
	public String getRouterToSendTo(String userName) {
		for (RoutingRow distanceVector : distanceVectors) {
			ArrayList<User> users = distanceVector.getUsers();
			for (User user : users) {
				if (user.getName().equals(userName)) {
					return distanceVector.getRouterChoice();
				}
			}
		}
		return null;
	}

	public ArrayList<RoutingRow> getRows() {
		return distanceVectors;
	}

	
	/**Routingtable constructor that turns a DatagramPacket made from a RoutingTable into a RoutingTable
	 * @param packet
	 */
	@SuppressWarnings("unchecked")
	public RoutingTable (DatagramPacket packet) {

		try {

			byte[] data;
			ByteArrayInputStream bin;
			ObjectInputStream oin;

			data = packet.getData();  // use packet content as seed for stream
			bin = new ByteArrayInputStream(data);
			oin = new ObjectInputStream(bin);
			
			int packetType = oin.readInt();  // read type from beginning of packet

			switch(packetType) {   // depending on type create content object 
			case ROUTING_TABLE_CODE:
				this.port = oin.readInt();
				this.routerName = oin.readUTF();
				this.distanceVectors = (ArrayList<RoutingRow>)oin.readObject();


				break;
			
			default:
				this.port  = -1;
				this.routerName = null;
				this.distanceVectors = null;
				break;
			}
			oin.close();
			bin.close();

		}
		catch(Exception e) {e.printStackTrace();}

	}

	public int getTimesNotChanged() {
		return timesTableUnchanged;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**Converts a Routing table into a DatagramPacket to be sent
	 * @return DatagramPacket
	 */
	public DatagramPacket toDatagramPacket() {
		DatagramPacket packet= null;

		try {
			ByteArrayOutputStream bout;
			ObjectOutputStream oout;
			byte[] data;
			bout = new ByteArrayOutputStream();
			oout = new ObjectOutputStream(bout);
			
			oout.writeInt(ROUTING_TABLE_CODE);
			oout.writeInt(port);
			oout.writeUTF(routerName);
			oout.writeObject(distanceVectors);
			
			oout.flush();
			data = bout.toByteArray(); // convert content to byte array

			packet = new DatagramPacket(data, data.length); // create packet from byte array
			oout.close();
			bout.close();
		}
		catch(Exception e) {e.printStackTrace();}

		return packet;
	}

}
