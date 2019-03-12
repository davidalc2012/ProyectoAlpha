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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agnar
 */



public class ClientLauncher {
    
    public static void main(String[] args) {
            try {
                // TODO code application logic here
                //new GUI();
                String ipServer = "localhost";
                
                //RMI Service localization
                //   String path = "file:/Users/agnar/NetBeansProjects/ProyectoAlpha/src/client/client.policy";
                String path = "file:/Users/CVASQUEZP/ProyectoAlpha/src/client/client.policy";
                System.setProperty("java.security.policy",path);
                
                if (System.getSecurityManager() == null) {
                    System.setSecurityManager(new SecurityManager());
                }
                //RMI Load
                String name = "GameInfo";
                Registry registry = LocateRegistry.getRegistry(ipServer); // server's ip address args[0]
                GameInfo game = (GameInfo) registry.lookup(name);
                GameData gameData = game.getInfo();
                
                System.out.println(gameData.getIpTCP());
                System.out.println(gameData.getIpMulticast());
                System.out.println(gameData.getPortMulticast());
                System.out.println(gameData.getPortTCP());
                
                
                //TCP Socket for the monster hit send - Debe pasarse a una función de la GUI!
                //Registro cliente nuevo
                Socket sTCP = null;
                int serverTCPPort = gameData.getPortTCP();
                InetAddress serverTCPIp = gameData.getIpTCP();
                sTCP = new Socket(serverTCPIp, serverTCPPort);
                DataInputStream in = new DataInputStream(sTCP.getInputStream());
                DataOutputStream out = new DataOutputStream(sTCP.getOutputStream());
                out.writeUTF("registro");
                
                String data = in.readUTF();
                System.out.println("Received: " + data) ;
                
                //La conexión debe permanecer abierta!
                sTCP.close();
                
                
            /*    //Multicast subscription
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
                
                */
                
                
                
            }
            catch (IOException ex){
                Logger.getLogger(ClientLauncher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
            Logger.getLogger(ClientLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            

    }
    
}
