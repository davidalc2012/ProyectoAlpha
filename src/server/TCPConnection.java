/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author agnar
 */

public class TCPConnection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public TCPConnection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out =new DataOutputStream(clientSocket.getOutputStream());
            
            
        } catch(IOException e)  {System.out.println("Connection:"+e.getMessage());}
    }

    @Override
    public void run(){
        try {			                 // an echo server
            String data = in.readUTF();	  
            if(data.equals("registro")){
                System.out.println("Nuevo cliente en el puerto: " + clientSocket.getPort());
                out.writeUTF(data);
            }else{
            System.out.println("Message received from: " + clientSocket.getRemoteSocketAddress());
            out.writeUTF(data);
            }

        } 
        catch(EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        } 
        catch(IOException e) {
            System.out.println("IO:"+e.getMessage());
        } /*finally {
            try {
               clientSocket.close();
            } catch (IOException e){
                System.out.println(e);
            }
        }*/
    }
}