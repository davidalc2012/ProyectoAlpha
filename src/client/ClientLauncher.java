/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.GameData;
import interfaces.GameInfo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author agnar
 */



public class ClientLauncher {
    
    public static void main(String[] args) {
        // TODO code application logic here
        new GUI();
        String ipServer = "localhost";
        
        //RMI Service localization
        String path = "file:/Users/agnar/NetBeansProjects/ProyectoAlpha/src/client/client.policy";
        System.setProperty("java.security.policy",path);
        
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            //RMI Load
            String name = "GameInfo";
            Registry registry = LocateRegistry.getRegistry(ipServer); // server's ip address args[0]
            GameInfo game = (GameInfo) registry.lookup(name);
            GameData gameData = game.getInfo();
            
            System.out.println(gameData.getIpTCP());
            System.out.println(gameData.getIpMulticast());
            System.out.println(gameData.getPortMulticast());
            System.out.println(gameData.getPortTCP());
                    
            
            
            
            
            //Multicast subscription
            MulticastSocket sMulticast =null;
            int serverMulticastPort = gameData.getPortMulticast();
            InetAddress serverMulticastIpGroup = gameData.getIpMulticast();
            
            try {
	    	sMulticast = new MulticastSocket(6789);
	   	sMulticast.joinGroup(serverMulticastIpGroup); 

	    	byte[] buffer = new byte[1000];
 	   	for(int i=0; i< 3; i++) {
                    System.out.println("Waiting for messages");
                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
 		    sMulticast.receive(messageIn);
 		    System.out.println("Message: " + (new String(messageIn.getData())).trim()+ " from: "+ messageIn.getAddress());
  	     	}
	    	sMulticast.leaveGroup(serverMulticastIpGroup);		
 	    }
            catch (SocketException e){
                System.out.println("Socket: " + e.getMessage());
            }
            catch (IOException e){
                System.out.println("IO: " + e.getMessage());
            }
            finally {
               if(sMulticast != null) sMulticast.close();
            }
            
            
            
            
            
            
            //TCP Socket for the monster hit send - Debe pasarse a una función de la GUI!
            Socket sTCP = null;
            int serverTCPPort = gameData.getPortTCP();
            InetAddress serverTCPIp = gameData.getIpTCP();
            sTCP = new Socket(serverTCPIp, serverTCPPort);       
            DataInputStream in = new DataInputStream(sTCP.getInputStream());
            DataOutputStream out = new DataOutputStream(sTCP.getOutputStream());
            out.writeUTF(String.valueOf(serverTCPPort));

            String data = in.readUTF();	      
            System.out.println("Received: " + data) ;      
            
            //La conexión debe permanecer abierta!
            sTCP.close();
            
            
            
            
            
            
            
            

        } catch (Exception e) {
            System.err.println("exception");
            e.printStackTrace();
        }
    }
    
}
