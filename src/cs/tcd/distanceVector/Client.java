package cs.tcd.distanceVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {

		int size = 0;
		ArrayList<Router> routers = new ArrayList<Router>();
		Router router;
		String user1;
		String user2;
		String user3;
		Hashtable<String,Integer> userRouterHT = new Hashtable<String,Integer>(); //maps user to pos of router in arraylist
		
		ArrayList<User> usersA = new ArrayList<User>();
		user1 = "Aaron";
		user2 = "Able";
		usersA.add(new User(user1));
		usersA.add(new User(user2));
		router = new Router(usersA, "A", 50000);
		routers.add(router);
		userRouterHT.put(user1, 0);
		userRouterHT.put(user2, 0);

		ArrayList<User> usersB = new ArrayList<User>();
		user1 = "Ben";
		usersB.add(new User(user1));
		router = new Router(usersB, "B", 50001);
		routers.add(router);
		userRouterHT.put(user1, 1);

		ArrayList<User> usersC = new ArrayList<User>();
		user1 = "Carl";
		user2 = "Carla";
		user3 = "Cian";
		usersC.add(new User(user1));
		usersC.add(new User(user2));
		usersC.add(new User(user3));
		router = new Router(usersC, "C", 50002);
		routers.add(router);
		userRouterHT.put(user1, 2);
		userRouterHT.put(user2, 2);
		userRouterHT.put(user3, 2);
		

		ArrayList<User> usersD = new ArrayList<User>();
		user1 = "Dillon";
		user2 = "Denise";
		usersD.add(new User(user1));
		usersD.add(new User(user2));
		router = new Router(usersD, "D", 50003);
		routers.add(router);
		userRouterHT.put(user1, 3);
		userRouterHT.put(user2, 3);
		


		ArrayList<User> usersE = new ArrayList<User>();
		user1 = "Earl";
		user2 = "Emma";
		usersE.add(new User(user1));
		usersE.add(new User(user2));
		router = new Router(usersE, "E", 50004);
		routers.add(router);
		userRouterHT.put(user1, 4);
		userRouterHT.put(user2, 4);
		


		ArrayList<User> usersF = new ArrayList<User>();
		user1 = "Fiona";
		usersF.add(new User(user1));
		router = new Router(usersF, "F", 50005);
		routers.add(router);
		userRouterHT.put(user1, 5);


		ArrayList<User> usersG = new ArrayList<User>();
		user1 = "Gloria";
		user2 = "Gabrial";
		usersG.add(new User(user1));
		usersG.add(new User(user2));
		router = new Router(usersG, "G", 50006);
		routers.add(router);
		userRouterHT.put(user1, 6);
		userRouterHT.put(user2, 6);
		

		ArrayList<User> usersH = new ArrayList<User>();
		user1 = "Harry";
		user2 = "Hellen";
		usersH.add(new User(user1));
		usersH.add(new User(user2));
		router = new Router(usersH, "H", 50007);
		routers.add(router);
		userRouterHT.put(user1, 7);
		userRouterHT.put(user2, 7);


		ArrayList<User> usersI = new ArrayList<User>();
		user1 = "Ian";
		usersI.add(new User(user1));
		router = new Router(usersI, "I", 50008);
		routers.add(router);
		userRouterHT.put(user1, 8);

		ArrayList<User> usersJ = new ArrayList<User>();
		user1 = "Jill";
		user2 = "Joe";
		usersJ.add(new User(user1));
		usersJ.add(new User(user2));
		router = new Router(usersH, "J", 50009);
		routers.add(router);
		userRouterHT.put(user1, 9);
		userRouterHT.put(user2, 9);


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
		System.out.println("To send a file write in the form 'FILE:userFrom:userTO:fileName");Scanner sc  = new Scanner(System.in);
		while(sc.hasNext()) {
			String line = sc.nextLine();
			String[] data = line.split(":");
			if(data[0].equals("FILE") &&data.length==4){
				
				String fName = data[3];
				File file= new File(fName);	
				byte[]buffer= new byte[(int) file.length()];
				FileInputStream fin;
				
				try {
					fin = new FileInputStream(file);
					size= fin.read(buffer);
					if (size==-1) {
						fin.close();
						
					}
					Path path = Paths.get(fName);
					byte[] fData = Files.readAllBytes(path);
					Message fileMessage= new Message(data[1] + "," + data[2] + "," + fName , fData);
					System.out.println("Sending file:" +fName ); // Send packet with file name and length
					routers.get(userRouterHT.get(data[1])).sendMessage(fileMessage);
					
					fin.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			}else{
				routers.get(userRouterHT.get(data[0])).sendMessage(new Message(data[0] + "," + data[1] + "," + data[2]));	
				

			}
			System.out.println("Message Received");
		}
		sc.close();
	}
}