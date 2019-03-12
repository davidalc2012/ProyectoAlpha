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

/**
 *
 * @author agnar
 */
public class MulticastTest {
    
    public static void main(String[] args) {
            //Multicast Test!
        MulticastConnection multicast = new MulticastConnection();
        multicast.sendMonster();
	
    }
}
