package cs.tcd.ie;

import java.util.ArrayList;
import java.util.Scanner;

import edu.stanford.nlp.io.EncodingPrintWriter.out;

public class Client {

	public static void main(String[] args) {

		int size = 0;
		ArrayList<Router> routers = new ArrayList<Router>();

		ArrayList<User> usersA = new ArrayList<User>();
		usersA.add(new User("Aaron"));
		usersA.add(new User("Able"));
		routers.add(new Router(usersA, "A", 50000));

		ArrayList<User> usersB = new ArrayList<User>();
		usersB.add(new User("Ben"));
		routers.add(new Router(usersB, "B", 50001));


		ArrayList<User> usersC = new ArrayList<User>();
		usersC.add(new User("Carl"));
		usersC.add(new User("Carla"));
		usersC.add(new User("Cian"));
		routers.add(new Router(usersC, "C", 50002));

		ArrayList<User> usersD = new ArrayList<User>();
		usersD.add(new User("Dillon"));
		usersD.add(new User("Denise"));
		routers.add(new Router(usersD, "D", 50003));

		ArrayList<User> usersE = new ArrayList<User>();
		usersE.add(new User("Earl"));
		usersE.add(new User("Emma"));
		routers.add(new Router(usersE, "E", 50004));

		ArrayList<User> usersF = new ArrayList<User>();
		usersF.add(new User("Fiona"));
		routers.add(new Router(usersF, "F", 50005));

		ArrayList<User> usersG = new ArrayList<User>();
		usersG.add(new User("Gloria"));
		usersG.add(new User("Gabrial"));
		routers.add(new Router(usersG, "G", 50006));

		ArrayList<User> usersH = new ArrayList<User>();
		usersH.add(new User("Harry"));
		usersH.add(new User("Hellen"));
		routers.add(new Router(usersH, "H", 50007));

		ArrayList<User> usersI = new ArrayList<User>();
		usersI.add(new User("Ian"));
		routers.add(new Router(usersI, "I", 50008));

		ArrayList<User> usersJ = new ArrayList<User>();
		usersJ.add(new User("Jill"));
		usersJ.add(new User("Joe"));
		routers.add(new Router(usersJ, "J", 50009));

		size = 10;

		// Routers connected to one another
		routers.get(0).addConnectedRouter(routers.get(1));	// Connect Router A to B

		routers.get(1).addConnectedRouter(routers.get(0));	// Connect Router B to A
		routers.get(1).addConnectedRouter(routers.get(2));	// Connect Router B to C
		routers.get(1).addConnectedRouter(routers.get(9));	// Connect Router B to J

		routers.get(2).addConnectedRouter(routers.get(1));	// Connect Router C to B
		routers.get(2).addConnectedRouter(routers.get(3));	// Connect Router C to D
		routers.get(2).addConnectedRouter(routers.get(8));  // Connect Router C to I

		routers.get(3).addConnectedRouter(routers.get(2));	// Connect Router D to C
		routers.get(3).addConnectedRouter(routers.get(4));	// Connect Router D to E
		routers.get(3).addConnectedRouter(routers.get(5));	// Connect Router D to F

		routers.get(4).addConnectedRouter(routers.get(3));	// Connect Router E to D
		routers.get(4).addConnectedRouter(routers.get(5));	// Connect Router E to F

		routers.get(5).addConnectedRouter(routers.get(3));	// Connect Router F to D
		routers.get(5).addConnectedRouter(routers.get(4));	// Connect Router F to E
		routers.get(5).addConnectedRouter(routers.get(6));	// Connect Router F to G

		routers.get(6).addConnectedRouter(routers.get(5));	// Connect Router G to F
		routers.get(6).addConnectedRouter(routers.get(7));	// Connect Router G to H

		routers.get(7).addConnectedRouter(routers.get(6));	// Connect Router H to G
		routers.get(7).addConnectedRouter(routers.get(8));	// Connect Router H to I
		routers.get(7).addConnectedRouter(routers.get(9));	// Connect Router H to J

		routers.get(8).addConnectedRouter(routers.get(2));	// Connect Router I to C
		routers.get(8).addConnectedRouter(routers.get(7));	// Connect Router I to H

		routers.get(9).addConnectedRouter(routers.get(1));	// Connect Router J to B
		routers.get(9).addConnectedRouter(routers.get(7));	// Connect Router J to H


		//Graph<Router> graph = new Graph<Router>();

		routers.get(0).ping();

		System.out.println("Please enter the users to send a string from and to, followed by a message. Each part should be seperated by a colan, ':'.");
		Scanner sc  = new Scanner(System.in);
		while(sc.hasNext()) {
			String line = sc.next();
			String[] data = line.split(":");
			if(data[0].equals("Aaron") || data[0].equals("Able"))	{
				routers.get(0).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Ben")) {
				routers.get(1).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Carl") || data[0].equals("Carla") || data[0].equals("Cian")) {
				routers.get(2).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Dillon") || data[0].equals("Denise")) {
				routers.get(3).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Earl") || data[0].equals("Emma")) {
				routers.get(4).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Fiona")) {
				routers.get(5).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Gloria") || data[0].equals("Gabrial")) {
				routers.get(6).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Harry") || data[0].equals("Hellen")) {
				routers.get(7).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}	else if(data[0].equals("Ian")) {
				routers.get(8).sendMessage(new Message(data[0] + "," + data[1] + data[3]));
			}
			System.out.println("Message Received");
		}
	}
}