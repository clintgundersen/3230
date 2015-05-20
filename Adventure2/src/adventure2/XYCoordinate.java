package adventure2;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains XYCoordinate class to manage map coordinates for 
    the Adventure2 game.
    CS3250-601 Project #4 assignment
    @version 1.0 2014-10-8
    @author Clint Gundersen
 */

/**
 * This program creates and stores coordinates to track player movements
 * In order to hopefully satisfy the requirements for Project 4
 * @version 1.0 2014-10-8
 * @author gundersenc
 */
public class XYCoordinate
{
    private int xCoor;
    private int yCoor;
    
    /**
     * default constructor, places XY at top left corner of map.
     */
    public XYCoordinate()
    {
        xCoor = 0;
        yCoor = 0;
    }
    
    /**
     * Param constructor, allows the specification of both X and Y coordinates
     * @param xCoor
     * @param yCoor 
     */
    public XYCoordinate(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }
    
    /**
     * Sets both the X and Y coordinates
     * @param xCoor
     * @param yCoor 
     */
    public void setXY(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }
    
    /**
     * returns the current xCoor value
     * @return xCoor int
     */
    public int getXCoordinate()
    {
        return xCoor;
    }
    
    /**
     * set the current xCoor value
     * @param xCoor 
     */
    public void setXCoordinate(int xCoor)
    {
       this.xCoor = xCoor;
    }
    
    /**
     * returns the current yCoor value
     * @return yCoor int
     */
    public int getYCoordinate()
    {
        return yCoor;
    }
    
    /**
     * set the current yCoor value
     * @param yCoor 
     */
    public void setYCoordinate(int yCoor)
    {
        this.yCoor = yCoor;
    }
    
    /**
     * overrides toString to print both the x and y coor comma seperated
     * @return string value of this
     */
    public String toString()
    {
        return String.valueOf(xCoor) + ',' + String.valueOf(yCoor);
    }
    
    /**
     * increments xCoor
     */
    public void xIncrement()
    {
        xCoor++;
    }
    
    /**
     * decrements xCoor
     */
    public void xDecrement()
    {
        xCoor--;
    }
    
    /**
     * increments yCoor
     */
    public void yIncrement()
    {
        yCoor++;
    }
    
    /**
     * decrements yCoor
     */
    public void yDecrement()
    {
        yCoor--;
    }
}
