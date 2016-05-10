package graphGenerate;

import java.io.*;
import java.util.*;


/**
 * Generate Bipartite Graph
 * @author Sisi Wang
 *
 * Dec 3, 2015
 */
public class BipartiteGraph
{
	
	/**
	 * @param n number of nodes on source side
	 * @param m number of nodes on sink side
	 * @param minCapacity
	 * @param maxCapacity
	 * @param maxProbability
	 * @param fileName 
	 */
	public void generateGraph(int n, int m, int minCapacity, int maxCapacity, double maxProbability, String fileName){
		int i,j;
		double value, x;
		if(maxProbability > 1)
			{
				System.out.println("Max probability should be less than or equal to 1");
				return;
			}
		String directory = System.getProperty("/");
		try
		{
			PrintWriter outFile = new  PrintWriter(new FileWriter(new File(directory, fileName)));

			double[][] edge = new double[n][m];
			for(i=0; i<n; i++)
			{
				for(j=0; j<m; j++)
				{
					value = Math.random();
					if(value <= maxProbability)
						edge[i][j] = value;
					else
						edge[i][j] = 0;
				}
			}
//			
//			System.out.println("-----------------------------------------");
//			System.out.println("\tSource\tSink\tCapacity");
//			System.out.println("-----------------------------------------");			

			//computing the edges out of source
			for (i = 0; i < n; i++)
			{
				x=Math.random();
				//Compute a capacity in range of [minCapacity, maxCapacity]
				value = Math.floor(minCapacity + (x * (maxCapacity - minCapacity + 1)));
//				System.out.println("\t" + "s" + "\tl" + (i + 1) + "\t" + (int)value);
				outFile.println("\t" + "s" + "\tl" + (i + 1) + "\t" + (int)value);
			}
			for(i=0; i<n; i++)
			{
				for(j=0; j<m; j++)
				{				
					if(edge[i][j] > 0)
					{
						edge[i][j] = Math.floor(minCapacity + (edge[i][j] * (maxCapacity - minCapacity + 1)));
//						System.out.println("\tl"+ (i+1) + "\tr" + (j+1) + "\t" + (int)edge[i][j]);
						//computing for the vertices between source and sink and writing them to the output file
						outFile.println("\tl"+ (i+1) + "\tr" + (j+1) + "\t" + (int)edge[i][j]);
					}
				}
			}
			//computing the edges into the sink
			for (j=0; j < m; j++)
			{
				x=Math.random();
				value = Math.floor(minCapacity + (x * (maxCapacity - minCapacity + 1)));
//				System.out.println("\tr" + (j+1) + "\t" + "t" + "\t" + (int)value);
				outFile.println("\tr" + (j + 1) + "\t" + "t" + "\t" + (int)value);
			}

			System.out.println("\n\nOutput is created at: \t" + directory + "\\" + fileName);
			outFile.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}

	//helper functions
	public static String GetString() throws IOException 
	{
		BufferedReader stringIn = new BufferedReader (new
			InputStreamReader(System.in));
		return  stringIn.readLine();
	}

	public static int GetInt() throws IOException 
	{
		String aux = GetString();
		return Integer.parseInt(aux);
	}

	public static double GetReal() throws IOException 
	{
		String  aux = GetString();
		Double d  = new Double(aux);
		return  d.doubleValue() ;
	}
	
///*for test*/	
//	
//public static void main(String[] args) throws Exception
//	{
//		BipartiteGraph bg = new BipartiteGraph();
//		
//		bg.generateGraph(50, 20, 0, 100, 0.6, "g.txt");
//	}
}