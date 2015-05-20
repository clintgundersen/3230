/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package takehomefinal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author clint
 */
public class TakeHomeFinal
{
    private static final String LOCAL_HOST = "localhost";
    private static final int PORT = 8000;

   public static void main(String[] args) throws IOException
   {
      try (Socket s = new Socket(LOCAL_HOST, PORT))
      {
         PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
         Scanner in = new Scanner(s.getInputStream());

         
         while (in.hasNextLine())
         {
            String line = in.nextLine();
            System.out.println(line);
            printWriter.println("Chat Client Test Step");
            printWriter.println("Quit");
            //System.out.println(line);
         }         
      }
   }
   

}
    

