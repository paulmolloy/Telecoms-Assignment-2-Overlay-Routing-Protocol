package cs.tcd.ie;

import static org.junit.Assert.*;

import java.net.DatagramPacket;
import java.util.ArrayList;

import org.junit.Test;

public class RoutingTableTest {

	@Test
	public void testtoDatagramPacket() {
		
		ArrayList<User> r1Users = new ArrayList<User>();
		r1Users.add(new User("Bob"));
		r1Users.add(new User("Pete"));

		
		Router r1 = new Router(1, 1, r1Users, "Router 1", 50002);
		RoutingTable rTable1 = new RoutingTable(r1, r1Users);
		
		DatagramPacket packet =rTable1.toDatagramPacket();
		RoutingTable table = new RoutingTable(packet);
		System.out.println("Recived Router: "+table.getRouterName());
		System.out.println("Recived Port: "+table.getPort());

		//fail("Not yet implemented");
	}

}
