package cs.tcd.linkState;

import java.io.Serializable;
import java.util.ArrayList;

public class TopologyRow implements Comparable<TopologyRow> {

    private String router;
    private ArrayList<User> users;
    private double distance;

    public TopologyRow(String router, double distance) {
        this.router = router;
        this.distance = distance;
    }

    public int compareTo(TopologyRow row) {
        double num1 = distance;
        double num2 = row.getDistance();
        if(num1 < num2) {
            return 1;                                       // not sure if this is right......
        }
        else if(num1 == num2) {
            return 0;
        }   else    {
            return -1;
        }
    }


    public String getRouter() {
        return router;
    }

    public double getDistance() {
        return distance;
    }


    public void getRouter(String router) {
        this.router = router;
    }

}
