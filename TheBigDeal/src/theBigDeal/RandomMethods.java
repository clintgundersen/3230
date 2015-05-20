package theBigDeal;
import java.util.Random;

/**
 *  COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains random methods used by BestStrategy to determine the best 
    strategy.
    CS3250-601 Project #3 assignment
    @version 1.0 2014-09-23
    @author Clint Gundersen
 */

/**
 * This program generates random numbers in two different configurations for
 * use in the BestStrategy class
 * @version 1.0 2014-9-24
 * @author gundersenc
 */
public class RandomMethods
{
    //create a random number
    static Random randomSelection = new Random();
    
    /**
     * Creates a random number consisting of either one or zero, essentially
     * a coin toss
     * @return integer 0 or 1
     */
    public static int oneZero()
    {
        final int COIN_VALUE = 1;//sets the upper limit of the random number
        int toss = randomSelection.nextInt(COIN_VALUE);
        return toss;
    }
    
    /**
     * Creates a random number between 1 and 3, reflecting the names of the
     * doors available in the Lets Make a Deal game
     * @return int door number
     */
    public static int doorChoice()
    {
        final int DOORS_MIN = 1;//lowest door number possible
        final int DOORS_MAX = 3;//highest door number possible
        
        int randomDoor = randomSelection.nextInt(DOORS_MAX) + DOORS_MIN;
        return randomDoor;
    }
    
}
