package cs.tcd.ie;

public class RoutingRow {

	String userName, routerDestination, routerChoice;
	Double length;
	
	public RoutingRow(String userName, String routerDestination, String routerChoice) {
		this.userName = userName;
		this.routerDestination = routerDestination;
		this.routerChoice = routerChoice;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getrouterDestination() {
		return routerDestination;
	}
	
	public String getRouterChoice() {
		return routerChoice;
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

}
