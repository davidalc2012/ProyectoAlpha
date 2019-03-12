/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author agnar
 */
import interfaces.GameInfo;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerLauncher {
    
    public static void main(String[] args) {
        
        //set policy for the RMI Service
       // String path = "file:/Users/agnar/NetBeansProjects/ProyectoAlpha/src/server/server.policy";
         String path = "file:/Users/CVASQUEZP/ProyectoAlpha/src/client/client.policy";
        System.setProperty("java.security.policy",path);

        if (System.getSecurityManager() == null) {
           System.setSecurityManager(new SecurityManager());
        }
        
        try {

            // start the rmiregistry 
            LocateRegistry.createRegistry(1099);   /// default port
            
            //Register the RMI method
            String name = "GameInfo";
            GameInfoServer engine = new GameInfoServer();
            GameInfo stub = (GameInfo) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
            
            //TCP Thread initialization
            TCPThread tcpThread = new TCPThread();
            tcpThread.start();
           
            //multicast Thread 
        /*    MulticastThread multThread = new MulticastThread();
            multThread.start();
*/
            
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
        
      
        
    }        
}
