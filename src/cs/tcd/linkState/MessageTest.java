package cs.tcd.linkState;

import static org.junit.Assert.*;

import java.net.DatagramPacket;

import org.junit.Test;

public class MessageTest {

	@Test
	public void testToDatagramPacket() {
		String testString ="Hello";
		Message in = new Message(testString);
		DatagramPacket mPacket = in.toDatagramPacket();
		Message out = new Message(mPacket);
		System.out.println(out.toString());
		assertEquals("toDatagramPacket failed to convert correctly, incorrect messageString", testString,out.toString());

		
	}

}
