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
import javax.swing.JFrame;
import server.MulticastConnection;
import server.TCPThread;

/**
 *
 * @author agnar
 */
public class MulticastThread extends Thread {
    private static int serverPort; 
    private InetAddress ipAddress;
    private GUI gui;
    
    public MulticastThread (int serverPort, InetAddress ipAddress, GUI gui) throws IOException{
        this.serverPort = serverPort;
        this.ipAddress = ipAddress;
        this.gui = gui;
    }
    
    public void run(){
        try {
            MulticastSocket listenSocket = new MulticastSocket(serverPort);
            listenSocket.joinGroup(ipAddress); 

            while(true) {
	    	byte[] buffer = new byte[20];
                System.out.println("Waiting for messages MULTICAST");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

                listenSocket.receive(messageIn);
                String data = new String(messageIn.getData());
                System.out.println("Message: " + data);
                if(data.charAt(0)=='*'){
                    gui.win(data);
                }
                else {
                    String number = String.valueOf(data.charAt(0));
                    gui.markOne(Integer.valueOf(number));
                }
                //listenSocket.leaveGroup(group);		

            }
        } catch (IOException ex) {
            Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}