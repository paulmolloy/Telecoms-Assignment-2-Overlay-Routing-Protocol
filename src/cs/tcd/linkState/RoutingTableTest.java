package cs.tcd.linkState;

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
		int testPort = 50002;
		String testRName = "Router 1";


		Router r1 = new Router(r1Users, testRName, testPort);
		RoutingTable rTable1 = new RoutingTable(r1, testRName);

		DatagramPacket packet =rTable1.toDatagramPacket();
		RoutingTable table = new RoutingTable(packet);
		System.out.println("Recived Router: "+table.getRouterName());
		System.out.println("Recived Port: "+table.getPort());
		assertEquals("toDatagramPacket failed to convert correctly, incorrect port", testPort,table.getPort());
		assertEquals("toDatagramPacket failed to convert correctly, incorrect routerName", testRName,table.getRouterName());


	}

}
