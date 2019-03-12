/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CVASQUEZP
 */
public class MulticastThread extends Thread {
     private static int serverPort = 7896; 
    private ServerSocket listenSocket;
    
    public MulticastThread () throws IOException{
      //   this.listenSocket = new ServerSocket(serverPort);
    }
    
    public void run(){
        while(true) {
            try {
                Random x = new Random();
                int n = 1 + x.nextInt(9);
                System.out.println("Monstruo en la posición: " + n );
                Socket clientSocket = listenSocket.accept(); 
                MulticastConnection multConnection = new MulticastConnection();
                multConnection.sendMonster();
            } catch (IOException ex) {
                Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
