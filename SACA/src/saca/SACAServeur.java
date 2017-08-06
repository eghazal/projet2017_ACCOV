package saca;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SACAServeur {

    //Static variables.
    static String InvalideValue = "INVALIDE";
    static String ValideValue = "VALIDE";
    static List<String> ValidActions = Arrays.asList("A", "C", "V");

    static Scanner scanner = new Scanner(System.in);
    
    static ServerSocket avion_socket;
    static ServerSocket radar_socket;
    
    
    static Radar radar;
    public static Map<String, AvionEntity> avionArray = new HashMap<String, AvionEntity>();

    public static void main(String[] args) throws Exception {

        try {
            avion_socket = new ServerSocket(2009);
            radar_socket=new ServerSocket(2010);
              
            
            create_avions(10);
            Controlleur ctrl = new Controlleur(avionArray);
            radar=create_radar(radar_socket); 

            System.out.println("Saca est pret !");

            while (true) {
                String info=recevoire_caracteristiques_avion();
                //envoyer_caracteristiques_radar(info);
                //radar.recevoire_caracteristiques();
                ctrl.controlleur_commande();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        // TODO code application logic here
    }

    static void create_avions(int count) {
        for (int i = 0; i < count; i++) {
            AvionEntity av = new AvionEntity(avion_socket);
            av.start();
            avionArray.put(av.numero_vol.toString(), av);
        }
    }
    static Radar create_radar(ServerSocket socket){
        Radar r=new Radar(socket);
        r.start();
        return r;
    }
    static String recevoire_caracteristiques_avion() throws Exception {

        String caracteristiques;
        Socket clientSocket = new Socket("localhost", 2009);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        caracteristiques = inFromServer.readLine();
        //envoyer_radar(caracteristiques);
        System.out.println("De Gestionaire : " + caracteristiques);
        clientSocket.close();
        return caracteristiques;
    }
    
    
   
    static void envoyer_caracteristiques_radar(String caracteristiques) {
        
        try {
            Socket socketDeConnection;
            ServerSocket socketDeCommunication;
            socketDeCommunication=radar_socket;
            socketDeConnection = socketDeCommunication.accept();
             
            DataOutputStream outToClient = new DataOutputStream(socketDeConnection.getOutputStream());
            outToClient.writeUTF(caracteristiques);
            
             if (socketDeConnection.isConnected()) {
                socketDeConnection.close();
            }
            
        } catch (IOException ex) {
            System.out.println("Error in send line 96");
        }
        // fonction à implémenter qui envoie l'ensemble des caractéristiques
        // courantes de l'avion au gestionnaire de vols
    }
    
    
    
}
