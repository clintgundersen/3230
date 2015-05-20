package adventure2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Map class to to manage maps for the adventure game.
    CS3250-601 Project #4 assignment
    @version 1.0 2014-10-8
    @author Clint Gundersen
 */

/**
 * This program creates and prints maps based on location coordinates.
 * In order to hopefully satisfy the requirements for Project 4
 * @version 1.0 2014-10-8
 * @author gundersenc
 */
public class Map
{
    private int mapXPlane;
    private int mapYPlane;
    private char[][] grid;
    
    /**
     * Map constructor
     * @param relativePath path to a properly formatted map txt file
     * @throws FileNotFoundException because NetBeans made me.
     */
    public Map(String relativePath) throws FileNotFoundException
    {      
        //this file is passed from the Char constructor
        readMapFromFile(relativePath);
    }
    
    /**
     * Creates a map based on input from a properly formatted map txt file.
     * Does not check if the terrain displayed is valid.
     * @param relativePath path to properly formatted map txt file
     * @throws FileNotFoundException 
     */
    public void readMapFromFile(String relativePath) throws FileNotFoundException
    {
        File mapFile = new File(relativePath);
        Scanner fileScanner = new Scanner(mapFile);
        
        //setup map
        mapXPlane = Integer.parseInt(fileScanner.next());
        mapYPlane = Integer.parseInt(fileScanner.next());
        
        grid = new char[mapXPlane][mapYPlane];
        
        //loop through the remainder of the file and parse it out.
        int indexCount = 0;
        String line;
        while(fileScanner.hasNext())
        {
            line = fileScanner.next();
            grid[indexCount] = line.toCharArray();
            indexCount++;
        }
    }
    
    /**
     * Returns the char value of the terrain at a given coordinate, or if there
     * is no terrain at that coordinate returns an X
     * @param location XYCoordinate
     * @return char value of terrain or X if no terrain in requested location
     */
    public char getTerrainAtCoordinate(XYCoordinate location)
    {
        final char OUT_OF_BOUNDS = 'X';
        if (locationInBounds(location))
            return grid[location.getYCoordinate()][location.getXCoordinate()];
        else return OUT_OF_BOUNDS;
    }
    
    /**
     * Checks to see if a given XYCoordinate is within the bounds of the 
     * current map
     * @param location XYCoordinate
     * @return boolean value representing if location exists on the map
     */
    public boolean locationInBounds(XYCoordinate location)
    {
        if (location.getXCoordinate() >= 0 
                && location.getYCoordinate() >= 0 
                && location.getXCoordinate() < mapXPlane 
                && location.getYCoordinate() < mapYPlane)
            return true;
        else return false;
    }
    
    /**
     * Returns the width of the map
     * @return mapXplane int value of map width
     */
    public int getWidth()
    {
        return mapXPlane;
    }
    
    /**
     * Returns the height of the map
     * @return mapYplane int value of map width
     */
    public int getHeight()
    {
        return mapYPlane;
    }
    
    /**
     * prints out the portion of the map that is visible to the player at the
     * time of the call, based on the vision range value.
     * @param location XYCoordinate location of the player
     * @param visionRange int value of the diameter of vision the player can
     * see, probably should have gone with a radius but its done now.
     */
    public void printVisibleMap(XYCoordinate location, int visionRange)
    {
        XYCoordinate miniMap = new XYCoordinate();
        //find the radius surrounding the player.
        final int HALF = 2;//for dividing vision range to get center point
        int radius = visionRange / HALF;
        //set location to be the "0,0" of our minimap with our player in center
        miniMap.setXY(location.getXCoordinate() - radius, 
                location.getYCoordinate() - radius);
         
        for(int i = 0; i < visionRange; i++)
        {
            for (int j = 0; j < visionRange; j++)
            {
                System.out.print(getTerrainAtCoordinate(miniMap));
                miniMap.xIncrement();
            }
            System.out.print('\n');
            miniMap.yIncrement();
            //reset x for the next pass
            miniMap.setXCoordinate(miniMap.getXCoordinate() - visionRange);
        }
    }
    
    /**
     * Prints the entire map, not just the map as seen by the player.  Only
     * used in testing.
     */
    public void testMapOutput()
    {
        XYCoordinate location = new XYCoordinate();
        for(int i = 0; i < mapYPlane; i++)
        {
            for (int j = 0; j < mapXPlane; j++)
            {
                System.out.print(getTerrainAtCoordinate(location));
                location.xIncrement();
            }
            System.out.print('\n');
            location.yIncrement();
            location.setXCoordinate(0);
        }
        
    }
    
}
