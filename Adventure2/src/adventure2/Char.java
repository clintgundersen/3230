package adventure2;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Char class to manage the player character 
    in order to play Adventure2 game.
    CS3250-601 Project #4 assignment
    @version 1.0 2014-10-8
    @author Clint Gundersen
 */

/**
 * This program creates and controls the player character.  
 * @version 1.0 2014-10-8
 * @author gundersenc
 */
public class Char
{
    private final static char NORTH = 'n';
    private final static char SOUTH = 's';
    private final static char EAST = 'e';
    private final static char WEST = 'w';
    private final static int DEFAULT_VISION_RANGE = 5;
    
    private static ArrayList<String> inventory = new ArrayList<String>();
    private static XYCoordinate location;
    private static Map playerMap;
    
    /**
     * Char constructor requires the path to the map file, populates default
     * inventory list
     * @param relativePathTomapFile path to formated text file
     * @throws FileNotFoundException because netbeans made me
     */
    public Char(String relativePathTomapFile) throws FileNotFoundException
    {
        //populate default inventory
        inventory.add("flashlight");
        inventory.add("rope");
        inventory.add("cell phone");
        inventory.add("food");
        inventory.add("walking stick");
        inventory.add("canteen");
        location = new XYCoordinate();
        playerMap = new Map(relativePathTomapFile);
    }
    
     /**
     * executes the go command, moving the player in the direction indicated
     * in their command
     * @param direction single character direction 
     */
    public static void goCommand(char direction)
    {
        if(directionIsValid(direction))
        {
            if (direction == (NORTH))
            moveNorth();
            else if (direction == (SOUTH))
            moveSouth();
            else if (direction == (EAST))
            moveEast();
            else if (direction == (WEST))
            moveWest();
        }        
    }
    
    /**
     * moves the player north if possible or prints an error message
     */
    public static void moveNorth()
    {
        if (location.getYCoordinate() > 0)
        {
            System.out.println("Moving north...");            
            location.yDecrement();
        }
        else System.out.println("You can't go that far north");
        location.toString();
                    
        printMoveResult();
    }
       
    /**
     * moves the player south if possible or prints an error message
     */
    public static void moveSouth()
    {
        if (location.getYCoordinate() < playerMap.getHeight() - 1)
        {
            System.out.println("Moving south...");            
            location.yIncrement();
        }
        else System.out.println("You can't go that far south");
        location.toString();
                    
        printMoveResult();        
    }
    
    /**
     * moves the player east if possible or prints an error message
     */
    public static void moveEast()
    {
        if (location.getXCoordinate() < playerMap.getWidth() - 1)
        {
            System.out.println("Moving east...");            
            location.xIncrement();
        }           
        else System.out.println("You can't go that far east");
        location.toString();
        
        printMoveResult();
    }
    
    /**
     * moves the player west if possible or prints an error message
     */
    public static void moveWest()
    {
        if (location.getXCoordinate() > 0)
        {
            System.out.println("Moving west...");
            location.xDecrement();           
        }            
        else System.out.println("You can't go that far west");
        location.toString();
        
        printMoveResult();
    }   
    
    /**
     * checks to see if a direction is valid, if not returns false
     * @param direction single char direction
     * @return boolean value, true if direction is recognized as valid
     */
    public static boolean directionIsValid(char direction)
    {
        if (direction ==  NORTH || direction == SOUTH || 
                direction == EAST || direction == WEST)
            return true;
        else
        {
            System.out.println("You can't go that way.");
            return false;
        }            
    }
    
    /**
     * prints out the current contents of the class variable inventory
     */
    public static void inventoryCommand()
    {
        System.out.println("You are carrying:");
        for (int i = 0; i < inventory.size(); i++)
        {
            System.out.println(inventory.get(i));
        }
    }
    
    /**
     * returns an XYCoordinate with the players current location
     * @return XYCoordinate current location
     */
    public static XYCoordinate getLocation()
    {
        return location;
    }
    
    /**
     * prints the output result of a character movement
     */
    public static void printMoveResult()
    {
        System.out.println("You are at location " + location + " in terrain " 
                + playerMap.getTerrainAtCoordinate(location));
        playerMap.printVisibleMap(location, DEFAULT_VISION_RANGE);
    }
    
    /**
     * for testing only, calls the printmap function to output a complete map
     */
    public static void testMap()
    {
        playerMap.testMapOutput();
    }
}


