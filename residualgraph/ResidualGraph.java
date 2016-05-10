/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package residualgraph;

import java.util.*;

/**
 * 
 * @author sumanaravikrishnan
 */


public class ResidualGraph 
{
    LinkedList vertexList; //List of vertices in residual graph
    LinkedList edgeList; //List of edges in residual graph

    // Constructor
    public ResidualGraph() 
    {
        this.vertexList = new LinkedList();
        this.edgeList = new LinkedList();
    }
    
    /**
     * Return the vertex list of this graph.
     * @returns  vertex list of this graph
     */
    public Iterator vertices() 
    {
        return vertexList.iterator();
    }

    /**
     * Return the edge list of this graph.
     * @return  edge list of this graph
     */
    public Iterator edges() 
    {
        return edgeList.iterator();
    }

    /**
     * Given a vertex, return an iterator to the edge list of that vertex
     * @param v  a vertex
     * @return  an iterator to the edge list of that vertex
     */
    public Iterator outgoingEdges(ResidualVertex v) 
    {
        return v.outgoingEdge.iterator();
    }

    /**
     * Return an arbitrary vertex of this graph
     * @return  some vertex of this graph
     */
    public ResidualVertex aVertex() 
    {
        if (vertexList.size() > 0)
            return (ResidualVertex) vertexList.getFirst();
        else
            return null;
    }
    
    /**
     * Add a vertex to this graph.
     * @param rv  a vertex in the residual graph
     */
    public void insertVertex(ResidualVertex rv) 
    {
        vertexList.addLast(rv);
    }

    
    /**
     * Add an edge to this graph.
     * @param v  the first endpoint of the edge
     * @param w  the second endpoint of the edge
     * @param e  edge between them
     */
    public void insertEdge(ResidualVertex v, ResidualVertex w, ResidualEdge e) 
    {
        this.edgeList.addLast(e);
        v.outgoingEdge.addLast(e);
    }

    /**
     * Add an edge to this graph.
     * @param e  residual edge with updates values
     */
    public void updateEdge(ResidualEdge e) 
    {
        int index = edgeList.indexOf(e);
        edgeList.remove(index);
        edgeList.addLast(e);
    }
    
    /**
     * Add a vertex to this graph.
     * @param v  residual vertex with updates values
     */
    public void updateVertex(ResidualVertex v) 
    {
        int index = vertexList.indexOf(v);
        vertexList.remove(index);
        vertexList.addLast(v);
    }
    
    /**
     * Given a vertex and an edge, if the vertex is one of the endpoints
     * of the edge, return the other endpoint of the edge.  Otherwise,
     * return null.
     * @param v  a vertex
     * @param e  an edge
     * @return  the other endpoint of the edge (or null, if v is not an endpoint of e)
     */
    public ResidualVertex opposite(ResidualVertex v, ResidualEdge e) 
    {
        ResidualVertex w;
        
        if (e.getFirstEndpoint() == v) {
            w = e.getSecondEndpoint();
        }
        else if (e.getSecondEndpoint() == v) {
            w = e.getFirstEndpoint();
        }
        else
            w = null;
        
        return w;
    }
    
    /**
     * Return the number of vertices in this graph.
     * @return  the number of vertices
     */
    public int numVertices() 
    {
        return vertexList.size();
    }

    /**
     * Return the number of edges in this graph.
     * @return  the number of edges
     */
    public int numEdges()
    {
        return edgeList.size();
    }
    
    /**
     * Checks if the residual graph contains active nodes | Nodes containing excess other than source and sink
     * @return  true if residual graph contains active nodes. Else false
     */
    public boolean containsActiveNode()
    {
        //System.out.println("Checking for active node");
        for (int i = 0; i<vertexList.size(); i++) 
        {
            ResidualVertex v = (ResidualVertex) vertexList.get(i);
            if(!v.getName().equalsIgnoreCase("s") && !v.getName().equalsIgnoreCase("t") && v.getExcess() > 0)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns active node from residual graph
     * Active node is any node apart from s and t in the residual graph with excess
     * @return  an active vertex. Null if no vertex found.
     */
    public ResidualVertex getActiveNode()
    {
        for (int i = 0; i<vertexList.size(); i++) 
        {
            ResidualVertex v = (ResidualVertex) vertexList.get(i);
            if(!v.getName().equalsIgnoreCase("s") && !v.getName().equalsIgnoreCase("t") && v.getExcess() > 0)
            {
                return v;
            }
        }
        return null;
    }
    
     /**
     * Returns active node from residual graph
     * Active node is any node apart from s and t in the residual graph with excess
     * @return  an active vertex. Null if no vertex found.
     */
    public ResidualVertex getVertex(String name)
    {
        for (int i = 0; i<vertexList.size(); i++) 
        {
            ResidualVertex v = (ResidualVertex) vertexList.get(i);
            if(v.getName().equalsIgnoreCase(name))
            {
                //System.out.println("Vertex found - "+v.getName());
                return v;
            }
        }
        return null;
    }
    
    /**
     * Computes the edge between 2 vertices in the residual graph
     * @return  the edge between two vertices. If edge is not present, returns null.
     */
    public ResidualEdge getEdge(ResidualVertex startNode, ResidualVertex endNode)
    {
        ResidualEdge e;
        Iterator itr = this.outgoingEdges(startNode);
        while(itr.hasNext())
        {
            e = (ResidualEdge) itr.next();
            if(e.getSecondEndpoint().getName().equalsIgnoreCase(endNode.getName()))
            {
                return e;
            }
        }
        return null;
    }
    
    /**
     * Remove edge from residual graph
     * @return  true if residual graph contains active nodes. Else false
     */
    public void removeEdge(ResidualEdge e)
    {
        this.edgeList.remove(e);
        ResidualVertex v =  e.getFirstEndpoint();
        v.outgoingEdge.remove(e);
        this.updateVertex(v);
    }
    
    /**
     * Get excess at sink. Used for pre-flow push algorithm
     * @return  the value of excess at the sink/
     */
    public double getExcessAtSink()
    {
        LinkedList vertices = this.vertexList;
        double maxFlow = 0;
        for(int i=0; i<vertices.size(); i++)
        {
            ResidualVertex v = (ResidualVertex) vertices.get(i);
            if(v.getName().equalsIgnoreCase("t"))
            {
                maxFlow =  v.getExcess();
            }
        }
        return maxFlow;
    }
    /**
     * Gets the source vertex
     * @return  the source vertex
     */
    public ResidualVertex getSource()
    {
        LinkedList vertices = this.vertexList;
        ResidualVertex s;
        for(int i =0; i<vertices.size(); i++)
        {
            s = (ResidualVertex) vertices.get(i);
            if(s.getName().equalsIgnoreCase("s"))
            {
                return s;
            }
        }
        return null;
    }
    /**
     * Gets s-t path
     * @return  path from s to t
     */
    public LinkedList<ResidualVertex> depthFirstSearch(ResidualVertex v, LinkedList<ResidualVertex> path)
    {
        if(!v.isVisited())
        {
            v.visit();
            path.addLast(v);
            this.updateVertex(v);
        }
        
        if(v.hasAdjacentUnivisited())
        {
            ResidualVertex w = v.getAdjacentUnivisited();
            
            if(!w.getName().equalsIgnoreCase("t"))
            {
                this.depthFirstSearch(w,path);
            }
            else
            {
                path.addLast(w);
            }
        }
        else
        {
            path.remove(v);
            
            if(!path.isEmpty())
            {
                v = (ResidualVertex) path.getLast();
                this.depthFirstSearch(v,path);
            }
        }
        
        return path;
    }

    public void updateVertexVisited() 
    {
        LinkedList vertices = this.vertexList;
        for(int i=0; i<vertices.size(); i++)
        {
            ResidualVertex v = (ResidualVertex) vertices.get(i);
            v.unvisit();
            this.updateVertex(v);
        }
    }
}
