package calculator;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains Calculator class to replicate the function of a handheld
    calculator.
    CS3250-601 Project #7 assignment
    @version 1.0 2014-12-2
    @author Clint Gundersen
 */

/**
 * This program creates a calculator which can be operated by clicking on the
 * buttons on the calculator with a mouse cursor.  
 * @version 1.0 2014-12-2
 * @author gundersenc
 */
public class Calculator extends Application
{
    //display variable
    String displayValue = "0";
    double leftOperand = 0;
    double rightOperand = 0;
    String operator;
    boolean percentFlag = false;
    DecimalFormat smallDec;
    DecimalFormat dec;
    
    //create gui elements
    TextField display = new TextField();
    GridPane numberGrid;
    
    //extra credit buttons
    Button  square = new Button();
    Button  cube = new Button();
    Button  sqrt = new Button();
    Button  mod = new Button();
    
    //action buttons
    Button  clear = new Button();
    Button  flipSign = new Button();
    Button  percent = new Button();
    
    //math buttons
    Button  divide = new Button();
    Button  multiply = new Button();
    Button  subtract = new Button();
    Button  add = new Button();
    Button  execute = new Button();
    
    //number buttons
    Button  one = new Button();
    Button  two = new Button();
    Button  three = new Button();
    Button  four = new Button();
    Button  five = new Button();
    Button  six = new Button();
    Button  seven = new Button();
    Button  eight = new Button();
    Button  nine = new Button();
    Button  zero = new Button();
    Button decimal = new Button();
    
    /**
     * Setup the stage and gui elements
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage)
    {
        //setup gui elements      
        setupDisplay();
        setupExtraButtons();
        setupActionButtons();
        setupMathButtons();    
        setupNumberButtons();
                                            
        //setup grid 
        setupGridPane();
        
        //setup vbox
        VBox main = new VBox();
        main.getChildren().add(display);
        main.getChildren().add(numberGrid);
        
        //fill gridpane numbers
        final int ROW_0 = 0;
        final int ROW_1 = 1;
        final int ROW_2 = 2;
        final int ROW_3 = 3;
        final int ROW_4 = 4;
        final int ROW_5 = 5;
        numberGrid.addRow(ROW_0, square, cube,sqrt, mod);
        numberGrid.addRow(ROW_1, clear, flipSign, percent, divide);
        numberGrid.addRow(ROW_2, one, two, three, multiply);        
        numberGrid.addRow(ROW_3, four, five, six, subtract);
        numberGrid.addRow(ROW_4, seven, eight, nine, add);
        
        //setup last goofy row for zero
        final int COLUMN_SPAN = 2;
        final int ROW_SPAN = 1;
        numberGrid.add(zero, 0, ROW_5, COLUMN_SPAN, ROW_SPAN);
        numberGrid.addRow(ROW_5, decimal, execute);
        
        Scene scene = new Scene(main);
        scene.getStylesheets().add(Calculator.class.getResource
        ("calcStyle.css").toExternalForm());
        
        //setup stage
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();        
    }
    
    /**
     * setup the grid pane
     */
    public void setupGridPane()
    {
        final int COLUMN_WIDTH = 70;
        numberGrid = new GridPane();//represents numbers
        numberGrid.setId("numbergrid");
        numberGrid.setHgap(2);
        numberGrid.setVgap(2);
        numberGrid.setPadding(new Insets(0,0,0,0));
        
        //set column witdths
        numberGrid.getColumnConstraints().add(new ColumnConstraints(COLUMN_WIDTH));
        numberGrid.getColumnConstraints().add(new ColumnConstraints(COLUMN_WIDTH));
        numberGrid.getColumnConstraints().add(new ColumnConstraints(COLUMN_WIDTH));
        numberGrid.getColumnConstraints().add(new ColumnConstraints(COLUMN_WIDTH));
        //numberGrid.setGridLinesVisible(true);//for debugging layout
    }
    
    /**
     * sets up the text area for displaying input and output
     */
    public void setupDisplay()
    {
        display.setText(displayValue);
        display.setId("display");
        display.setAlignment(Pos.BASELINE_RIGHT);
        display.setEditable(false);
        display.setFocusTraversable(false);
        
        smallDec = new DecimalFormat("##############0.###############");
        dec = new DecimalFormat("0.#############E0");
    }
    
    /**
     * setup buttons to hopefully qualify for the extra credit portion of 
     * the assignment
     */
    public void setupExtraButtons()
    {
        square.setText("x²");
        square.setId("extra");
        square.setMaxWidth(Double.MAX_VALUE);
        
        cube.setText("x³");
        cube.setId("extra");
        cube.setMaxWidth(Double.MAX_VALUE);
        
        sqrt.setText("√");
        sqrt.setId("extra");
        sqrt.setMaxWidth(Double.MAX_VALUE);
        
        mod.setText("mod");
        mod.setId("extra");
        mod.setMaxWidth(Double.MAX_VALUE);
        
        setExtraListeners();
    }
    
    /**
     * set the listeners for the extra credit buttons
     */
    public void setExtraListeners()
    {
        square.setOnAction((ActionEvent e) ->
        {
            double value = Double.parseDouble(display.getText());
            formatDisplay(value * value);
        });
        
        cube.setOnAction((ActionEvent e) ->
        {
            double value = Double.parseDouble(display.getText());
            formatDisplay(value * value * value);
        });
        
        sqrt.setOnAction((ActionEvent e) ->
        {
            formatDisplay(Math.sqrt(Double.parseDouble(display.getText())));
        });
        
        mod.setOnAction((ActionEvent e) ->
        {
            prepMath();
            operator = "mod";
        });      
    }
    
    /**
     * setup the "other" buttons that do stuff
     */
    public void setupActionButtons()
    {
        clear.setText("C");
        clear.setId("action");
        clear.setMaxWidth(Double.MAX_VALUE);
        
        flipSign.setText("+/-");
        flipSign.setId("action");
        flipSign.setMaxWidth(Double.MAX_VALUE);
        
        percent.setText("%");
        percent.setId("action");
        percent.setMaxWidth(Double.MAX_VALUE);
        
        setActionListeners();
    }
    
    /**
     * set listeners for "other" buttons
     */
    public void setActionListeners()
    {
        clear.setOnAction((ActionEvent e) ->
        {
            displayValue = "0";
            display.setText(displayValue);
        });
        
        flipSign.setOnAction((ActionEvent e) ->
        {
            if (!display.getText().contains("-"))
                displayValue = "-" + displayValue;
            else displayValue = displayValue.substring(1, displayValue.length());
            display.setText(displayValue);
        });
        
        percent.setOnAction((ActionEvent e) ->
        {
            final double PERCENT = 100;
            rightOperand = leftOperand * (Double.parseDouble(display.getText()) / PERCENT);
            percentFlag = true;
        });
    }
    
    /**
     * set up buttons that control most of the math operations
     */
    public void setupMathButtons()
    {
        divide.setText("÷");
        divide.setId("math");
        divide.setMaxWidth(Double.MAX_VALUE);
        
        multiply.setText("X");
        multiply.setId("math");
        multiply.setMaxWidth(Double.MAX_VALUE);
        
        subtract.setText("-");
        subtract.setId("math");
        subtract.setMaxWidth(Double.MAX_VALUE);
        
        add.setText("+");
        add.setId("math");
        add.setMaxWidth(Double.MAX_VALUE);
        
        execute.setText("=");
        execute.setId("math");
        execute.setMaxWidth(Double.MAX_VALUE);
        
        setMathListeners();
    }
    
    /**
     * set listeners for math buttons
     */
    public void setMathListeners()
    {
        divide.setOnAction((ActionEvent e) ->
        {
            prepMath();
            operator = "divide";
        });
        
        multiply.setOnAction((ActionEvent e) ->
        {
            prepMath();
            operator = "multiply";
        });
        
        subtract.setOnAction((ActionEvent e) ->
        {
            prepMath();
            operator = "subtract";
        });
        
        add.setOnAction((ActionEvent e) ->
        {
            prepMath();
            operator = "add";
        });
        
        execute.setOnAction((ActionEvent e) ->
        {
            if (!percentFlag)
                rightOperand = Double.parseDouble(display.getText());
            else percentFlag = false;
            doTheMath();
        });
    }
    
    /**
     * sets variables to a state to receive math functions
     */
    public void prepMath()
    {
        leftOperand = Double.parseDouble(display.getText());
        displayValue = "0";
    }
    
    /**
     * set up the number buttons 0-9
     */
    public void setupNumberButtons()
    {
        one.setText("1");
        one.setId("number");
        one.setMaxWidth(Double.MAX_VALUE);
        
        two.setText("2");
        two.setId("number");
        two.setMaxWidth(Double.MAX_VALUE);
        
        three.setText("3");
        three.setId("number");
        three.setMaxWidth(Double.MAX_VALUE);
        
        four.setText("4");
        four.setId("number");
        four.setMaxWidth(Double.MAX_VALUE);
        
        five.setText("5");
        five.setId("number");
        five.setMaxWidth(Double.MAX_VALUE);
        
        six.setText("6");
        six.setId("number");
        six.setMaxWidth(Double.MAX_VALUE);
        
        seven.setText("7");
        seven.setId("number");
        seven.setMaxWidth(Double.MAX_VALUE);
        
        eight.setText("8");
        eight.setId("number");
        eight.setMaxWidth(Double.MAX_VALUE);
        
        nine.setText("9");
        nine.setId("number");
        nine.setMaxWidth(Double.MAX_VALUE);
        
        zero.setText("0");
        zero.setId("number");
        zero.setMaxWidth(Double.MAX_VALUE);
        
        decimal.setText(".");
        decimal.setId("number");
        decimal.setMaxWidth(Double.MAX_VALUE); 
        
        setNumberListeners();
    }
    
    /**
     * set listeners for number buttons
     */
    public void setNumberListeners()
    {
        one.setOnAction((ActionEvent e) ->
        {
            appendDisplay(one.getText());
        });
        
        two.setOnAction((ActionEvent e) ->
        {
            appendDisplay(two.getText());
        });
        
        three.setOnAction((ActionEvent e) ->
        {
            appendDisplay(three.getText());
        });
        
        four.setOnAction((ActionEvent e) ->
        {
            appendDisplay(four.getText());
        });
        
        five.setOnAction((ActionEvent e) ->
        {
            appendDisplay(five.getText());
        });
        
        six.setOnAction((ActionEvent e) ->
        {
            appendDisplay(six.getText());
        });
        
        seven.setOnAction((ActionEvent e) ->
        {
            appendDisplay(seven.getText());
        });
        
        eight.setOnAction((ActionEvent e) ->
        {
            appendDisplay(eight.getText());
        });
        
        nine.setOnAction((ActionEvent e) ->
        {
            appendDisplay(nine.getText());
        });
        
        zero.setOnAction((ActionEvent e) ->
        {
            appendDisplay(zero.getText());
        });
        
        decimal.setOnAction((ActionEvent e) ->
        {
            appendDisplay(".");
        });        
    }
    
    /**
     * adds numbers to the display in the same manner as a handheld calculator
     * @param append String number to append
     */
    public void appendDisplay(String append)
    {
        if (displayValue.equals("0") || displayValue.equals("0.0") && displayNotFull())
            displayValue = append;
        else if (append.equals("."))
        {
            if(!isFraction() && displayNotFull())
                displayValue += append;
            
        }
        else if (displayNotFull())
            displayValue += append;
        
        display.setText(displayValue);
    }
    
    /**
     * checks to make sure there is room on the display, maximum 15 numbers
     * @return true if room left in display
     */
    public boolean displayNotFull()
    {
        final int MAX_NORMAL = 15;
        final int MAX_SPECIAL = 16;
        final int MAX_DEC_NEG = 17;
        //if string is a whole number, holds 15 charactesr
        if(!isFraction() && !isNegative() && 
                display.getText().length() < MAX_NORMAL)
            return true;
        //if string is a pos fraction, or negative whole number 16 characters
        else if (isNegative() && !isFraction() && 
                display.getText().length() < MAX_SPECIAL)
            return true;
        else if (!isNegative() && isFraction() && 
                display.getText().length() < MAX_SPECIAL)
            return true;
        //if string is a neg fraction holds 17 chars
        else if (isNegative() && isFraction() && 
                display.getText().length() < MAX_DEC_NEG)
            return true;
        else return false;
    }
    
    /**
     * checks to see if the displayed number is a negative value
     * @return true if displayed value is negative
     */
    public boolean isNegative()
    {
        if (display.getText().contains("-"))
            return true;
        else return false;
    }
    
    /**
     * checks to see if displayed number is a decimal value
     * @return true if decimal value
     */
    public boolean isFraction()
    {
        if (display.getText().contains("."))
            return true;
        else return false;
    }

    /**
     * performs math operations based on flags set by operator buttons
     */
    public void doTheMath()
    { 
        if (operator.equals("divide"))
            divide();
        else if (operator.equals("multiply"))
            formatDisplay(leftOperand * rightOperand);
        else if (operator.equals("subtract"))
            formatDisplay(leftOperand - rightOperand);
        else if (operator.equals("add"))
            formatDisplay(leftOperand + rightOperand);
        else if (operator.equals("mod"))
            formatDisplay(leftOperand % rightOperand);     
        
        leftOperand = 0;
        rightOperand = 0;
        displayValue = "0";
    }
    
    /**
     * Performs division operations, or displays undefined if /0
     */
    public void divide()
    {
        if (rightOperand != 0)
            formatDisplay(leftOperand / rightOperand);
        else display.setText("Undefined");
    }
    
    /**
     * Formats the display for showing a number or scientific notation
     * accordingly based on the size of the output
     * @param number double
     */
    public void formatDisplay(double number)
    {
        final int NOTATION_CHECK = 15;
        String numCheck = String.valueOf(number);
        
        if (numCheck.length() > NOTATION_CHECK)
            display.setText(String.valueOf(dec.format(number)));
        else display.setText(String.valueOf(smallDec.format(number)));
    }
    
        public static void main(String[] args)
    {
        launch(args);
    }
    
    
}
