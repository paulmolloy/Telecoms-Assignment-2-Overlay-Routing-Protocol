package cs.tcd.linkState;

/*
	Authors: Yasir Zardari, Paul Molloy, Sean McDonagh
 */

import java.io.Serializable;
import java.util.ArrayList;

/*
    Forwarding Row is used by ForwardingTable. It contains the users of the destination Router, the name of the destination Router,
    the router before the destination router in order to achieve the shortest path to the destination Router, and finally the distance
    from the source router i.e the router that created the Forwarding Table, and the destination router.
 */

public class ForwardingRow implements Serializable {

    private String routerDestination, routerIntermediate;
    private ArrayList<User> users;
    private double distance;

    public ForwardingRow(ArrayList<User> users, String routerDestination, String routerIntermediate, double distance) {
        this.distance = distance;
        this.users = users;
        this.routerDestination = routerDestination;
        this.routerIntermediate = routerIntermediate;
        this.distance = distance;
    }

    public double getDistanceFromRouter() {
        return distance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getRouterDestination() {
        return routerDestination;
    }

    public String getRouterChoice() {
        return routerIntermediate;
    }

    public void setRouterDistance(double distance) {
        this.distance = distance;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setRouterDestination(String routerDestination) {
        this.routerDestination = routerDestination;
    }

    public void setRouterChoice(String routerChoice) {
        this.routerIntermediate = routerChoice;
    }

}
