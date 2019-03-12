/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agnar
 */
public class TCPThread extends Thread {
    
    private static int serverPort = 7896; 
    private ServerSocket listenSocket;
    
    public TCPThread () throws IOException{
         this.listenSocket = new ServerSocket(serverPort);
    }
    
    public void run(){
        while(true) {
            try {
                System.out.println("Waiting for players...");
                Socket clientSocket = listenSocket.accept(); 
                TCPConnection tcpConnection = new TCPConnection(clientSocket);
                tcpConnection.start();
            } catch (IOException ex) {
                Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
