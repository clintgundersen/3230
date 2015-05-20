package chatclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
    COPYRIGHT (C) 2014 Clint Gundersen. All Rights Reserved.
    File contains ChatClient program to hopefully satisfy the requirements of 
    the take home final exam.
    CS3250-601 Project Take Home Final Exam
    @version 1.0 2014-12-15
    @author Clint Gundersen
 */

/**
 * This program connects to a chat server to create a functioning Instant 
 * messenger style chat program.
 * @version 1.0 2014-12-15
 * @author gundersenc
 */
public class ChatClient extends Application
{
    
    //consts for defaults
    private static final String DEFAULT_USER = "Anonymous";
    private static final int PORT = 8000;
    private static final String ADDRESS = "localhost";
    
    //user variables
    private static String userName = DEFAULT_USER;
    private static int userPort = PORT;
    
    //sizing consts
    final int WIDTH = 600;
    final int MESSAGE_WIDTH = 400;
    final int HEIGHT = 325;
    final int PADDING = 8;
        
    //networking
    private static Socket socket;
    private static PrintWriter out;
    private static ReadFromSocket in;
    
    //layout elements
    private static VBox root;
    private static HBox nameHBox;
    private static HBox outputHBox;
    private static Scene scene;
    
    //text elements
    private static Label outLabel;
    private static Label nameLabel;
    private static TextField outMessage;
    private static TextArea conversationTextArea;
    
    //controls
    private static Button disconnectButton;
    private static Button sendButton;
    
    @Override
    public void start(Stage primaryStage) throws IOException
    { 
        //assigns variables from command line parameters, over writes defaults
        List<String> unnamedParameters = getParameters().getUnnamed();
        if (unnamedParameters.size() > 0)
        {
            userName = unnamedParameters.get(0);
            userPort = Integer.parseInt(unnamedParameters.get(1));
            System.out.println
                ("User Name: " + userName + "\n" + "User Port: " + userPort);
        }
        
        //initialize layout elements
        setupLayout();
            
        //initialize text elements
        setupTextElements();
        setupTextarea();
        
        //setup gui controls
        setupButtons();                
        
        //add nodes
        root.getChildren().add(nameHBox);
        root.getChildren().add(outputHBox);
        root.getChildren().add(conversationTextArea);
        root.getChildren().add(disconnectButton);
        
        nameHBox.getChildren().add(nameLabel);
        outputHBox.getChildren().add(outLabel);
        outputHBox.getChildren().add(outMessage);
        outputHBox.getChildren().add(sendButton);
        
        //launch network setup
        setUpNetwork();
        
        //primary Stage
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        /**
         * make sure things close properly, based on code provided by "Sam"
         * in forums for CS3250
         */
        primaryStage.setOnCloseRequest((WindowEvent event) -> 
        {
            Platform.exit();
            System.exit(0);
        });
        
        primaryStage.show();
    }
    
    /**
     * setup the layout elements, specifically the containers used
     */
    public void setupLayout()
    {
        root = new VBox();
        nameHBox = new HBox();
        outputHBox = new HBox();
        scene = new Scene(root, WIDTH, HEIGHT);        
        
        nameHBox.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        nameHBox.setAlignment(Pos.CENTER);
        
        outputHBox.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        outputHBox.setSpacing(PADDING);
        outputHBox.setStyle("-fx-border-color: green");
    }
    
    /**
     * setup text boxes and labels
     */
    public void setupTextElements()
    {
        outLabel= new Label();
        outLabel.setText("Enter Message");
        
        nameLabel = new Label();
        nameLabel.setText("User name: " + userName);
         
        outMessage = new TextField();
        outMessage.setAlignment(Pos.BOTTOM_LEFT);
        outMessage.setPrefWidth(MESSAGE_WIDTH);
        setupTextFieldListener(); //event listener
    }
    
    /**
     * set up the text area
     */
    public static void setupTextarea()
    {
        conversationTextArea = new TextArea();
        conversationTextArea.setPrefHeight
            (root.getHeight() - outputHBox.getHeight());
        conversationTextArea.setEditable(false);
        conversationTextArea.setWrapText(true);
    }
    
    /**
     * Updates the conversation display by appending a line of text
     * @param update String
     */
    public static void updateConversation(String update)
    {
        conversationTextArea.appendText(update + "\n");
        outMessage.setText("");
    }
    
    /**
     * configure the textfield listener to send out the message entered
     * in the text field
     */
    public void setupTextFieldListener()
    {        
        outMessage.setOnAction(e -> 
        {
            sendMessage();
        });
    }
    
    /**
     * setup the clickable buttons and the listener
     */
    public void setupButtons()
    {
        disconnectButton = new Button();
        disconnectButton.setText("Disconnect");
        disconnectButton.setOnAction(new EventHandler<ActionEvent>()                    
        {            
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Sending Disconnect message");
                //fire off dc message, actual dc is handled by readFromServer
                out.println("disconnect " + userName);
                out.flush();
            }
        });
        
        sendButton = new Button();
        sendButton.setText("Send");
        sendButton.setOnAction(new EventHandler<ActionEvent>()                    
        {            
            @Override
            public void handle(ActionEvent event)
            {
                sendMessage();
            }
        });
    }
    
    /**
     * sends message tot he chat server
     */
    public void sendMessage()
    {
        out.println(outMessage.getText());            
        out.flush();
        in.updateGui(userName + ": " + outMessage.getText());
    }
    
    /**
     * set up our networking elements, the socket, and the printwriter
     * @throws IOException 
     */
    public static void setUpNetwork() throws IOException
    {
        try
        {
            socket = new Socket(ADDRESS, PORT);
            out = new PrintWriter(socket.getOutputStream());
            
            //send connect message to server
            out.println("connect " + userName);
            out.flush();
            
            //launch instance of private class running its own thread
            in = new ReadFromSocket();
            in.start();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ChatClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }                
    }  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);        
    }
    
    /**
     * Class handles downloading text sent from server on its own thread
     */
    private static class ReadFromSocket extends Thread
   {
        Scanner scanner;

        /**
         * constructor instantiates the scanner
         * @throws IOException 
         */
        private ReadFromSocket() throws IOException
        {  
            scanner = new Scanner(socket.getInputStream());
        }
        
        /**
         * run the threaded readFromServer method
         */
        public void run()
        {
            try
            {
                readFromServer();
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(ChatClient.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * creates the loop that watches for input from the server, calls
         * the updateGui function to add string elements received from the 
         * server
         * @throws InterruptedException 
         */
        public void readFromServer() throws InterruptedException
        {
            boolean done = false;
            while (!done && scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                System.out.println("Received: " + line);
                //this allows me to update the gui from a sperate thread               
                updateGui(line);
                if (line.equals("disconnected")) done = true;
                    System.out.println("Received disconnect message");
            }
            scanner.close();
            updateGui("Server has disconnected");
        }
        
        /**
         * allows updating the gui from outside its thread
         * @param update String
         */
        public void updateGui(String update)
        {
            Platform.runLater(new Runnable() 
            {
                @Override public void run() 
                {
                    updateConversation(update);     
                }                
            });
        }        
   }    
}
