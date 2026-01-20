package fr.efrei.alsi54;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        String choice = "";
        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            displayMenu();
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Afficher tous les programmeurs");
                    // Call method to display all programmers

                    break;
                case "2":
                    System.out.println("Afficher un programmeur");
                    // Call method to display a programmer
                    break;
                case "3":
                    System.out.println("Supprimer un programmeur");
                    // Call method to delete a programmer
                    break;
                case "4":
                    System.out.println("Ajouter un programmeur");
                    // Call method to add a programmer
                    break;
                case "5":
                    System.out.println("Modifier le salaire");
                    // Call method to modify salary
                    break;
                case "6":
                    System.out.println("Afficher la liste des projets");
                    // Call method to display project list
                    break;
                case "7":
                    System.out.println("Obtenir la liste des programmeurs qui travaillent sur le même projet");
                    // Call method to get programmers on the same project
                    break;
                case "8":
                    System.out.println("Quitter le programme");
                    sc.close();
                    exit = true;
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }

        }

    }

    private static void displayMenu() {
        System.out.println("********* MENU *************");
        System.out.println("1. Afficher tous les programmeurs");
        System.out.println("2. Afficher un programmeur");
        System.out.println("3. Supprimer un programmeur");
        System.out.println("4. Ajouter un programmeur");
        System.out.println("5. Modifier le salaire");
        System.out.println("6. Afficher la liste des projets (Intitulé, Date de début, Date de fin prévue, Etat, …)");
        System.out.println("7. Obtenir la liste des programmeurs qui travaillent sur le même projet");
        System.out.println("8. Quitter le programme");
    }
}
