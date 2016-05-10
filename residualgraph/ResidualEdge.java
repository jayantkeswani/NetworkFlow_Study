/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package residualgraph;

/**
 *
 * @author sumanaravikrishnan
 */
public class ResidualEdge 
{
    private double capacity;        // the capacity associated with this edge in the residual graph
    private double flow;            // the flow associated with this edge in the residual graph
    private ResidualVertex v;       // one end of the edge in the residual graph         
    private ResidualVertex w;       // other end of the edge in the residual graph    
    
    /**
     *  Constructor that allows Vertex v, Vertex w and a capacity to be associated with an edge
     *  @param v     One end of an edge in residual graph
     *  @param w     Other end of an edge in residual graph
     *  @param capacity     Total capacity of the edge
     */
    public ResidualEdge(ResidualVertex v, ResidualVertex w, double capacity) 
    {
        this.capacity = capacity;
        this.v = v;
        this.w = w;
        //Setting flow to initially be zero
        this.flow = 0.0;
    }
    
    /**
     * Return the first endpoint of this residual edge.
     * @return  the first endpoint of this residual edge
     */
    public ResidualVertex getFirstEndpoint() 
    {
        return this.v;
    }

    /**
     * Return the second endpoint of this residual edge.
     * @return  the second endpoint of this residual edge
     */
    public ResidualVertex getSecondEndpoint() 
    {
        return this.w;
    }

    /**
     * Return the capacity associated with this edge.
     * @return  the capacity of this edge
     */
    public double getCapacity() 
    {
        return this.capacity;
    }
    
    /**
     * Return the capacity associated with this edge.
     * @return  the capacity of this edge
     */
    public void setCapacity(double capacity) 
    {
        this.capacity = capacity;
    }
        
    /**
     * Set the flow associated with this edge.
     * @param flow  the flow of this edge
     */
    public void setFlow(double flow) 
    {
        this.flow = flow;
    }
    
    /**
     * Return the flow associated with this edge.
     * @return  the flow of this edge
     */
    public double getFlow() 
    {
        return this.flow;
    }
    
    /**
     * Return if edge has saturated (capacity = flow)
     * @return  true if edge has saturated. false if more can be pushed.
     */
    public boolean isSaturated() 
    {
        if(this.capacity == this.flow) return true;
        return false;
    }
    
    /**
     * Return how much more flow can be pushed on an edge
     * @return extra flow possible
     */
    public double extraFlowAvailable() 
    {
        return getCapacity() - getFlow();
    }
}
