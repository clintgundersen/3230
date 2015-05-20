/*
 * Multi Thread Server Demo software
 * ref: ITJP 10ed. by Liang
 * modified for NetBeans and to comply with
 * the CS3250 Style Guide
 * by Charles Knadler
 */

package multithreadserver;

import java.io.*;
import java.net.*; // networking classes
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * This is a JavaFX program created by Daniel Liang
 * An implementation of client/server software with multithreading.
 * The clients sends the radius of a circle and the server
 * returns the area of the circle.
 *
 * @version 1.01 2014-10-25
 * @author Charles Knadler
 * Developed on NetBeans and modified for CS 3250-601
 */
public class MultiThreadServer extends Application
{
    // avoid magic numbers
    private static final int SCENE_WIDTH = 450;
    private static final int SCENE_HEIGHT = 200;
    private static final int PORT = 8000;
    
    //create a text area to display messages
    TextArea ta = new TextArea();
    
    // declare and initial client number
    private int clientNo = 0;
    
    @Override
    public void start(Stage primaryStage)
    {
        //--** Create Server UI
        
        // create a scene and place in stage
        Scene scene = new Scene(new ScrollPane(ta), 
            SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("MultiThread Server");
        primaryStage.setScene(scene);
        primaryStage.show(); // display the stage
        
        //--** create a separate server thread
        new Thread(() ->
        {
           try
           {
             // create a server socket
             ServerSocket serverSocket = new ServerSocket(PORT);
             // update server display
             ta.appendText("MultiThreadServer started at " +new Date() + '\n'); 
                     
             while (true)
             {
                //--** connect client
             
                // listen for a new connection request
                Socket socket = serverSocket.accept();
             
                // increment client number
                clientNo++;
             
                Platform.runLater(() ->
                {
                    // display client number
                    ta.appendText("Starting thread for client " + clientNo +
                         " at " + new Date() + '\n');
                 
                    // Find client's host name and IP address
                    InetAddress inetAddress = socket.getInetAddress();
                    ta.appendText("Client " + clientNo + "'s host name is "
                        + inetAddress.getHostName() + '\n');
                    ta.appendText("Client " + clientNo + "'s IP Address is "
                        + inetAddress.getHostAddress() + '\n');
                });
             
                // create and start a new thread for the connection
                new Thread(new HandleAClient(socket)).start();
             }
            }// end try
            catch (IOException ex)
            {
               System.err.println(ex);
            }
        }).start(); //end new thread
    }
    
    // Define the thread class for new connections
    class HandleAClient implements Runnable
    {
        private Socket socket; // a connected socket
        
        /** construct a thread */
        public HandleAClient(Socket socket)
        {
            this.socket = socket;
        }
        
        /** run a thread */
        public void run()
        {
            try
            {
                // create data streams
                DataInputStream inputFromClient = new DataInputStream(
                socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                socket.getOutputStream());
                
                // serve the client
                while (true)
                {
                    //get the radius
                    //String clientMessage = inputFromClient.readDouble();
                    String clientMessage = inputFromClient.readUTF();
                    
                    
                    // respond to client
                    String outMessage = "got it";
                    outputToClient.writeBytes(outMessage);
                    
                    //--** update GUI
                    Platform.runLater(() ->
                    {
                        ta.appendText("radius received from client: " + 
                            clientMessage + "\n");    
                        ta.appendText("Area found: " + outMessage + '\n');
                    });
                }            } // end try
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}

