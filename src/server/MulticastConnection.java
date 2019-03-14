/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.GameControl;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    
    public void sendEnd(String winner, GameControl game) throws IOException, InterruptedException{
        String myMessage = "";
        if (winner.equals("EMPATE")){
            myMessage = "*EMPATE*";
        } 
        else{
            System.out.println("WINNER: " + winner);
            myMessage = "*"+winner+"*";
        }
        byte [] m = myMessage.getBytes();
        DatagramPacket messageOut = new DatagramPacket(m, m.length, group, multicastPort);
        s.send(messageOut);
        
        //TimeUnit.SECONDS.sleep(10);
        //System.out.println("REINICIANDO");
        //game.setStarted(true);
        //game.start();
    }
    
    public void sendMonster(int random){
        
   	try { 
            //s.joinGroup(group);
           // String myMessage=Integer.toString((new Random().nextInt(8))+1);
            String myMessage = Integer.toString(random);
            System.out.println("Monstruo a enviar: " + myMessage);
            byte [] m = myMessage.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, multicastPort);
            s.send(messageOut);
             
            //s.leaveGroup(group);
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
       
        
    }
}
