package graphGenerate;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

/**
 * @author Sisi Wang
 *
 * Dec 3, 2015
 */
public class FixedDegreeGenerator {
	
	private static final String NL = "\n";
	
	
	/**
	 * @param v - the number of vertices
	 * @param e - the number of edges leaving each node
	 * @param minCapacities
	 * @param minCapacity
	 * @param f
	 */
	public void generateGraph(int v, int e, int minCapacity, int maxCapacity, String f){
		toFile(graphBuilder(v,e,minCapacity,maxCapacity), f);
	}

	/**
	 * This method creates a 3 token representation of a graph.
	 * @param v The number of vertices in the graph
	 * @param e The number of edges leaving each vertice
	 * @param min The lowerbound on the capacity value of each edge
	 * @param max The upperbound on the capacity value of each edge
	 * @return A string buffer, each line contains 3 tokens corresponding 
	 *			to a directed edge: the tail, the head, and the capacity.
	 */
	public static StringBuffer graphBuilder(int v, int e, int min, int max){
		int i;
		int j;
		int head;
		int c;
		SortedSet s;
		Random gen = new Random();
		StringBuffer bfr = new StringBuffer();
		
		//Add distinguished node s
		j = 1;
		s = new TreeSet();
		while(j <= e){
			head = gen.nextInt(v) + 1;
			if(!s.contains(head)){
				s.add(head);
				c = min + gen.nextInt(max - min + 1);
				bfr.append("s"+" "+"v"+head+" "+c+NL);	
				j++;
			}
		}
		
		//Add distinguished node t
		j = 1;
		s = new TreeSet();
		while(j <= e){
			int tail = gen.nextInt(v) + 1;
			if(!s.contains(tail)){
				s.add(tail);
				c = min + gen.nextInt(max - min + 1);
				bfr.append("v"+tail+" "+"t"+" "+c+NL);
				j++;
			}
		}
		
		//Add internal nodes
		for(i = 1; i <= v; i++){
			s = new TreeSet();
			s.add(i);
			j = 1;
			while(j <= e){
				head = gen.nextInt(v) + 1;
				if(!s.contains(head)){
					s.add(head);
					c = min + gen.nextInt(max - min + 1);
					bfr.append("v"+i+" "+"v"+head+" "+c+NL);
					j++;
				}
			}
		}
		return bfr;
	}
	

	/**
	 * This method attempts to save a string at a given location.
	 * @param outString The StringBuffer containing the data being saved
	 * @param filename The complete file path including file name
	 */
	private static void toFile(StringBuffer outString, String filename){
		try{
			BufferedWriter fout = new BufferedWriter(new FileWriter(filename));
			fout.write(outString.toString());
			fout.close();
		}catch(Exception e){
			System.out.println("Error saving file.");
			System.out.println("Please check file paths and restart this program.");
			System.exit(1);
		} 
	}
	
//	/*for testing*/
//	public static void main(String[] args){
//		FixedDegreeGenerator fg = new FixedDegreeGenerator();
//		fg.generateGraph(20, 3, 0,100, "g1.txt");
//	}
	
}
