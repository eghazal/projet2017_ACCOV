/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saca;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import static saca.SACAServeur.radar_socket;

/**
 *
 * @author eghaz
 */
public class Radar extends Thread{
    
    
      Socket socketDeConnection;
      ServerSocket socketDeCommunication;
            
    public Radar(ServerSocket s){
        socketDeCommunication= s;
    }
    
    public void recevoire_caracteristiques() throws Exception {
          
        socketDeCommunication=radar_socket;
        socketDeConnection = socketDeCommunication.accept();
        String caracteristiques;
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socketDeConnection.getInputStream()));
        caracteristiques = inFromServer.readLine();
        System.out.println("Du Radar : " + caracteristiques);
        socketDeConnection.close();
    }
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {

        try {
            while (true) {
                recevoire_caracteristiques();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
