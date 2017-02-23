import java.util.HashMap;

public class Endpoint {
	
	private int l;
	private int k;
	int[] cacheIds;
	HashMap<Integer, Integer> videosPop;//videoid->num requests
	
	
	public int getl(){
		return l;	
	}
	public int getk(){
		return k;	
	}
	
	public int setl(int l){
		return l;
	}
	
	public int setk(int l){
		return k;
	}
	
	
	public Endpoint(int l, int k){
		this.l=l;
		this.k=k;
		videosPop = new HashMap<Integer,Integer>();
		
	}
	public void addVideoRequest(int vidId, int numRequests){
		if(videosPop.containsValue(vidId)){
			videosPop.put((Integer)vidId, videosPop.get(vidId)+ new Integer( numRequests));
			
		}else{
			videosPop.put((Integer)vidId, new Integer( numRequests));
			
		}
		
	}
	
	
	

	//public class  
}
