package graphGenerate;

import java.util.LinkedList;

import graph.GraphInput;
import graph.SimpleGraph;

/**
 * @author Sisi Wang
 *
 * Dec 5, 2015
 */
public class GraphGenerate {
	
	
	private static final String FILENAME = "g.txt";
	private static final int NUMOFNODE = 100;
	private static final int MINCAPACITY = 1;
	private static final int MAXCAPACITY = 50;
	private static final int DEGREE = 3;
	private static final double PROBABILITY = 0.5;
	
	public static SimpleGraph[] sg;
 	
	
//	/*for generating testing graph
//	 * */
//	public static void main(String[] args) {
////        RandomGenerator rg = new RandomGenerator();
////        rg.BuildGraph("lowDensity.txt", ".", 100, 5, 1, 50);
//		String[] arr = {String.valueOf(10),String.valueOf(10),String.valueOf(MAXCAPACITY),"meshTest.txt"};
//		MeshGenerator mg = new MeshGenerator(arr);
//		mg.generate();
//        
//	}
	
	
	
    /**
     * generate graphs with different number of vertices
     * @param size - number of graphs to be generated
     * @param type - graph type
     * @return
     */
    public static SimpleGraph[] GraphsNumNodes(int size, String type) {
    	
    	sg = new SimpleGraph[size]; 
        GraphInput graphInput = new GraphInput();
        
        switch (type.toLowerCase()){
        
		case "bipartite":		
			for(int i =1;i<=size;i++){
				SimpleGraph bip = new SimpleGraph();
				int n = i*5;
		    	BipartiteGraph bg = new BipartiteGraph();
				bg.generateGraph(n, n, MINCAPACITY, MAXCAPACITY, PROBABILITY, FILENAME);
				graphInput.LoadSimpleGraph(bip,FILENAME);
				sg[i-1] = bip;
			}
			System.out.println("bipartite");
			break;
		case "fixeddegree":
			for(int i =1;i<=size;i++){
				SimpleGraph fix = new SimpleGraph();
				FixedDegreeGenerator fdg = new FixedDegreeGenerator();
				fdg.generateGraph(i*10, DEGREE, MINCAPACITY, MAXCAPACITY, FILENAME);
				graphInput.LoadSimpleGraph(fix,FILENAME);
				sg[i-1] = fix;
			}
			System.out.println("fixed");
			break;
		case "mesh":
			for(int i =1;i<=size;i++){
				SimpleGraph mesh = new SimpleGraph();
				String[] arr = {String.valueOf(i),String.valueOf(i),String.valueOf(MAXCAPACITY),FILENAME};
				MeshGenerator mg = new MeshGenerator(arr);
				mg.generate();
				graphInput.LoadSimpleGraph(mesh,FILENAME);
				sg[i-1] = mesh;
			}
			System.out.println("mesh");
			break;

		case "random":
			for(int i =1;i<=size;i++){
				SimpleGraph ran = new SimpleGraph();
				RandomGenerator rg = new RandomGenerator();
				rg.BuildGraph(FILENAME, ".", i*10, PROBABILITY*100, MINCAPACITY, MAXCAPACITY);
				graphInput.LoadSimpleGraph(ran,FILENAME);
				
//				System.out.println(ran.numVertices()+"--"+ran.numEdges());
				
				sg[i-1] = ran;
//				System.out.println(sg[i-1].numVertices()+"--"+sg[i-1].numEdges());
				
			}
//			for(int i =0; i<sg.length;i++)
//	        {
//	        	System.out.println(sg[i].numVertices()+"--"+sg[i].numEdges());
//
//	        }
			System.out.println("random");
			break;
		default:
			System.out.println("Invalid type.");
			break;
		}
//        for(int i =0; i<sg.length;i++)
//        {
//        	System.out.println(sg[i].numVertices()+"--"+sg[i].numEdges());
//        }
        
		return sg;
        
			
	}
    
    /**
     * generate graphs with different probability/density/degree
     * @param size - number of graphs to be generated
     * @param type - graph type
     * @return
     */
    public static SimpleGraph[] GraphsPro(int size, String type) {

    	SimpleGraph[] sg = new SimpleGraph[size]; 
        GraphInput graphInput = new GraphInput();
        
        switch (type.toLowerCase()){
		case "bipartite":
			for(int i =1;i<=size;i++){
				SimpleGraph bip = new SimpleGraph();
		    	BipartiteGraph bg = new BipartiteGraph();
				bg.generateGraph(NUMOFNODE/2, NUMOFNODE/2, MINCAPACITY, MAXCAPACITY, (100/size)*0.01*i, FILENAME);
				graphInput.LoadSimpleGraph(bip,FILENAME);
				sg[i-1] = bip;
			}
			System.out.println("bipartite");
			break;
		case "fixeddegree":
			for(int i =1;i<=size;i++){
				SimpleGraph fix = new SimpleGraph();
				FixedDegreeGenerator fdg = new FixedDegreeGenerator();
				fdg.generateGraph(NUMOFNODE, i, MINCAPACITY, MAXCAPACITY, FILENAME);
				graphInput.LoadSimpleGraph(fix,FILENAME);
				sg[i-1] = fix;
			}
			System.out.println("fixed");
			break;
		case "random":
			for(int i =1;i<=size;i++){
				SimpleGraph ran = new SimpleGraph();
				RandomGenerator rg = new RandomGenerator();
				rg.BuildGraph(FILENAME, ".", NUMOFNODE, (100/size)*i, MINCAPACITY, MAXCAPACITY);
				graphInput.LoadSimpleGraph(ran,FILENAME);
				sg[i-1] = ran;
			}
			System.out.println("random");
			break;
		default:
			System.out.println("Invalid type.");
			break;
		}
		return sg;		
	}
    
    /**
     * generate graphs with different Maximum Capacity
     * @param size - number of graphs to be generated
     * @param type - graph type
     * @return
     */
    public static SimpleGraph[] GraphsRange(int size, String type) {
    	SimpleGraph[] sg = new SimpleGraph[size]; 
        GraphInput graphInput = new GraphInput();
        
        switch (type.toLowerCase()){
		case "bipartite":
			for(int i =1;i<=size;i++){
				SimpleGraph bip = new SimpleGraph();
		    	BipartiteGraph bg = new BipartiteGraph();
				bg.generateGraph(NUMOFNODE/2, NUMOFNODE/2, MAXCAPACITY, i*5, PROBABILITY, FILENAME);
				graphInput.LoadSimpleGraph(bip,FILENAME);
				sg[i-1] = bip;
			}
			System.out.println("bipartite");
			break;
		case "fixeddegree":
			for(int i =1;i<=size;i++){
				SimpleGraph fix = new SimpleGraph();
				FixedDegreeGenerator fdg = new FixedDegreeGenerator();
				fdg.generateGraph(NUMOFNODE, DEGREE, MINCAPACITY, i*5, FILENAME);
				graphInput.LoadSimpleGraph(fix,FILENAME);
				sg[i-1] = fix;
			}
			System.out.println("fixed");
			break;
		case "mesh":
			for(int i =1;i<=size;i++){
				SimpleGraph mesh = new SimpleGraph();
				String[] arr = {String.valueOf(10),String.valueOf(10),String.valueOf(i*5),FILENAME};
				MeshGenerator mg = new MeshGenerator(arr);
				mg.generate();
				graphInput.LoadSimpleGraph(mesh,FILENAME);
				sg[i-1] = mesh;
			}
			System.out.println("mesh");
			break;
		case "random":
			for(int i =1;i<=size;i++){
				SimpleGraph ran = new SimpleGraph();
				RandomGenerator rg = new RandomGenerator();
				rg.BuildGraph(FILENAME, ".", NUMOFNODE, PROBABILITY*100, MINCAPACITY, i*5);
				graphInput.LoadSimpleGraph(ran,FILENAME);
				sg[i-1] = ran;
			}
			System.out.println("random");
			break;
		default:
			System.out.println("Invalid type.");
			break;
		}
		return sg;		
	}
}
