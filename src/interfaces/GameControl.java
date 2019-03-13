/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import server.MulticastConnection;

/**
 *
 * @author agnar
 */
public class GameControl {
    private ArrayList<Player> playersArray;
    private MulticastConnection multicast;
    private int count;
    private boolean started; 
    private static int ROUNDS = 10;
    
    public GameControl(){
        playersArray = new ArrayList<Player>();
        count = 0;
        started = true;
    }
    
    public void add(Player player){
        System.out.println(player);
        playersArray.add(player);

    }

    @Override
    public String toString() {
        return "GameControl{" + "playersArray=" + playersArray + '}';
    }
    
    public void sendMonster() throws InterruptedException{
        //TimeUnit.SECONDS.sleep(10);
        //    for (int i = 0; i<10;i++){
        //TimeUnit.SECONDS.sleep(4);
        
        multicast.sendMonster();
        count++;
        //    }
    }
    
    public void setMulticast(MulticastConnection multicast){
        this.multicast = multicast;
    }
    
    public void start() throws InterruptedException{
        if (started){
            if(count<ROUNDS){
                sendMonster();
            }
            else {
                started = false;
            }
        }
        else {
            count=0;
            started = true;
            //
        }
        
    }
    
    public void playerPoint(Socket socketAddress){
        int aux = playersArray.indexOf(new Player(socketAddress));
        if (aux!=-1){
            playersArray.get(aux).point();
        }
    }
}
