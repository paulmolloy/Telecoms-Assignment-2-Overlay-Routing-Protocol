package cs.tcd.ie;

public class RoutingRow {

	String userName, routerDestination, routerChoice;
	Double length;
	
	
	public RoutingRow(String userName, String routerDestination, String routerChoice, Double length) {
		this.userName = userName;
		this.routerDestination = routerDestination;
		this.routerChoice = routerChoice;
		this.length = length;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getRouterDestination() {
		return routerDestination;
	}
	
	public String getRouterChoice() {
		return routerChoice;
	}
	
	public Double getDistance() {
		return length;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRouterDestination(String routerDestination) {
		this.routerDestination = routerDestination;
	}
	
	public void setRouterChoice(String routerChoice) {
		this.routerChoice = routerChoice;
	}
	
	public void setDistance(Double distance) {
		this.length = distance;
	}

}
