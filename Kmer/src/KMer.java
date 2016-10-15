/**
 * Assume N --> number of rows
 * 		  k --> user input k value
 * 
 * Search Speed Complexity =  O(log N)               This is because the nature of TreeMap 
 * 
 * Space Complexity <= O((N-k+1)*N) = O(N^2)         Each row can generate N-k+1 subset or less (since I'm not considering duplicate case)
 * 													 And we have N numbers of rows therefore the complexity should be product of those value
 * 
 * Yes it uses way less than 8G of ram               It uses less than 512M
 * 
 * Search Engine building speed = O(N^2) * O (Log N) Since this program will go though all the possible kmer string O(N^2), and insert to TreeMap 
 *                              = O(N^2 * Log N)     Insertion in TreeMap is also O(log N), the the overall speed is product of those two value
 * 
 * */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;


public class KMer {

		public static void main(String[] args)
		{
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter the k value: ");
			int k= Integer.parseInt(scan.nextLine());
			System.out.println("Preparing Search Engine");
			TreeMap<String,Location> kmerMap = prepareSearchEngine(k);
			
//			for(String s : kmerMap.keySet())
//			{
//				System.out.println(s);
//			}
			String userInput="";
			while(!userInput.equals("q"))
			{
				System.out.println("Enter a search string");
				userInput=scan.nextLine();
				search(kmerMap,userInput);
			}
			

		}
		public static void search(TreeMap<String,Location> kmerMap,String s)
		{
				if(kmerMap.containsKey(s))
				{
					System.out.println(kmerMap.get(s));
				}
				else
				{
					System.out.println("No result");
				}
		}
		public static TreeMap<String,Location> prepareSearchEngine(int k)
		{
			Scanner scan;
			//kmer map act as a search engine database 
			//with insertion complexity O(log n)
			//integer value of index as key
			//HasMap of kmerString map will be value
			TreeMap<String,Location> kmerMap = new TreeMap<String,Location>();
			
			try {
				//scan = new Scanner(new File("C:\\Users\\b003271\\workspace\\Tong\\test.fa"));
				scan = new Scanner(new File("C:\\Users\\b003271\\Desktop\\solexa_100_170_1.fa"));
				
				int progress = 0;
				while(scan.hasNextLine())
				{
					//grab the inedex of this next mer
					Integer index=Integer.parseInt(scan.nextLine().split(" ")[2]);
					String kmerString = scan.nextLine();

					for(int i=0;i<kmerString.length()-k+1;i++)
					{
						//no adding duplicate
						String key = kmerString.substring(i, i+k);
						//col index start with 1
						kmerMap.put(key, new Location(index,(i+1)));				
					}
					
					progress++;
					if(progress%100000==0)
					{
						System.out.println("Current progress : "+progress+" out of 1,000,000 has been inserted");
					}
				}
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return kmerMap;
		}
		
		
		private static class Location{
			public int row;
			public int col;
			
			public Location(int r, int c)
			{
				row=r;
				col=c;
			}
			public String toString()
			{
				return "{"+row+","+col+"}";
			}
		}
}
