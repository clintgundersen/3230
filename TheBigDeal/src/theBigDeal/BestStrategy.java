package theBigDeal;
import java.text.DecimalFormat;

/**
 *  COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Best Strategy class to evaluate Lets Make a Deal Strategies.
    CS3250-601 Project #3 assignment
    @version 1.0 2014-09-23
    @author Clint Gundersen
 */

/**
 * This program evaluates three different approaches to playing the game Lets
 * Make a Deal, and determines which one has the best chance of success.
 * @version 1.0 2014-9-24
 * @author gundersenc
 */
public class BestStrategy
{
    public static void main(String[] args)
    {

        System.out.println("Clint Gundersen Project 3 \"The Big Deal\"\n");
       
        final int ITERATIONS = 50000;
            
        double randomWin = 0;
        double fearfulWin = 0;
        double undecidedWin = 0;
        
        for (int i = 0; i < ITERATIONS; i++)
        {
            if(randomStrategy())
                randomWin++;
            if(fearfulStrategy())
                fearfulWin++;
            if(undecidedStrategy())
                undecidedWin++;
        }
        
        double randomScore = randomWin / ITERATIONS;
        double fearfulScore = fearfulWin / ITERATIONS;
        double undecidedScore = undecidedWin / ITERATIONS;
        
        printOutput(randomScore, fearfulScore, undecidedScore, ITERATIONS);
            
    }
    
    /**
     * Simulates choosing a door at random and sticking with it when presented
     * with opportunity to change.
     * @return true if win
     */
    public static boolean randomStrategy()
    {
        int winDoor = RandomMethods.doorChoice();
        int selectedDoor = RandomMethods.doorChoice();
        if (winDoor ==  selectedDoor)
            return true;
        else return false;
    }
    
    /**
     * Simulates choosing  a door randomly and then switch to the door 
     * remaining, after being presented with a non-winning door.
     * @return true if win
     */
    public static boolean fearfulStrategy()
    {   
        int winDoor = RandomMethods.doorChoice();
        int selection = RandomMethods.doorChoice();
        
        //selected the winning door
        if (selection == winDoor)
        {
            /*this strategy will lose every time the winning door is selected
            as only losing doors will be presented.
            */
            return false;
        }
        //did not select winning door
        else return true;
        /*This strategy will win every time the winning door is not selected
        as the host will eliminate the losing door and leave only the 
        winning door remaining
        */
    }
    
    /**
     * Simulates a strategy of randomly selecting between the two real
     * strategies
     * @return true if win
     */
    public static boolean undecidedStrategy()
    {
        final int RANDOM = 0;
        int coinToss = RandomMethods.oneZero();
        
        if (coinToss == RANDOM)
            return randomStrategy();
        else return fearfulStrategy();
    }
    
    /**
     * Prints formatted output for the scores given.
     * @param randomScore double 
     * @param fearfulScore double
     * @param undecidedScore double
     * @param iterations int
     */
    public static void printOutput(double randomScore, double fearfulScore, 
            double undecidedScore, int iterations)
    {
        DecimalFormat fourPlaces = new DecimalFormat("##.####");
        System.out.println("Best Strategy for the Big Deal");
        System.out.println("(" + iterations + " iterations per strategy)");
        System.out.println("Strategy\tFraction Wins");
        System.out.println("--------\t-------------");
        System.out.println("Random\t\t" + fourPlaces.format(randomScore));
        System.out.println("Fearful\t\t" + fourPlaces.format(fearfulScore));
        System.out.println("Undecided\t" + fourPlaces.format(undecidedScore)
                + '\n');
        
        System.out.println("The best strategy is: " + 
                findBestStrategy(randomScore, fearfulScore, undecidedScore));
        System.out.println("Submitted by Clint Gundersen\n");
    }
    
    /**
     * Evaluates which of the three scores is the highest, and then returns
     * the name of that strategy as the winning strategy.
     * @param randomStrat double
     * @param fearfulStrat double
     * @param undecidedStrat double
     * @return String name of the winning strategy.
     */
    public static String findBestStrategy(double randomStrat, 
            double fearfulStrat, double undecidedStrat)
    {
        if (randomStrat > fearfulStrat && randomStrat > undecidedStrat)
            return "Random Strategy";
        else if (fearfulStrat > randomStrat && fearfulStrat > undecidedStrat)
            return "Fearful Strategy";
        else if (undecidedStrat > randomStrat && undecidedStrat > fearfulStrat)
            return "Undecided Strategy";
        else return "Data inconclusive.";
    }
    
}
