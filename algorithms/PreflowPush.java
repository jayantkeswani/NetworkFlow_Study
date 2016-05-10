/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import graph.Edge;
import graph.SimpleGraph;
import graph.Vertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import residualgraph.ResidualEdge;
import residualgraph.ResidualGraph;
import residualgraph.ResidualVertex;

/**
 *
 * @author sumanaravikrishnan
 */
public class PreflowPush 
{
    private static ResidualGraph gResidual;
    static double maxFlow;
    
    /**
     *  Constructor to find the max-flow of a graph g
     *  @param g     graph to find max-flow for
     */
    public static double PreflowPush(SimpleGraph g)
    {
        gResidual = new ResidualGraph();
    	maxFlow = 0;
        calculateMaxFlow(g);
        return maxFlow;
    }
    
    /**
     *  Calculate max-flow of a given graph g
     *  @param g     graph to find max-flow for
     */
    private static void calculateMaxFlow(SimpleGraph g)
    {
        initializeResidualGraph(g);
        ResidualVertex v;
        while((v = gResidual.getActiveNode()) != null)
        {
            //ResidualVertex v = gResidual.getActiveNode();
            //System.out.println("Selecting active node "+v.getName()+" with excess "+v.getExcess());
            ResidualEdge e = getMinimumHeightAdjacentVertex(v);
            if(e != null)
            {
                //System.out.println("Choosing edge "+e.getFirstEndpoint().getName()+"-"+e.getSecondEndpoint().getName());
                push(v,e);
            }
            else
            {
                //System.out.println("No adjacent vertex with lesser height");
                relabel(v);
            }
            //System.out.println("----------");
        }
        //System.out.println("No more active nodes found");
        maxFlow = gResidual.getExcessAtSink();
    }
    private static ResidualEdge getMinimumHeightAdjacentVertex(ResidualVertex v)
    {
        LinkedList edges = v.outgoingEdge;
        ResidualVertex w;
        ResidualEdge fe = null;
        double minimumHeight = v.getHeight();
        //System.out.println("Adjacent Edges to "+v.getName()+", height = "+v.getHeight());
        for (Object edge : edges) 
        {
            ResidualEdge e = (ResidualEdge) edge;
            w = e.getSecondEndpoint();
            if(minimumHeight > w.getHeight())
            {
                //System.out.println(w.getName()+"; height = "+w.getHeight());
                minimumHeight = w.getHeight();
                fe = e;
            }
        }
        return fe;
    }
    
    /**
     *  Push flow between from a vertex along an edge
     *  @param v     Vertex 1
     *  @param forward     Outgoing edge from v
     */
    private static void push(ResidualVertex v, ResidualEdge forward)
    {
        ResidualVertex w = forward.getSecondEndpoint();
        double excessAvailable = v.getExcess();//77
        double canPush = forward.getCapacity();//40
        //System.out.println("Excess at "+v.getName()+" = "+excessAvailable);
        //System.out.println(canPush+" can be pushed on "+v.getName()+"-"+w.getName());
        double pushValue = (excessAvailable < canPush) ? excessAvailable : canPush;//40
        //System.out.println("Going to push "+pushValue);

        //Handle the forward edge
        if(pushValue == canPush)
        {
            //System.out.println("Remove "+v.getName()+"-"+w.getName());
            gResidual.removeEdge(forward);
        }
        else
        {
            forward.setCapacity(forward.getCapacity() - pushValue);
            //System.out.println("Update "+v.getName()+"-"+w.getName()+" to "+forward.getCapacity());
            gResidual.updateEdge(forward);
        }
        v.setExcess(v.getExcess() - pushValue);
        gResidual.updateVertex(v);
        //Handle the backward edge
        ResidualEdge backward = gResidual.getEdge(w, v);
        if(backward == null)
        {
            backward = new ResidualEdge(w,v,pushValue);
            //System.out.println("Insert "+w.getName()+"-"+v.getName()+" of capacity "+backward.getCapacity());
            gResidual.insertEdge(w, v, backward);
        }
        else
        {
            backward.setCapacity(backward.getCapacity() + pushValue);
            //System.out.println("Update "+w.getName()+"-"+v.getName()+" to capacity "+backward.getCapacity());
            gResidual.updateEdge(backward);
        }
        //backward.setFlow(backward.getCapacity() + pushValue);
        //forward.setFlow(forward.getFlow() - pushValue);
        
        w.setExcess(w.getExcess() + pushValue);
        gResidual.updateVertex(w);
        //System.out.println("Updating excess at "+v.getName()+" to "+v.getExcess());
        //System.out.println("Updating excess at "+w.getName()+" to "+w.getExcess());
        
        
    }
    
    /**
     *  Relabel height of a vertex.
     *  Sets the height of the vertex to 1 + minimum height of adjacent vertices
     *  @param v     Vertex to relabel
     */
    private  static void relabel(ResidualVertex v)
    {   
        LinkedList outgoingEdges = v.outgoingEdge;
        double minimumHeight = v.getHeight();
        for(int i=0; i<outgoingEdges.size();i++)
        {
            ResidualEdge e = (ResidualEdge) outgoingEdges.get(i);
            ResidualVertex w = e.getSecondEndpoint();
            //If there is an adjacent node with lesser height
            if(minimumHeight > w.getHeight())
            {
                minimumHeight = w.getHeight();
            }
        }
        
        v.setHeight(minimumHeight + 1);
        //System.out.println("Relable "+v.getName()+" to "+v.getHeight());
        gResidual.updateVertex(v);
    }
    
    /**
     *  Initialise the residual graph using a given graph g
     *  Initialise all vertex heights to be 0 except source. Source height is n. All vertex excess is 0.
     *  Initialise edges to have flow 0 and given capacity
     *  @param g     graph to find max-flow for
     */
    private static void initializeResidualGraph(SimpleGraph g)
    {
        Iterator vertices = g.vertices();
        HashMap<String,ResidualVertex> vertexList = new HashMap<String,ResidualVertex>();
        int noOfVertices = g.numVertices();
        while(vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();
            ResidualVertex rv = new ResidualVertex(v.getName());
            vertexList.put(String.valueOf(rv.getName()), rv);
            if(rv.getName().equalsIgnoreCase("s"))
            {
                
                rv.setHeight(noOfVertices);
                //System.out.println("Source Height = "+rv.getHeight());
            }
            else
            {
                rv.setHeight(0);
            }
            gResidual.insertVertex(rv);
        }
        Iterator edges = g.edges();
        while(edges.hasNext())
        {
            Edge e = (Edge) edges.next();
            Vertex v = e.getFirstEndpoint();
            Vertex w = e.getSecondEndpoint();
            ResidualVertex rv = vertexList.get(v.getName());
            ResidualVertex rw = vertexList.get(w.getName());
            double edgeCapacity = (double) e.getData();
            ResidualEdge edge;
            if(rv.getName().equalsIgnoreCase("s"))
            {
                edge = new ResidualEdge(rw, rv, edgeCapacity);
                rw.setExcess(edge.getCapacity());
                gResidual.updateVertex(rw);
                gResidual.insertEdge(rw, rv, edge);
            }
            else
            {
                edge = new ResidualEdge(rv, rw, edgeCapacity);
                gResidual.insertEdge(rv, rw, edge);
            }
        }
    }
}
