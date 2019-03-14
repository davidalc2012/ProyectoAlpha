/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.GameControl;
import interfaces.Player;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agnar
 */
public class TCPThread extends Thread {
    
    private static int serverPort = 7896; 
    private ServerSocket listenSocket;
    private GameControl gameControl;
    private ArrayList<TCPConnection> clientSockets;
    
    public TCPThread (GameControl gameControl) throws IOException{
         this.listenSocket = new ServerSocket(serverPort);
         this.gameControl = gameControl;
         clientSockets = new ArrayList<TCPConnection>();
    }
    
    public void run(){
        while(true) {
            try {
                System.out.println("Waiting for players...");
                Socket clientSocket = listenSocket.accept(); 
                TCPConnection tcpConnection = new TCPConnection(clientSocket, gameControl);
                clientSockets.add(tcpConnection);
                tcpConnection.start();
            } catch (IOException ex) {
                Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setGameControl(GameControl gameControl){
        this.gameControl = gameControl;
        for (int i = 0; i<clientSockets.size();i++){
            clientSockets.get(i).setGameControl(gameControl);
        }
    }
    
}
