/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.MulticastConnection;
import server.TCPThread;

/**
 *
 * @author agnar
 */
public class MulticastThread extends Thread {
    private static int serverPort; 
    private InetAddress ipAddress;
    
    public MulticastThread (int serverPort, InetAddress ipAddress) throws IOException{
        this.serverPort = serverPort;
        this.ipAddress = ipAddress;
    }
    
    public void run(){
        try {
            MulticastSocket listenSocket = new MulticastSocket(serverPort);
            listenSocket.joinGroup(ipAddress); 

            while(true) {
	    	byte[] buffer = new byte[1];
                System.out.println("Waiting for messages MULTICAST");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

                listenSocket.receive(messageIn);

                System.out.println("Message: " + new String(messageIn.getData()));

                //listenSocket.leaveGroup(group);		

                
                /*
                
                Random x = new Random();
                int n = 1 + x.nextInt(9);
                System.out.println("Monstruo en la posición: " + n );
                Socket clientSocket = listenSocket.accept(); 
                MulticastConnection multConnection = new MulticastConnection();
                multConnection.sendMonster();
                */
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}