/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 *
 * @author agnar
 */
public class MulticastConnection{
    private static int multicastPort = 6789; 
    private static String ipMulticast = "228.5.6.7";
    //ServerSocket listenSocket;
    InetAddress group;
    MulticastSocket s;
    
    public MulticastConnection (){
      /*  try {
           // this.listenSocket = new ServerSocket(multicastPort);
            InetAddress group = InetAddress.getByName(ipMulticast);
            s = new MulticastSocket(multicastPort);
            s.joinGroup(group);
            System.out.println("Messages' TTL (Time-To-Live): "+ s.getTimeToLive());
        } catch (SocketException e){
             System.out.println("Socket: " + e.getMessage());
	 }
         catch (IOException e){
             System.out.println("IO: " + e.getMessage());
         }
	 finally {
            //if(s != null) s.close();
        }*/
    }   
    
    public void sendMonster(){
        
        
   	try { 
            InetAddress group = InetAddress.getByName(ipMulticast); // destination multicast group 
	    s = new MulticastSocket(multicastPort);
	    s.joinGroup(group); 
            System.out.println("Messages' TTL (Time-To-Live): "+ s.getTimeToLive());
            String myMessage="HelloMulticast";
            byte [] m = myMessage.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, multicastPort);
            s.send(messageOut);

           // s.leaveGroup(group);
            
        }
         catch (SocketException e){
             System.out.println("Socket: " + e.getMessage());
	 }
         catch (IOException e){
             System.out.println("IO: " + e.getMessage());
         }
	 finally {
            if(s != null) s.close();
        }
        
        
        
    }
}
