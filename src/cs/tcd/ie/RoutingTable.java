package cs.tcd.ie;

//import edu.stanford.nlp.util.Triple;
import java.util.ArrayList;

public class RoutingTable {
	
	private String routerName;
	private ArrayList<RoutingRow> distanceVectors;
	private Router router;
	
	/*
	 *  The Routing table consists of an ArrayList called distanceVectors.
	 *  Each distanceVector consits of a triple<String, Integer, String>, where
	 *  the first String represents the userName on the Router, 
	 *  the Integer is the distance from the user to the router,
	 *  The third String is the name of the router.
	 */
	
	public RoutingTable(Router router, ArrayList<User> users, String routerName) {
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
	 * returns true if the distence entered in the last message from A - D is shorter than the last distance from A - D
	 * If it is shorter, then it becomes the newest shortest distance between A - D
	 */
	public double distanceBetweenRouters(double distance, int xPos, int yPos) {
		int x = router.getxPos();
		int y = router.getyPos();
		
		Double distFromRouterAToB = Math.sqrt(Math.pow((x - xPos), 2) + Math.pow((y - yPos), 2));
		return (distance + distFromRouterAToB);
	}
	
}
