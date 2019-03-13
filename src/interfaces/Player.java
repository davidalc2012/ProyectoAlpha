/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.net.Socket;
import java.util.Objects;

/**
 *
 * @author agnar
 */
public class Player {
    private int score;
    private Socket socket;

    public Player(Socket socket, int score) {
        this.score = score;
        this.socket = socket;
    }
        
    public Player(Socket socket) {
        this.socket = socket;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public void point(){
        score++;    
    }
    
    @Override
    public String toString() {
        return "Player{" + "score=" + score + ", socket=" + socket + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.socket, other.socket)) {
            return false;
        }
        return true;
    }
    
    
}
