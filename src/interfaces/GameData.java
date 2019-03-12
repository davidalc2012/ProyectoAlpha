/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agnar
 */

//Class that saves the information for the game
public class GameData implements Serializable{
    private InetAddress ipTCP;
    private InetAddress ipMulticast;
    private int portMulticast;
    private int portTCP;
    
    public GameData(String ipTCP, String ipMulticast, int portTCP, int portMulticast) {
        try {
            this.ipTCP = InetAddress.getByName(ipTCP);
            this.ipMulticast = InetAddress.getByName(ipMulticast);
            this.portMulticast = portMulticast;
            this.portTCP = portTCP;
        } catch (UnknownHostException ex) {
            Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InetAddress getIpTCP() {
        return ipTCP;
    }
    
    public InetAddress getIpMulticast() {
        return ipMulticast;
    }

    public int getPortMulticast() {
        return portMulticast;
    }
    
    public int getPortTCP() {
        return portTCP;
    }
}
