/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package residualgraph;

import java.util.LinkedList;

/**
 *
 * @author sumanaravikrishnan
 */
public class ResidualVertex 
{
    public LinkedList outgoingEdge;

    private double height;              // the height associated with this vertex in the residual graph
    private double excess;              // the excess flow associated with this vertex in the residual graph
    private final String name;                // a name associated with this vertex
    private boolean visited;            //If a node is visited when searching for s-t path
    
    /**
     *  Constructor that allows height, excess and a name to be associated with the vertex.
     *  @param name     a name to be associated with this vertex
     */
    public ResidualVertex(Object name) 
    {
        this.name = String.valueOf(name);
        this.height = 0;
        this.excess = 0;
        this.visited = false;
        this.outgoingEdge = new LinkedList();
    }
    
    /**
     * Return the name associated with this vertex.
     * @return  the name of this vertex
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Return the height associated with this vertex.
     * @return  the height of this vertex
     */
    public double getHeight() 
    {
        return this.height;
    }
    
    /**
     * Set the height associated with this vertex.
     * @param height  the height of this vertex
     */
    public void setHeight(double height) 
    {
        this.height = height;
    }
    
    /**
     * Return the excess associated with this vertex.
     * @return  the excess of this vertex
     */
    public double getExcess() 
    {
        return this.excess;
    }
    
    /**
     * Set the excess associated with this vertex.
     * No excess is stored at source. Saving excess at sink to compute max flow.
     * @param excess  the height of this vertex
     */
    public void setExcess(double excess) 
    {
        if(!this.getName().equalsIgnoreCase("s"))
        {
            this.excess = excess;
        }
        else
        {
            this.excess = 0;
        }
    }
    
    /**
     * Return if there is any excess associated with the vertex
     * @return  true if excess is present. false if no excess.
     */
    public boolean hasExcess() 
    {
        if(this.excess == 0) return false;
        return true;
    }
    
    /**
     * Return if there a vertex is visited during s-t path search
     * @return  true if visited and false if not visited
     */
    public boolean isVisited() 
    {
        return this.visited;
    }
    
    /**
     * Visit a vertex
     */
    public void visit() 
    {
        this.visited = true;
    }
    
    /**
     * Un-visit a vertex
     */
    public void unvisit() 
    {
        this.visited = false;
    }
    
    /**
     * Find is there are adjacent univisted nodes
     */
    public boolean hasAdjacentUnivisited() 
    {
        for(int i=0; i<this.outgoingEdge.size(); i++)
        {
            ResidualEdge e = (ResidualEdge) this.outgoingEdge.get(i);
            ResidualVertex v = e.getSecondEndpoint();
            if(!v.isVisited())
            {
                
                return true;
            }
        }
        return false;
    }
     /**
     * Return adjacent vertex
     */
    public ResidualVertex getAdjacentUnivisited() 
    {
        for(int i=0; i<this.outgoingEdge.size(); i++)
        {
            ResidualEdge e = (ResidualEdge) this.outgoingEdge.get(i);
            ResidualVertex v = e.getSecondEndpoint();
            if(!v.isVisited())
            {
                return v;
            }
        }
        return null;
    }
}
