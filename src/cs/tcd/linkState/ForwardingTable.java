package cs.tcd.linkState;


import java.util.*;

/**
 * Created by root on 11/01/17.
 */
public class ForwardingTable {

    private ArrayList<TopologyTable> tables;
    private PriorityQueue<TopologyRow> pq;
    private String[] edgeTo;
    private double[] distTo;
    private ForwardingRow[] forwardingTable;
    private String routerName;
    static final Integer MAX_SIZE_OF_NETWORK = 1000;


    public ForwardingTable(ArrayList<TopologyTable> topologyTables, String routerName) {
        this.tables = topologyTables;
        this.routerName = routerName;

        pq = new PriorityQueue<>();                                        // Max number of routers in network.
        edgeTo = new String[tables.size()];
        distTo = new double[tables.size()];
        forwardingTable = new ForwardingRow[tables.size()];

        for(int i = 0; i < tables.size(); i++) {
            TopologyTable tmp = tables.get(i);
            char letter = tmp.getRouterName().charAt(0);
            int index = letter - 65;
            distTo[index] = Double.POSITIVE_INFINITY;
            if(tmp.getRouterName().equals(routerName)) {
                pq.add(new TopologyRow(tmp.getRouterName(), 0));
                distTo[index] = 0;
            }
            forwardingTable[index] = new ForwardingRow(tmp.getUsers(), tmp.getRouterName(), null);
        }

        this.runDjikstra();

    }

    public void runDjikstra() {
        while(!pq.isEmpty()) {
            TopologyRow mainRow = pq.poll();

            ArrayList<TopologyRow> connectedRouters = null;
            for(TopologyTable table: tables) {
                if(table.getRouterName().equals(mainRow.getRouter())) {
                    connectedRouters = table.getVectors();
                }
            }

            for(TopologyRow row: connectedRouters) {
                char letterOne = row.getRouter().charAt(0);
                int routerIndex = letterOne - 65;
                char letterTwo = mainRow.getRouter().charAt(0);
                int mainRouterIndex = letterTwo - 65;
                if(distTo[routerIndex] > distTo[mainRouterIndex] + row.getDistance()) {
                    distTo[routerIndex] = distTo[mainRouterIndex] + row.getDistance();
                    edgeTo[routerIndex] = mainRow.getRouter();
                    boolean foundTopologyRow = false;
                    Iterator iterator = pq.iterator();
                    while(iterator.hasNext()) {
                        TopologyRow tr = (TopologyRow)iterator.next();
                        if (tr.getRouter().equals(row.getRouter())) {
                            pq.remove(tr);
                            pq.add(new TopologyRow(row.getRouter(), distTo[routerIndex]));
                            forwardingTable[routerIndex].setRouterChoice(mainRow.getRouter());
                            foundTopologyRow = true;
                        }
                    }
                    if(!foundTopologyRow) {
                        pq.add(new TopologyRow(row.getRouter(), distTo[routerIndex]));
                    }
                }
            }
        }
    }

    public String getRouterTo(String userTo) {
        boolean foundRouter =  false;
        String nextRouter = null;
        ForwardingRow fr = null;
        for(ForwardingRow row: forwardingTable) {
            ArrayList<User> users = row.getUsers();
            for(User user: users) {
                if(user.equals(userTo)) {
                    foundRouter = true;
                    fr = row;
                }
            }
            if(foundRouter) {
                boolean keepGoing = true;
                while(keepGoing) {
                    nextRouter = fr.getRouterChoice();
                    if(nextRouter.equals(routerName)) {
                        keepGoing = false;
                    }   else    {
                        char letter = nextRouter.charAt(0);
                        int index = letter - 65;
                        fr = forwardingTable[index];
                    }
                }
                return nextRouter;
            }
        }
        return null;
    }
}