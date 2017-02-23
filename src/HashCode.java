import java.io.FileNotFoundException;
import java.io.FileReader;
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
			for(int i=0;i< v;i++){
				int l, k;

				//endpoints[i].setl(in.nextInt());
				//endpoints[i].setk(in.nextInt());

				
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
}
