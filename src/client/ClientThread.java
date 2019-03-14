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
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.AccessException;
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
public class ClientThread extends Thread{
    private String pathPolicy;
    private String ipRMI;
    private Socket sTCP;
    
    
    public ClientThread (String pathPolicy, String ipRMI){
        this.pathPolicy = pathPolicy;
        this.ipRMI = ipRMI;
        Socket sTCP = null;
    }
    
    public void run(){
        try {
            System.setProperty("java.security.policy",pathPolicy);

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            //RMI Load
            String name = "GameInfo";
            Registry registry = LocateRegistry.getRegistry(ipRMI); // server's ip address args[0]
            GameInfo game = (GameInfo) registry.lookup(name);
            GameData gameData = game.getInfo();

            //TCP Socket for the monster hit send - Debe pasarse a una función de la GUI!
            //Registro cliente nuevo
            //Socket sTCP = null;
            int serverTCPPort = gameData.getPortTCP();
            InetAddress serverTCPIp = gameData.getIpTCP();
            sTCP = new Socket(serverTCPIp, serverTCPPort);
            DataInputStream in = new DataInputStream(sTCP.getInputStream());
            DataOutputStream out = new DataOutputStream(sTCP.getOutputStream());
            out.writeUTF("registro");

            MulticastThread multiThread;
            String data = in.readUTF();
            
            
            if (data.equals("registrado")){
                System.out.println("Received: " + data) ;
                //Multicast 
                GUI gui = new GUI(sTCP); 
                multiThread = new MulticastThread(gameData.getPortMulticast(), gameData.getIpMulticast(), gui, this);
                multiThread.start();
            }

            //La conexión debe permanecer abierta!
            //sTCP.close();

        } catch (RemoteException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
