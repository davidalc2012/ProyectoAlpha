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
        // TODO code application logic here

        String ipServer = "localhost";

        //RMI Service localization
        String path = "file:/Users/agnar/NetBeansProjects/ProyectoAlpha/src/client/client.policy";
        //String path = "file:/Users/CVASQUEZP/ProyectoAlpha/src/client/client.policy";

        ClientThread cliente1 = new ClientThread(path, ipServer);
        cliente1.start();
        
    }
    
}
