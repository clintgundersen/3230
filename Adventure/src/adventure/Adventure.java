package adventure;
import java.util.*;
/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Adventure class to play Adventure game.
    CS3250-601 Project #2 assignment
    @version 1.0 2014-09-11
    @author Clint Gundersen
 */


/**
 * This program displays the program title header, then runs the Adventure
 * game. In order to hopefully satisfy the requirements for Project 2
 * @version 1.0 2014-9-11
 * @author gundersenc
 */
public class Adventure
{
    private static int currentXCoordinate = 0;
    private static int currentYCoordinate = 0;
    private static boolean continueGame = true;
    private static final int MAX_MAP_SIZE = 4;
    private final static int TOTAL_COMMAND_COMPONANTS = 2;
    private final static char GO = 'g';
    private final static char INVENTORY = 'i';
    private final static char QUIT = 'q';
    private final static char NORTH = 'n';
    private final static char SOUTH = 's';
    private final static char EAST = 'e';
    private final static char WEST = 'w';
    
    private static List<String> inventory = new ArrayList<String>();  
        
            
    public static void main(String[] args)
    {        
        //populate inventory
        inventory.add("flashlight");
        inventory.add("rope");
        inventory.add("cell phone");
        inventory.add("food");
        inventory.add("walking stick");
        inventory.add("canteen");
        
        //print header
        printHeader();
        
        //print command prompt
        printPrompt();
                
        do //loop until quit command is reached
        {
            boolean invalidInput = true;
            do //loop while input is invalid
            {
                //reset invalid input in case we just looped
                invalidInput = true;
            
                //read a line of input
                Scanner commandInput = new Scanner(System.in);
                String input = commandInput.nextLine();
            
                //parse the input into a command and parameter
                char[] command = new char[TOTAL_COMMAND_COMPONANTS];
                command = parseInput(input);
            
                //if command is valid, execute command
                if (commandIsValid(command[0]))
                {
                    invalidInput = false;
                    executeCommand(command);
                }
                //if bad input loop back to get good input
                else 
                {
                    System.out.println("You can not do that.");
                    printPrompt();
                }
            
            } while (invalidInput);
        }while (continueGame);
        
    }
    
    /**
     * Prints the program header
     */
    public static void printHeader()
    {
        System.out.println("Project02 Adventure");
    }
    
    /**
     * prints the command prompt advising the user of valid commands
     */
    public static void printPrompt()
    {
        System.out.println("Enter command: Go (direction), " + "Inventory, "
                + "or Quit");
    }
    
    /**
     * splits a string using methods supplied in Project 2 instructions
     * @param commandInput string
     * @return 2 string array, if string could not be split, second string is 
     * marked as invalid
     */
    public static String[] splitString(String commandInput)
    {
        String[] commandParts = new String[TOTAL_COMMAND_COMPONANTS];
        
        if (commandInput.contains(" "))
        {
            commandParts = commandInput.split(" +");
        }
        else
        {
            commandParts[0] = commandInput;
            commandParts[1] = "invalid";
        }
        
        return commandParts;
    }
    
    /**
     * parses user input into an array of two characters for use
     * in executing game functions
     * @param commandInput, line of input to be parsed
     * @return a 2 character array, first char is command, second is direction
     */
    public static char[] parseInput(String commandInput)
    {
        //switch to lowercase
        commandInput = commandInput.toLowerCase();
        
        //split command into two strings 
        String[] commandParts = new String[TOTAL_COMMAND_COMPONANTS];
        commandParts = splitString(commandInput);
        
        //get command
        char[] commandArray = new char[TOTAL_COMMAND_COMPONANTS];
        commandArray[0]  = getCommand(commandParts[0]);
        
        //if command is a "go" command, get direction parameter
        char direction;
        if (commandArray[0] == 'g')
        {
            commandArray[1] = getDirection(commandParts[1]);
        }
        
        //return command and parameter
        return commandArray;
    }
    
    /**
     * converts a string into a single character to be used in commands
     * @param commandString input from the user
     * @return single character, the first character in the commandString
     */
    public static char getCommand(String commandString)
    {
        char command = commandString.charAt(0);
        return command;
    }
    
    /**
     * converts the users input string into a direction, either n = north 
     * s = south, e = east, w = west, or i = invalid direction
     * @param directionString input string from the user
     * @return direction character
     */
    public static char getDirection(String directionString)
    {
        char direction = 'i';
        
        if (directionString.equals("north"))
            direction = 'n';
        else if (directionString.equals("south"))
            direction = 's';
        else if (directionString.equals("east"))
            direction = 'e';
        else if (directionString.equals("west"))
            direction = 'w';
        
        return direction;
    }
    
    /**
     * determines if the command entered by the user is one of the accpeted
     * commands
     * @param command single char command
     * @return boolean value of true if the char is recognized as a valid 
     * direction
     */
    public static boolean commandIsValid(char command)
    {
        if (command == GO || command == INVENTORY || command == QUIT)
            return true;
        else return false;
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
        else return false;
    }
    
    /**
     * executes the users command
     * @param command array of two characters, first is command char second is
     * direction char
     */
    public static void executeCommand(char[] command)
    {
        if (command[0] == GO)
        {
            goCommand(command[1]);
        }
        else if (command[0] == INVENTORY)
        {
            inventoryCommand();
        }
        else if (command[0] ==  QUIT)
        {
            quitCommand();
        }
    }
    
    /**
     * executes the go command, moving the player in the direction indicated
     * in their command
     * @param direction single character direction 
     */
    public static void goCommand(char direction)
    {
        if (directionIsValid(direction))
                {
                    if (direction == (NORTH))
                        moveNorth();
                    if (direction == (SOUTH))
                        moveSouth();
                    if (direction == (EAST))
                        moveEast();
                    if (direction == (WEST))
                        moveWest();
                }
        else System.out.println("You can't go that way.");
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
     * prints the farewell message, allows game to exit
     */
    public static void quitCommand()
    {
        continueGame = false;
        System.out.println("Farewell");
        printCurrentLocation();
        printHeader();
    }
    
    /**
     * prints the players current location
     */
    public static void printCurrentLocation()
    {
        System.out.println("You are at location " + currentYCoordinate + ','
                + currentXCoordinate);
    }
    
    /**
     * moves the player north if possible or prints an error message
     */
    public static void moveNorth()
    {
        if (currentYCoordinate > 0)
        {
            System.out.println("Moving north...");
            currentYCoordinate--;
        }
        else System.out.println("You can't go that far north");
        printCurrentLocation();
    }
       
    /**
     * moves the player south if possible or prints an error message
     */
    public static void moveSouth()
    {
        if (currentYCoordinate < MAX_MAP_SIZE)
        {
            System.out.println("Moving south...");
            currentYCoordinate++;
        }
        else System.out.println("You can't go that far south");
        printCurrentLocation();
    }
    
    /**
     * moves the player east if possible or prints an error message
     */
    public static void moveEast()
    {
        if (currentXCoordinate < MAX_MAP_SIZE)
        {
            System.out.println("Moving east...");
            currentXCoordinate++;
        }           
        else System.out.println("You can't go that far east");
        printCurrentLocation();
    }
    
    /**
     * moves the player west if possible or prints an error message
     */
    public static void moveWest()
    {
        if (currentXCoordinate > 0)
        {
            System.out.println("Moving west...");
            currentXCoordinate--;
        }            
        else System.out.println("You can't go that far west");
        printCurrentLocation();
    }
}
