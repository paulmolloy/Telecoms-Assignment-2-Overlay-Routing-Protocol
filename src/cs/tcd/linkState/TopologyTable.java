package cs.tcd.linkState;

import java.io.*;
import java.net.DatagramPacket;
import java.util.ArrayList;


/**
 * Created by root on 11/01/17.
 */
public class TopologyTable {

    private ArrayList<TopologyRow> table;
    private ArrayList<String> connectedRouters;
    private ArrayList<User> users;
    private ArrayList<Coordinate> coords;
    private Coordinate coord;
    private String routerName;
    public static final int TOPOLOGY_TABLE_CODE = 1;

    public TopologyTable(ArrayList<String> connectedRouters, ArrayList<Coordinate> coords, ArrayList<User> users, String routerName, Coordinate mainCoord) {
        this.table = new ArrayList<TopologyRow>();
        this.connectedRouters = connectedRouters;
        this.routerName = routerName;
        this.users = users;
        this.coord = mainCoord;
        this.coords = coords;
        createVectors();
    }

    public TopologyTable(DatagramPacket packet) {
        try {
            byte[] data;
            ByteArrayInputStream bin;
            ObjectInputStream oin;

            data = packet.getData();  // use packet content as seed for stream
            bin = new ByteArrayInputStream(data);
            oin = new ObjectInputStream(bin);
            int packetType = oin.readInt();  // read type from beginning of packet

            switch(packetType) {   // depending on type create content object
                case TOPOLOGY_TABLE_CODE:
                    this.routerName = oin.readUTF();
                    this.table = (ArrayList<TopologyRow>)oin.readObject();
                    this.users = (ArrayList<User>)oin.readObject();
                    break;

                default:
                    this.routerName = null;
                    this.table = null;
                    break;
            }
            oin.close();
            bin.close();

        }
        catch(Exception e) {e.printStackTrace();}
    }

    public String getRouterName() {
        return routerName;
    }

    public ArrayList<String> getConnectedRouters() {
        return connectedRouters;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<TopologyRow> getVectors() {
        return table;
    }

    private void createVectors() {
        for(int i = 0; i < connectedRouters.size(); i++) {
            table.add(new TopologyRow(connectedRouters.get(i), coords.get(i).getDistanceFrom(coord)));
        }
    }

    /**Converts a Routing table into a DatagramPacket to be sent
     * @return DatagramPacket
     */
    public DatagramPacket toDatagramPacket() {
        DatagramPacket packet = null;

        try {
            ByteArrayOutputStream bout;
            ObjectOutputStream oout;
            byte[] data;
            bout = new ByteArrayOutputStream();
            oout = new ObjectOutputStream(bout);

            oout.writeInt(TOPOLOGY_TABLE_CODE);

            oout.writeUTF(routerName);
            oout.writeObject(table);
            //oout.flush();
            oout.writeObject(users);
            oout.flush();

            data = bout.toByteArray(); // convert content to byte array

            packet = new DatagramPacket(data, data.length); // create packet from byte array
            oout.close();
            bout.close();
        }
        catch(Exception e) {e.printStackTrace();}

        return packet;
    }

    public boolean isSameTable(TopologyTable tt) {
        if(tt.getRouterName().equals(routerName)) {
            return true;
        }   else    {
            return false;
        }
    }

}
