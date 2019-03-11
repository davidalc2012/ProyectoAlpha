/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author agnar
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

//Interface for the RMI service. Get the info for the game
public interface GameInfo extends Remote {
	// Returns the information for the game (IP, Port)
	public GameData getInfo ( ) throws RemoteException;
}

