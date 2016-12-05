package cs.tcd.ie;

import java.util.ArrayList;

public class RoutingTable {
	
	private String routerName;
	private ArrayList<RoutingRow> distanceVectors;
	private Router router;
	
	/*
	 *  The Routing table consists of an ArrayList called distanceVectors.
	 *  Each distanceVector consists of a RoutingRow<String, String, String, Double>, where
	 *  The First String represents the userName on the Router, 
	 *  The Second String is the name of the destination router.
	 *  The Third String is the router that needs to receive the Message in order for the Message to travel the sortest distance.
	 *  The Final int is the number of hops from the Source Router, to the Destination Router.
	 */
	
	public RoutingTable(Router router, ArrayList<User> users) {
		this.router = router;
		this.routerName = router.getName();
		distanceVectors = new ArrayList<RoutingRow>();
	}
	
	/*
	 * Update the Routing Table with a Message that is recieved.
	 */
	public void updateRoutingTable(Message message) {
		boolean updated = false;
		ArrayList<User> users = message.getUsers();
		for(User user: users) {
			for(RoutingRow distanceVector: distanceVectors) {
				if(user.getName().equals(distanceVector.getUserName())) {
					int newHops = message.getRouterHops() + 1;
					if(newHops < distanceVector.getHops()) {
						distanceVector.setDistance(newHops);
						distanceVector.setRouterChoice(message.getRouterFrom());
					}
					updated = true;
				}
			}
			if(updated == false) {
				distanceVectors.add(new RoutingRow(user.getName(), message.getRouterConnected(), message.getRouterFrom(), message.getRouterHops()));
			}
		}
	}
	
	/*
	 * Gets the router name of a specific user on the router.
	 */
	public String getRouterToSendTo(String user) {
		for(RoutingRow distanceVector: distanceVectors) {
			if(distanceVector.getUserName().equals(user)) {
				return distanceVector.getRouterChoice();
			}
		}
		return null;
	}
	
}
