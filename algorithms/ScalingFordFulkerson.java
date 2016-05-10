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
public final class ScalingFordFulkerson 
{
    public static double maxFlow;
    public static ResidualGraph gResidual;
    public static double ScalingFordFulkerson(SimpleGraph g)
    {
        //contrutc residual
        gResidual = new ResidualGraph();
        addVerticesToResidual(g);
        maxFlow = 0;
        //max edge out of source
        double maxSource = findMaxSourceCapacity(g);
        //find delta based on max value
        int delta = calculateDelta(maxSource);
        //while(delta>=1)
        while(delta >= 1)
        {
            //System.out.println("Delta = "+delta);
            //initialize residual graph
            addEdgesToResidual(g,delta);
            
            //find st path
            //printResidualGraph();
            LinkedList<ResidualVertex> currentPath;
            while(!(currentPath = findPath()).isEmpty())
            {
                //System.out.println("Inside while");
                //bottleneck
                double bottleneck = findBottleneck(currentPath);
                //push
                pushFlow(currentPath, bottleneck);
                maxFlow += bottleneck;
                //System.out.println("Updated maxFlow = "+maxFlow);
            }

            //delta = delta/2
            delta = delta / 2;
            //System.out.println("--------------------");
        }
        
        //printResidualGraph();
        return maxFlow;
    }
    private static void pushFlow(LinkedList<ResidualVertex> stPath, double bottleneck) 
    {
        for(int i=0; i<stPath.size()-1; i++)
        {
            ResidualVertex rv = stPath.get(i);
            ResidualVertex rw = stPath.get(i+1);
            ResidualEdge forward = gResidual.getEdge(rv, rw);
            if(forward.getCapacity() == bottleneck)
            {
                gResidual.removeEdge(forward);
                //System.out.println("Removing edge "+forward.getFirstEndpoint().getName()+"-"+forward.getSecondEndpoint().getName());
            }
            else
            {
                forward.setCapacity(forward.getCapacity() - bottleneck);
                //System.out.println("Updating edge "+forward.getFirstEndpoint().getName()+"-"+forward.getSecondEndpoint().getName()+" to "+forward.getCapacity());
                gResidual.updateEdge(forward);
            }
            
            ResidualEdge backward = gResidual.getEdge(rw, rv);
            if(backward == null)
            {
                backward = new ResidualEdge(rw,rv,bottleneck);
                gResidual.insertEdge(rw, rv, backward);
                //gResidual.updateEdge(backward);
                //System.out.println("Inserting edge "+backward.getFirstEndpoint().getName()+"-"+backward.getSecondEndpoint().getName()+" with "+bottleneck);
            }
            else
            {
                backward.setCapacity(backward.getCapacity() + bottleneck);
                //System.out.println("Updating edge "+backward.getFirstEndpoint().getName()+"-"+backward.getSecondEndpoint().getName()+" to capacity "+backward.getCapacity());
                gResidual.updateEdge(backward);
            }
        }
    }
    private static double findBottleneck(LinkedList<ResidualVertex> path) 
    {
        double bottleneck = 100000;
        //System.out.println("Path size "+path.size());
        for(int i=0; i<path.size()-1; i++)
        {
            ResidualVertex rv = path.get(i);
            ResidualVertex rw = path.get(i+1);
            ResidualEdge re = gResidual.getEdge(rv, rw);
            //System.out.println("re "+re.getCapacity());
            //System.out.println("Get capacity for "+rv.getName()+"-"+rw.getName());
            double canPush = re.getCapacity();
            //System.out.println(canPush);
            if(bottleneck >= canPush)
            {
                bottleneck = canPush;
            }
        }
        //System.out.println("Bottleneck = "+bottleneck);
        return bottleneck;
    }

    private static LinkedList<ResidualVertex> findPath() 
    {
        LinkedList<ResidualVertex> path = new LinkedList<ResidualVertex>();
        ResidualVertex s = gResidual.getSource();
        path = gResidual.depthFirstSearch(s, path);
        if(!path.isEmpty())
        {
            for(int i=0; i<path.size();i++)
            {
                //System.out.print(path.get(i).getName()+" ");
            }
        }
        else
        {
            //System.out.println("No path found.");
            path.clear();
        }
        //System.out.println("");
        //System.out.println("Changing all visited to unvisited");
        updateVertexVisited();
        s.unvisit();
        gResidual.updateVertex(s);
        //System.out.println(gResidual.getSource().isVisited());
        return path;
    }
/*
    public static void updateVertexVisited() 
    {
        Iterator vertices = gResidual.vertices();
        while(vertices.hasNext())
        {
            ResidualVertex v = (ResidualVertex) vertices.next();
            if(v.isVisited())
            {
                v.unvisit();
            }
            gResidual.updateVertex(v);
        }
    }*/
    
    private static void addEdgesToResidual(SimpleGraph g, int delta) 
    {
        Iterator edges = g.edges();
        while(edges.hasNext())
        {
            Edge e = (Edge) edges.next();
            if((double)e.getData() >= delta && (double)e.getData() < delta*2)
            {
                ResidualVertex rv = null;
                ResidualVertex rw = null;
                Vertex fVertex = e.getFirstEndpoint();
                Vertex sVertex = e.getSecondEndpoint();
                Iterator vertices = gResidual.vertices();
                while(vertices.hasNext())
                {
                    ResidualVertex v= (ResidualVertex) vertices.next();
                    if(v.getName().equalsIgnoreCase(fVertex.getName().toString()))
                    {
                        rv=v;
                    }
                    if(v.getName().equalsIgnoreCase(sVertex.getName().toString()))
                    {
                        rw=v;
                    }
                }
                double edgeCapacity = (double) e.getData();
                ResidualEdge re = new ResidualEdge(rv,rw, edgeCapacity);
                gResidual.insertEdge(rv, rw, re);
//                System.out.println(re.getCapacity());
                
                //System.out.println(gResidual.getEdge(rv, rw).getCapacity());
            }
        }
    }
    private static int calculateDelta(double maxSource) 
    {
        int i = 1;
        for(; i<maxSource; i=i*2){}
        //i = i/2;
        return i;
    }
    private static double findMaxSourceCapacity(SimpleGraph g) 
    {
        Iterator vertices = g.vertices();
        double maximum = 0;
        while(vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();
            if(v.getName().toString().equalsIgnoreCase("s"))
            {
                LinkedList edges = v.incidentEdgeList;
                for(int i = 0; i<edges.size(); i++)
                {
                    Edge e = (Edge) edges.get(i);
                    if(maximum < (double) e.getData())
                    {
                        maximum = (double) e.getData();
                    }
                }
            }
        }
        return maximum;
    }
    private static void addVerticesToResidual(SimpleGraph g) 
    {
        // TODO Auto-generated method stub
        Iterator vertices = g.vertices();
        while(vertices.hasNext())
        {
                Vertex v = (Vertex) vertices.next();
                ResidualVertex rv = new ResidualVertex(v.getName());
                gResidual.insertVertex(rv);
        }
    }
    private static void printResidualGraph() 
    {
        Iterator i;
        ResidualVertex v;
        ResidualEdge e;
		 
        System.out.println("Iterating through vertices...");
        for (i= gResidual.vertices(); i.hasNext(); ) 
        {
            v = (ResidualVertex) i.next();
            System.out.println("Vertex " + v.getName() + " : visited = "+v.isVisited());
        }

        System.out.println("Iterating through adjacency lists...");
        for (i= gResidual.vertices(); i.hasNext(); ) 
        {
            v = (ResidualVertex) i.next();
            System.out.println("Vertex "+v.getName());
            Iterator j;

            for (j = gResidual.outgoingEdges(v); j.hasNext();) 
            {
                e = (ResidualEdge) j.next();
                System.out.println("\tEdge to "+gResidual.opposite(v, e).getName()+"; capacity = " + e.getCapacity() + ", flow = "+e.getFlow());
            }
        }
    }
    public static void updateVertexVisited() 
    {
        Iterator i;
        for (i= gResidual.vertices(); i.hasNext(); ) 
        {
            ResidualVertex v = (ResidualVertex) i.next();
            if(v.isVisited())
            {
                v.unvisit();
                //System.out.println(v.getName()+" - unvisited. Current visited state = "+v.isVisited());
            }
            else
            {
                //System.out.println(v.getName()+" - Not visited anyway. Current visited state = "+v.isVisited());
            }
        }
    }
}


