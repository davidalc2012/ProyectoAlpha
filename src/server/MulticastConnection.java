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
import java.net.SocketException;
import java.util.Random;

/**
 *
 * @author agnar
 */


/* EL PROBLEMA QUE TIENE ESTA CLASE ES QUE NO FUNCIONA SI DIVIDES EL CÓDIGO DE LA CONEXIÓN MULTICAST
ENTONCES SI TODO LO PONES DENTRO DE LA FUNCIÓN sendMonster LA CONEXIÓN SI FUNCIONA*/
public class MulticastConnection{
    private static int multicastPort = 6789; 
    private static String ipMulticast = "228.5.6.7";
    InetAddress group;
    MulticastSocket s;
    
    public MulticastConnection (){
        try {
           // this.listenSocket = new ServerSocket(multicastPort);
            group = InetAddress.getByName(ipMulticast);
            s = new MulticastSocket(multicastPort);
            s.joinGroup(group);
        } catch (SocketException e){
             System.out.println("Socket: " + e.getMessage());
	}
         catch (IOException e){
             System.out.println("IO: " + e.getMessage());
        }finally {
            //if(s != null) s.close();
        }   
    }
    
    public String sendMonster(){
        
   	try { 
            //s.joinGroup(group);
            String myMessage=Integer.toString((new Random().nextInt(8))+1);
            System.out.println(myMessage);
            byte [] m = myMessage.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, multicastPort);
            s.send(messageOut);
             
            //s.leaveGroup(group);
            return myMessage;
        }
         catch (SocketException e){
             System.out.println("Socket: " + e.getMessage());
	 }
         catch (IOException e){
             System.out.println("IO: " + e.getMessage());
         }
	 finally {
            //if(s != null) s.close();
        }
        
        return "";
        
    }
}
