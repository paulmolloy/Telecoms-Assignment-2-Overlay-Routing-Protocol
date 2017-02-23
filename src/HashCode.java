import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HashCode {
	

	
	public static void main(String[] args){
		
		//Scanner in = new Scanner(new FileReader("filename.txt"));
		getInput();

	}
	
	private static void getInput(){
		
		try {
			
			Scanner in = new Scanner(new FileReader("kittens.in"));
			int v, e,r,c,x;
			
			v= in.nextInt();
			e= in.nextInt();
			r= in.nextInt();
			c= in.nextInt();
			x= in.nextInt();
			
			System.out.println(v);
			int[] videos = new int[v];
			for(int i=0;i< v;i++){
				videos[i] = in.nextInt();
				
			}
			

			
			Endpoint[] endpoints = new Endpoint[e]; 
			for(int i=0;i< e;i++){
				int l, k;
				endpoints[i] = new Endpoint(in.nextInt(), in.nextInt());
				for(int j=0; i<endpoints[i].getk();j++){
					
				}
			}
			

			int cEndpointId, cVideoId, cNumRequests;
			for(int i=0;i< r;i++){
				cNumRequests = in.nextInt();
				cVideoId = in.nextInt();
				cEndpointId = in.nextInt();
				endpoints[cEndpointId].addVideoRequest(cVideoId, cNumRequests);
			}
//			while(in.hasNext()){
//				
//				System.out.println(v + x);
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void outputFile(CacheServer cacheServers[]) throws IOException{
			
		File file = new File("output.txt");
		int numberOfCacheServers = 0;
		for(int i = 0; i < cacheServers.length; i++){
//			if(cacheServers[i].isUsed == true){
//				numberOfCacheServers++;
//			}
		}
		
		file.createNewFile();
		FileWriter writer = new FileWriter(file); 
		writer.write(numberOfCacheServers + "\n");
		
		for(int i = 0; i < cacheServers.length; i++){
			//if(cacheServers[i].isUsed == true){
				writer.write(cacheServers[i] + " ");
//				for(int k = 0; k < cacheServers[i].vidID.length; k++){
//					writer.write(cacheServers[i].vidID[i] + " ");
//				}
				writer.write("\n");
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
