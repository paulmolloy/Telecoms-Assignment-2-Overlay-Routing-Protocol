package cs.tcd.ie;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	
	public static void client(String[] args) {
		
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

		size = 9;
		
		// Routers connected to one another
		routers.get(0).addConnectedRouter(routers.get(1));	// Connect Router A to B
		routers.get(0).addConnectedRouter(routers.get(2));	// Connect Router A to C
		routers.get(1).addConnectedRouter(routers.get(5));	// Connect Router B to F
		routers.get(1).addConnectedRouter(routers.get(3));	// Connect Router B to D
		routers.get(2).addConnectedRouter(routers.get(3));	// Connect Router C to B
		routers.get(2).addConnectedRouter(routers.get(4));	// Connect Router C to B
		routers.get(3).addConnectedRouter(routers.get(4));	// Connect Router D to B
		routers.get(3).addConnectedRouter(routers.get(6));	// Connect Router D to B
		routers.get(4).addConnectedRouter(routers.get(7));	// Connect Router E to B
		routers.get(4).addConnectedRouter(routers.get(9));	// Connect Router E to B
		routers.get(5).addConnectedRouter(routers.get(1));	// Connect Router F to B
		routers.get(6).addConnectedRouter(routers.get(8));	// Connect Router G to B
		routers.get(7).addConnectedRouter(routers.get(9));	// Connect Router H to B
		
		//Graph<Router> graph = new Graph<Router>();
		
		routers.get(0).ping();
		
		System.out.println("Please enter the users to send a string from and to, followed by a message. Each part should be seperated by a colan, ':'.");
		Scanner sc  = new Scanner(System.in);
		while(sc.hasNext()) {
			String line = sc.next();
			String[] data = line.split(":");
			if(data[0].equals("Aaron") || data[0].equals("Able"))	{
				routers.get(0).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Ben")) {
				routers.get(1).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Carl") || data[0].equals("Carla") || data[0].equals("Cian")) {
				routers.get(2).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Dillon") || data[0].equals("Denise")) {
				routers.get(3).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Earl") || data[0].equals("Emma")) {
				routers.get(4).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Fiona")) {
				routers.get(5).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Gloria") || data[0].equals("Gabrial")) {
				routers.get(6).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Harry") || data[0].equals("Hellen")) {
				routers.get(7).sendMessage(data[1], data[2]);
			}	else if(data[0].equals("Ian")) {
				routers.get(8).sendMessage(data[1], data[2]);
			}
		}
	}
}