package parallelmatrixmultiply;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Matrix class to store matrices and their functions
    CS3250-601 Project #6 assignment
    @version 1.0 2014-11-10
    @author Clint Gundersen
 */

/**
 * This program creates and stores matrices based on input from a properly 
 * formated text file.
 */
public class Matrix
{
    final int MAX_GRID_SIZE = 2000;
    private int gridColumns;
    private int gridRows;
    private double [][] grid;
    private long timeToCalculate;
    private ForkJoinPool forkPool;
    
    /**
     * Param constructor for matrix
     * @param gridRows int, number of rows
     * @param gridColumns  int number of columns
     */
    public Matrix (int gridRows, int gridColumns)
    {
        this.gridColumns = gridColumns;
        this.gridRows = gridRows;
        grid = new double[gridRows][gridColumns];
    }
    
    /**
     * Param constructor
     * @param path string, path to a properly formated matrix text file
     * @throws FileNotFoundException 
     */
    public Matrix(String path) throws FileNotFoundException
    {
        readMatrixFromFile(path);        
    }
    
    /**
     * Takes a properly formated text file and creates a matrix based on the 
     * values contained in the file
     * @param path string, path
     * @throws FileNotFoundException 
     */
    public void readMatrixFromFile(String path) throws FileNotFoundException
    {
        Scanner fileScanner = new Scanner(new File(path));
        
        //parse the first line to read in the size of our grid
        gridColumns = Integer.parseInt(fileScanner.next());
        gridRows = Integer.parseInt(fileScanner.next());
        
        //create the grid based on the size of the first line
        grid = new double [gridRows][gridColumns];
        
        //loop through the grid
        for (int i = 0; i < gridRows; i++)
        {
            for (int j = 0; j < gridColumns; j++)
            {
                grid[i][j] = Double.parseDouble(fileScanner.next());
            }
        }
    }
    
    /**
     * returns a string value that would print the matrix as it was on the
     * original text file, used for testing purposes only.
     * @return string matrix
     */
    public String getMatrix()
    {
        String output = "Columns: " + gridColumns;
        output += "Rows: " + gridRows + '\n';
        
        for (int i = 0; i < gridRows; i++)
        {
            for (int j = 0; j < gridColumns; j++)
            {
                output += grid[i][j];
                output += " - ";
            }
            output += '\n';
        }
        return output;
    }
    
    /**
     * Returns the double value from the matrix at a given coordinate
     * @param row int row coordinate
     * @param column int column coordinate
     * @return 
     */
    public double getValueAtCoordinate(int row, int column)
    {
        return grid[row][column];
    }
    
    /**
     * sets the value of a given coordinate on a matrix
     * @param row int row coordinate
     * @param column int column coordinate
     * @param value double value to be placed in the matrix
     */
    public void setValueAtCoordinate(int row, int column, double value)
    {
        grid[row][column] = value;
    }
    
    /**
     * returns the number of rows in the matrix
     * @return int rows
     */
    public int getRows()
    {
        return gridRows;
    }
    
    /**
     * returns the number of columns in the matrix
     * @return int columns
     */
    public int getColumns()
    {
        return gridColumns;
    }
    
    /**
     * stores the long value timeToCalculate
     * @param timeToCalculate long timeToCalculate, intended to be a millisecond
     * value
     */
    public void setTimeToCalculate(long timeToCalculate)
    {
        this.timeToCalculate = timeToCalculate;
    }
    
    /**
     * returns the timeToCaluclate value in milliseconds
     * @return timeToCalculate
     */
    public double getTimeToCalculate()
    {
        return timeToCalculate;
    }
    
    /**
     * Sequentially multiplies the values of this with the values ot right 
     * operand and stores
     * them in output.
     * @param rightOperand matrix rightOperand
     * @param output matrix output
     */
    public void sequentialMulitiply(Matrix rightOperand, Matrix output)
    {
        long startTime = System.currentTimeMillis();
 
        for (int i = 0; i < gridRows; i++)
        {             
            solveRow(i, rightOperand, output);
        }
        
        //time  method, set time value
        long endTime = System.currentTimeMillis();
        
        output.setTimeToCalculate(endTime - startTime);
    }

    /**
     * Parallel multiplies the values of this with the values ot right 
     * operand and stores
     * them in output.
     * @param rightOperand matrix rightOperand
     * @param output matrix output
     */
    public void paralellMultiply(Matrix rightOperand, Matrix output)
    {
        long startTime = System.currentTimeMillis();
        
        RecursiveAction mainTask = null;
        ArrayList <RecursiveAction> tasks = new ArrayList();
        for (int i = 0; i < gridRows; i++)
        {     
            tasks.add(new RowTask(i,rightOperand, output));
            tasks.get(i).fork();
        }
        for (int i = 0; i < tasks.size(); i++)
            tasks.get(i).join();
        
        //time the method, set time value
        long endTime = System.currentTimeMillis();
        output.setTimeToCalculate(endTime - startTime);        
    }
    
    /**
     * multiplies the values of a row of the matrix 
     * @param row int row coordinate
     * @param rightOperand Matrix rightOperand
     * @param output  Matrix output
     */
    public void solveRow(int row, Matrix rightOperand, Matrix output)
    {
        int column = 0;
            for (int j = 0; j < gridColumns; j++)
            {
                double value = 0;
                for (int k = 0; k < gridRows; k++)
                {
                    double factor = grid[row][k] * rightOperand.getValueAtCoordinate(k, j);
                    value += factor;
                }     
                output.setValueAtCoordinate(row, column, value);
                column++;
            }
    }
    
    /**
     * private class for the parallel function parallelMultiply
     * 
     */
    private class RowTask extends RecursiveAction
    {
        private int row;
        private Matrix rightOperand;
        private Matrix output;
        
        RowTask(int row, Matrix rightOperand, Matrix output)
        {
            this.row = row;
            this.rightOperand = rightOperand;
            this.output = output;
        }

        @Override
        protected void compute()
        {
            solveRow(row, rightOperand, output);
        }
        
    }
}


