package cs.tcd.linkState;

import java.io.Serializable;

public class User implements Serializable {

	private String userName;

	public User(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return userName;
	}

}
