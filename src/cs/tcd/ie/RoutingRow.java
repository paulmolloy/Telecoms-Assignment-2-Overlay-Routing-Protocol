package cs.tcd.ie;

public class RoutingRow {

	String userName, routerDestination, routerIntermediate;
	int hops;
	
	
	public RoutingRow(String userName, String routerDestination, String routerIntermediate, int hops) {
		this.userName = userName;
		this.routerDestination = routerDestination;
		this.routerIntermediate = routerIntermediate;
		this.hops = hops;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getRouterDestination() {
		return routerDestination;
	}
	
	public String getRouterChoice() {
		return routerIntermediate;
	}
	
	public int getHops() {
		return hops;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRouterDestination(String routerDestination) {
		this.routerDestination = routerDestination;
	}
	
	public void setRouterChoice(String routerChoice) {
		this.routerIntermediate = routerChoice;
	}
	
	public void setDistance(int hops) {
		this.hops = hops;
	}

}
