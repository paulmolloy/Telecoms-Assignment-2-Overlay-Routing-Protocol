
public class Video {

	int size, ID, numRequests;
	
	public Video(int ID, int size) {
		this.size = size;
		this.ID = ID;
		this.numRequests=0;
	}
	public Video(int ID, int size, int numRequests) {
		this.size = size;
		this.ID = ID;
		this.numRequests=numRequests;
	}
	
	public int getNumRequests(){
		return numRequests;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getID() {
		return ID;
	}
	
}
