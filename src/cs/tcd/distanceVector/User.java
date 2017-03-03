package cs.tcd.distanceVector;

import java.io.Serializable;

/*
	User is a class used to hold a string. Symbolising a name of a user on the network
 */

public class User implements Serializable {

	private String userName;

	public User(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return userName;
	}

}
