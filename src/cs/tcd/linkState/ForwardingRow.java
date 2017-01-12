package cs.tcd.linkState;

import java.io.Serializable;
import java.util.ArrayList;

public class ForwardingRow implements Serializable {

    private String routerDestination, routerIntermediate;
    private ArrayList<User> users;
    private double distance;

    public ForwardingRow(ArrayList<User> users, String routerDestination, String routerIntermediate) {
        this.users = users;
        this.routerDestination = routerDestination;
        this.routerIntermediate = routerIntermediate;
        this.distance = distance;
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
