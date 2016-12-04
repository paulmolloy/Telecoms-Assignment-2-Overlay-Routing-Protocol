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
	 *  The Final Double is the distance from the Source Router, to the Destination Router.
	 */
	
	public RoutingTable(Router router, ArrayList<User> users) {
		this.router = router;
		this.routerName = router.getName();
		distanceVectors = new ArrayList<RoutingRow>();
		for(int i = 0; i < users.size(); i++) {
			distanceVectors.add(new RoutingRow(users.get(i).getName(), null, null, null));
		}
	}
	
	/*
	 * Update the Routing Table with a Message that is recieved.
	 */
	public void updateRoutingTable(Message message) {
		boolean updated = false;
		for(RoutingRow distanceVector: distanceVectors) {
			if(message.getUserSentFrom().equals(distanceVector.getUserName())) {
				updated = true;
				Double currentDistance = distanceVector.getDistance();
				Double newDistance = distanceBetweenRouters(message.getDistanceFromRouterToForwardedRouter(), message.getx(), message.gety());
				if(newDistance < currentDistance) {
					distanceVector.setDistance(newDistance);
					distanceVector.setRouterChoice(message.getRouterFrom());
				}
			}
		}
		if(updated == false) {
			Double distance = distanceBetweenRouters(message.getDistance(), message.getx(), message.gety());
			distanceVectors.add(new RoutingRow(message.getUserSentFrom(), message.getForwardedRouter(), message.getRouterFrom(), distance));
		}
	}
	
	/*
	 * Returns a Double which indicates the distance from Router A to Router C.
	 * double Distance is the distance from Router B to Router C.
	 * int xPos is the x coordinate of Router B, int yPos is the y coordinate of Router B
	 */
	public double distanceBetweenRouters(double distance, int xPos, int yPos) {
		int x = router.getxPos();
		int y = router.getyPos();
		
		Double distFromRouterAToB = Math.sqrt(Math.pow((x - xPos), 2) + Math.pow((y - yPos), 2));
		return (distance + distFromRouterAToB);
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
