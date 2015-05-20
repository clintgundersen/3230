package parallelmatrixmultiply;

import java.io.FileNotFoundException;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains ParallelMatrixMultiply class to multiply two matrices and
    return the value at position 1,1.
    CS3250-601 Project #6 assignment
    @version 1.0 2014-11-10
    @author Clint Gundersen
 */

/**
 * This program creates two matrices frome command line arguments, multiplies
 * them together and stores the results in a third matrix, then returns the
 * value at position 1,1 to the gui.  
 * @version 1.0 2014-11-10
 * @author gundersenc
 */
public class ParallelMatrixMultiply extends Application
{
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException
    {
        //hardcoded for testing, hardcode overridden by parameters.
        String fileNameA = "//home//clint//Desktop//matrixTest";
        String fileNameB = "//home//clint//Desktop//matrixTest";
        Matrix left;
        Matrix right;
        Matrix sequential;
        Matrix parallel;
        
        //get the command line parameters
        //printlns are temporary values for debugging
        //code provided by prof knadler
        System.out.println("\nunnamedParameters -");
        List<String> unnamedParameters = getParameters().getUnnamed();
        if (unnamedParameters.size() > 0)
        {
            fileNameA = unnamedParameters.get(0);
            fileNameB = unnamedParameters.get(1);
            //System.out.println("filenames: " + fileNameA + " " + fileNameB);
        }
        
        //setup our matrices based on command line parameters
        left = new Matrix(fileNameA);
        right = new Matrix (fileNameB);
        
        // create gui
        final int GUI_WIDTH = 450;
        final int GUI_HEIGHT = 200;
        final int PADDING = 5;
        
        //borderpane
        BorderPane border = new BorderPane();
        Insets inset = new Insets(PADDING, PADDING, PADDING, PADDING);
        border.setPadding(inset);
        border.setStyle("-fx-border-color: green");
        
        // main display pane
        BorderPane displayPane = new BorderPane();
        
        // output area
        TextArea output = new TextArea();
        displayPane.setCenter(new ScrollPane(output));
        displayPane.setTop(border);
        
        //header
        Label header = new Label();
        header.setText("Project 6 Parallel Processing by Clint Gundersen");
        header.setAlignment(Pos.BOTTOM_RIGHT);
        border.setCenter(header);                        
        
        // stage
        Scene scene = new Scene(displayPane, GUI_WIDTH, GUI_HEIGHT);
        primaryStage.setTitle("ParallelMatrixMultiply");
        primaryStage.setScene(scene);
        primaryStage.show();     
        
        
        //create sequential output
        left.sequentialMulitiply(right, sequential = new Matrix(left.getRows(), 
                left.getColumns()));
        //create parallel output
        left.paralellMultiply(right, parallel = new Matrix(left.getRows(), 
                left.getColumns()));
        
        final int COORDINATE = 1;
        String parOutput = "PARALLEL ALGORITHM\nC(1,1) = " + 
                parallel.getValueAtCoordinate(COORDINATE, COORDINATE) 
                + "\nParallel Time = " 
                + parallel.getTimeToCalculate() + " milliseconds" + '\n' + 
                "The number of processors is " 
                + Runtime.getRuntime().availableProcessors();
        String seqOutput = "SEQUENTIAL ALGORITHM\nC(1,1) = " 
                + sequential.getValueAtCoordinate(COORDINATE, COORDINATE) 
                + "\nSequential Time = " + sequential.getTimeToCalculate() 
                + " milliseconds";
        output.setText(parOutput + "\n\n" + seqOutput);       
    }   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
