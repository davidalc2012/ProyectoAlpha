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
    private int playerPoints;
    private Player playerMax;
    //private int count;
    private boolean started;
    private static int POINTS = 5;
    
    //private static int ROUNDS = 5;
    private int monstActual;
    private boolean ended;
    private double startTime;
    
    public GameControl(){
        playersArray = new ArrayList<Player>();
        playerPoints=0;
        //count = 0;
        started = true;
        ended = false;
        
    }

    public boolean getEnded(){
        return ended;
    }
    
    public ArrayList<Player> getPlayersArray(){
        return playersArray;
    }
     
    public void setPlayersArray(ArrayList<Player> array){
        this.playersArray = array;
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
        //playerPoints++;
        //    }
    }

    public int getMonstActual() {
        return monstActual;
    }
    
    
    public void setMulticast(MulticastConnection multicast){
        this.multicast = multicast;
    }
    
    public boolean start() throws InterruptedException, IOException{
        
        if (started){
            if(playerPoints<POINTS){
                sendMonster();
                started=false;
                startTime = System.currentTimeMillis();
                return true;
            }
            else {
                int max = 0;
                boolean empate = false;
                System.out.println(playerMax.getSocket().getPort());
                multicast.sendEnd(String.valueOf(playerMax.getSocket().getPort()), this);
                playerPoints=0;
                this.setZeros();
                started = false;
                ended = true;
                return false;
            }
        }
        
        
        
        
        /*if (started){
            if(count<ROUNDS){
                sendMonster();
                started=false;
                startTime = System.currentTimeMillis();
                return true;
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
                    multicast.sendEnd(String.valueOf(winner.getSocket().getPort()), this);
                }
                count=0;
                this.setZeros();
                started = false;
                ended = true;
                return false;
            }
        }
        */
        if(System.currentTimeMillis()-startTime>10000){
            started=true;
        }
        return false;
    }
    
    public void setZeros(){
        for (int i=0;i<playersArray.size();i++){
            playersArray.get(i).setScore(0);
        }
    }
        
    public void playerPoint(Socket socketAddress){
        int auxPlayer = playersArray.indexOf(new Player(socketAddress));
        if (auxPlayer!=-1){
            int auxPoints = playersArray.get(auxPlayer).point();
            if (auxPoints>playerPoints){
                playerPoints=auxPoints;
                playerMax=playersArray.get(auxPlayer);
            }
        }
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
}
