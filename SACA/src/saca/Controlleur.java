/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saca;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static saca.SACAServeur.InvalideValue;
import static saca.SACAServeur.scanner;

/**
 *
 * @author eghaz
 */
public class Controlleur {

    static String InvalideValue = "INVALIDE";
    static String ValideValue = "VALIDE";
    static Scanner scanner = new Scanner(System.in);
    static List<String> ValidActions = Arrays.asList("A", "C", "V");

    static Map<String, AvionEntity> avionArray;

    public Controlleur(Map<String, AvionEntity> avionArray) {
        this.avionArray = avionArray;
    }

    public static void controlleur_commande() {
        try {
            System.out.println();
            //Attendre 2 seconds pour que le controlleur entre aucune valeur.
            int x = 2; // wait 2 seconds at most

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            long startTime = System.currentTimeMillis();
            System.out.println("Pour modifier le comportement d'une avion entrer ID:valeur, pour rien modifier entrer \"NA\".");
            System.out.println();
            while ((System.currentTimeMillis() - startTime) < x * 1000
                    && !in.ready()) {
            }
            if (in.ready()) {
                String line = in.readLine();
                String valideInput = valider_input(line);
                switch (valideInput) {
                    case "NA":
                        return;
                    case "INVALIDE":
                        System.out.println("Id d'avion invalide.");
                        return;
                    default:
                        System.out.println("Entrer Cle : Valeur (A pour altitude,C pour cap et V pour vitesse))");
                        System.out.println();
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

            }

        } catch (Exception e) {
            // System.in has been closed

            System.out.println("Valeur invalide.");
        }
    }

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
}
