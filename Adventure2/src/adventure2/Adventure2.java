package adventure2;

import java.io.FileNotFoundException;
import java.util.Scanner;
/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Adventure2 class to play Adventure2 game.
    CS3250-601 Project #4 assignment
    @version 1.0 2014-10-8
    @author Clint Gundersen
 */


/**
 * This program displays the program title header, then runs the Adventure
 * game. In order to hopefully satisfy the requirements for Project 4
 * @version 1.0 2014-10-8
 * @author gundersenc
 */
public class Adventure2
{
    private static boolean continueGame = true;
    private final static int TOTAL_COMMAND_COMPONANTS = 2;
    private final static char GO = 'g';
    private final static char INVENTORY = 'i';
    private final static char QUIT = 'q';
    private static Char player;
            
    public static void main(String[] args) throws FileNotFoundException
    {        
        
        //instantiate our objects
        player = new Char(args[0]);
        
        //print header
        printHeader();
        
        //print the first map for the player
        player.printMoveResult();
        
        //print command prompt
        printCommands();                    
        
        do //loop until quit command is reached
        {
            boolean invalidInput = true;
            do //loop while input is invalid
            {
                //reset invalid input in case we just looped
                invalidInput = true;
                
                //command prompt
                System.out.print('>');
                
                //read a line of input
                String input = null;
                Scanner commandInput = new Scanner(System.in);                
                input = commandInput.nextLine();
                
                if(input.isEmpty())
                    System.out.println("You must enter a command.");
                
                if (!input.isEmpty())
                {                                     
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
                        printCommands();
                    }
                }
            } while (invalidInput);
        }while (continueGame);
        
    }
    
    /**
     * Prints the program header
     */
    public static void printHeader()
    {
        System.out.println("Project 4 Adventure");
    }
    
    /**
     * prints the command prompt advising the user of valid commands
     */
    public static void printCommands()
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
            if (commandParts.length > 1)
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
     * executes the users command
     * @param command array of two characters, first is command char second is
     * direction char
     */
    public static void executeCommand(char[] command)
    {
        if (command[0] == GO)
        {
            player.goCommand(command[1]);
        }
        else if (command[0] == INVENTORY)
        {
            player.inventoryCommand();
        }
        else if (command[0] ==  QUIT)
        {
            quitCommand();
        }
    }
    
    /**
     * prints the farewell message, allows game to exit
     */
    public static void quitCommand()
    {
        continueGame = false;
        System.out.println("Farewell");
        System.out.println(player.getLocation());
        printHeader();
    }    
}

