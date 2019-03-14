/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.GameControl;
import interfaces.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agnar
 */

public class TCPConnection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    GameControl gameControl;
    Player player;
    
    public TCPConnection (Socket aClientSocket, GameControl gameControl) {
        try {
            clientSocket = aClientSocket;
            this.gameControl = gameControl;
            in = new DataInputStream(clientSocket.getInputStream());
            out =new DataOutputStream(clientSocket.getOutputStream());

            
            
        } catch(IOException e)  {System.out.println("Connection:"+e.getMessage());}
    }

    @Override
    public void run(){
        try {
            while(true){
                System.out.println("Waiting for messages from: " + clientSocket.getPort());
                String data = in.readUTF();	  
                if(data.equals("registro")){
                    System.out.println("Nuevo cliente en el puerto: " + clientSocket.getPort());
                    out.writeUTF("registrado");
                    gameControl.add(new Player(clientSocket, 0));
                }else{
                    System.out.println("Message: " + data + " received from: " + clientSocket.getRemoteSocketAddress());
                 
                    //Si data = monstruo
                    System.out.println("Monstruo actual: " + gameControl.getMonstActual());
                    System.out.println("Respuesta Recibida " +  data);
                    if(data.equals(String.valueOf(gameControl.getMonstActual()))){ //nunca es igual al monstruo es por eso que no entra :(
                        System.out.println("Entro!!!!!!!!!!!!!");
                        gameControl.playerPoint(clientSocket);
                    }
                    gameControl.start();
                //out.writeUTF(data);
                }
            }

        } 
        catch(EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        } 
        catch(IOException e) {
            System.out.println("IO:"+e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(TCPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } /*finally {
            try {
               clientSocket.close();
            } catch (IOException e){
                System.out.println(e);
            }
        }*/
    }
}
