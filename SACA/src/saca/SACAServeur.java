package saca;

import java.io.BufferedReader;
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
    static ServerSocket socket;
    public static Map<String, AvionEntity> avionArray = new HashMap<String, AvionEntity>();

    public static void main(String[] args) throws Exception {

        try {
            socket = new ServerSocket(2009);

            create_avions(10);
            Controlleur ctrl = new Controlleur(avionArray);

            System.out.println("Saca est pret !");

            while (true) {
                recevoire_caracteristiques();
                // controlleur_commande();
                ctrl.controlleur_commande();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        // TODO code application logic here
    }

    static void create_avions(int count) {
        for (int i = 0; i < count; i++) {
            AvionEntity av = new AvionEntity(socket);
            av.start();
            avionArray.put(av.numero_vol.toString(), av);
        }
    }

    static void recevoire_caracteristiques() throws Exception {

        String caracteristiques;
        Socket clientSocket = new Socket("localhost", 2009);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        caracteristiques = inFromServer.readLine();
        System.out.println("De Gestionaire : " + caracteristiques);
        clientSocket.close();
    }

    /* Le code suivant est fait dans la class controlleur.
    
    ///Valider si l'utilisateur a inserer NA pour aucune modification, une avion ID Valide.
    static String valider_input(String input) {
        String result = "NA";
        String[] splitResult = input.split(":");
        //Verifier aucune action.
        if (input.equals("NA")) {
            return result;
        } //Verifier si l'avion existe.
        else if (input.contains("ID:")) {
            result = splitResult.length > 0 ? splitResult[1] : InvalideValue;
            if (avionArray.containsKey(result)) {
                return result;
            }
            return InvalideValue;
        } //Verifier action.
        else if (splitResult.length == 2) {
            if (ValidActions.contains(splitResult[0])) {
                return ValideValue;
            }
        }

        return InvalideValue;
    }

    static void controlleur_commande() {
        try {
            System.out.println();
            //Attendre 2 seconds pour que le controlleur entre aucune valeur.
            
            System.out.println("Pour modifier le comportement d'une avion entrer ID:valeur, pour rien modifier entrer \"NA\".");
            String line = scanner.nextLine();
            String valideInput = valider_input(line);
            switch (valideInput) {
                case "NA":
                    return;
                case "INVALIDE":
                    System.out.println("Id d'avion invalide.");
                    return;
                default:
                    System.out.println("Entrer Cle : Valeur (A pour altitude,C pour cap et V pour vitesse))");
                    AvionEntity avToModify = avionArray.get(valideInput);
                    line = scanner.nextLine();
                    if (valider_input(line).equals(ValideValue)) {
                        String[] action = line.split(":");
                        String comportement = action[0];
                        int valeur = Integer.parseInt(action[1]);
                        changer_avion_comportement(avToModify, comportement, valeur);
                        avToModify.afficher_donnees();
                    }
                    break;

            }

        } catch (Exception e) {
            // System.in has been closed

            System.out.println("Valeur invalide.");
        }
    }

    static void changer_avion_comportement(AvionEntity avion, String comportement, int valeur) {
        switch (comportement) {
            case "C":
                avion.changer_cap(valeur);
                break;
            case "V":
                avion.changer_vitesse(valeur);
                break;
            case "A":
                avion.changer_altitude(valeur);
                break;
        }
    }
     */
}
