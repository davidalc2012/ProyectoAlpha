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
import interfaces.GameData;
import java.rmi.RemoteException;
import interfaces.GameInfo;

//Implements the interface for the RMI service
public class GameInfoServer implements GameInfo{
    public static String ipServerTCP="localhost";
    public static String ipServerMulticast="228.5.6.7";
    
    public GameInfoServer() throws RemoteException{
        super();
    }
    
    @Override
    public GameData getInfo( ) throws RemoteException {
        return new GameData(ipServerTCP, ipServerMulticast, 4000, 7896);
    }
}
