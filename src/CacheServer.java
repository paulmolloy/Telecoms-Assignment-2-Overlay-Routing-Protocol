import java.util.ArrayList;

public class CacheServer {
	ArrayList<ArrayList<Integer>> endPointIDs;
	ArrayList<Integer> vidID;
	int size;
	
	public CacheServer(int size) {
		endPointIDs = new ArrayList<ArrayList<Integer>>();
		vidID = new ArrayList<Integer>();
		this.size = size;
	}
	
	public void setEndPoint(int endPointID, int latency) {
		ArrayList<Integer> endPoint = new ArrayList<Integer>();
		endPoint.add(endPointID);
		endPoint.add(latency);
		endPointIDs.add(endPoint);
	}
	
	public void addVidID(Video vid) {
		vidID.add(vid.getID());
		this.size = this.size - vid.getSize();
	}
	
	public int sizeLeft() {
		return size;
	}
	
	public Integer getLatency(int endPointLatency) {
		for(int i = 0; i < endPointIDs.size(); i++) {
			ArrayList<Integer> tmp = endPointIDs.get(i);
			if(tmp.get(0).equals(endPointLatency)) {
				return tmp.get(1);
			}
		}
		return null;
	}
	
	public boolean containsVid(int ID) {
		if(vidID.contains(ID)) {
			return true;
		}	else	{
			return false;
		}
	}
	
	public boolean isCacheUsed() {
		if(vidID.isEmpty()) {
			return false;
		}	else	{
			return true;
		}
	}
	
}
