/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
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
    private int monstActual;
    
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
        return "GameControl" + "playersArray=" + playersArray + '}';
    }
    public int random(){
        this.monstActual = 1 + new Random().nextInt(9);
        return this.monstActual;
    }
    public void sendMonster() throws InterruptedException{
        //TimeUnit.SECONDS.sleep(10);
        //    for (int i = 0; i<10;i++){
        //TimeUnit.SECONDS.sleep(4)
        this.random();
        System.out.println(playersArray.toString());
        multicast.sendMonster(monstActual);
        count++;
        //    }
    }

    public int getMonstActual() {
        return monstActual;
    }
    
    
    public void setMulticast(MulticastConnection multicast){
        this.multicast = multicast;
    }
    
    public void start() throws InterruptedException, IOException{
        if (started){
            if(count<ROUNDS){
                sendMonster();
            }
            else {
                int max = 0;
                boolean empate = false;
                Player winner = new Player();
                for (int i=0;i<playersArray.size();i++){
                    if (playersArray.get(i).getScore()==max){
                        empate=true;
                    }
                    if(playersArray.get(i).getScore()>max){
                        winner = playersArray.get(i);
                        max = winner.getScore();
                        empate=false;
                    }
                }
                if (empate){
                    multicast.sendEnd("EMPATE", this);
                }
                else {
                    multicast.sendEnd(String.valueOf(winner.getSocket().getLocalPort()), this);
                }
                count=0;
                this.setZeros();
                started = false;
                
            }
        }
        /*else {
            count=0;
            started = true;
            //
        }*/
        
    }
    
    public void setZeros(){
        for (int i=0;i<playersArray.size();i++){
            playersArray.get(i).setScore(0);
        }
    }
        
    public void playerPoint(Socket socketAddress){
        int aux = playersArray.indexOf(new Player(socketAddress));
        if (aux!=-1){
            playersArray.get(aux).point();
        }
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
}
