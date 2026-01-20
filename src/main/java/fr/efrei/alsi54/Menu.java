package fr.efrei.alsi54;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final ActionsBDD actions = new ActionsBDDImpl();
    private final Scanner sc = new Scanner(System.in);

    /**
     * Méthode principale qui lance la boucle du menu
     */
    public void demarrer() {
        boolean exit = false;
        while (!exit) {
            afficherOptions();
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    afficherTousLesProgrammeurs();
                    break;
                case "2":
                    afficherUnProgrammeur();
                    break;
                case "3":
                    supprimerProgrammeur();
                    break;
                case "4":
                    ajouterProgrammeur();
                    break;
                case "5":
                    modifierSalaire();
                    break;
                case "6":
                    afficherTousLesProjets();
                    break;
                case "7":
                    afficherProgrammeursParProjet();
                    break;
                case "8":
                    System.out.println("Fermeture du programme...");
                    Database.getInstance().closeConnection();
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
            System.out.println("--------------------------------------------------");
        }
        sc.close();
    }

    private void afficherOptions() {
        System.out.println("********* MENU *************");
        System.out.println("1. Afficher tous les programmeurs");
        System.out.println("2. Afficher un programmeur");
        System.out.println("3. Supprimer un programmeur");
        System.out.println("4. Ajouter un programmeur");
        System.out.println("5. Modifier le salaire");
        System.out.println("6. Afficher la liste des projets");
        System.out.println("7. Obtenir la liste des programmeurs d'un projet");
        System.out.println("8. Quitter le programme");
        System.out.print("Quel est votre choix ? : ");
    }

    private void afficherTousLesProgrammeurs() {
        List<Programmer> liste = actions.getAllProgrammers();
        if (liste.isEmpty()) {
            System.out.println("Aucun programmeur dans la base.");
        } else {
            for (Programmer p : liste) {
                System.out.println(p);
            }
        }
    }

    private void afficherUnProgrammeur() {
        System.out.print("ID du programmeur à afficher : ");
        int id = lireEntier();
        Programmer p = actions.getProgrammerById(id);
        if (p != null) {
            System.out.println("Id : " + p.getId());
            System.out.println("Nom : " + p.getLastName());
            System.out.println("Prénom : " + p.getFirstName());
            System.out.println("Adresse : " + p.getAddress());
            System.out.println("Pseudo : " + p.getUsername());
            System.out.println("Responsable : " + p.getManager());
            System.out.println("Hobby : " + p.getHobby());
            System.out.println("Naissance : " + p.getBirthYear());
            System.out.println("Salaire : " + p.getSalary());
            System.out.println("Prime : " + p.getBonus());
        } else {
            System.out.println("Programmeur introuvable.");
        }
    }

    private void supprimerProgrammeur() {
        System.out.print("Id du programmeur à supprimer : ");
        int id = lireEntier();
        if (actions.getProgrammerById(id) != null) {
            actions.deleteProgrammer(id);
        } else {
            System.out.println("Suppression KO. L'ID n'existe pas.");
        }
    }

    private void ajouterProgrammeur() {
        System.out.println("--- Ajout d'un nouveau programmeur ---");
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Adresse : ");
        String adresse = sc.nextLine();
        System.out.print("Pseudo : ");
        String pseudo = sc.nextLine();
        System.out.print("Responsable : ");
        String manager = sc.nextLine();
        System.out.print("Hobby : ");
        String hobby = sc.nextLine();

        System.out.print("Année de naissance : ");
        int annee = lireEntier();

        System.out.print("Salaire : ");
        float salaire = lireFloat();

        System.out.print("Prime : ");
        float prime = lireFloat();

        Programmer newProg = new Programmer(nom, prenom, adresse, pseudo, manager, hobby, annee, salaire, prime);
        actions.addProgrammer(newProg);
    }

    private void modifierSalaire() {
        System.out.print("Id du programmeur : ");
        int id = lireEntier();
        if (actions.getProgrammerById(id) == null) {
            System.out.println("Programmeur introuvable.");
            return;
        }
        System.out.print("Nouveau salaire : ");
        float newSalary = lireFloat();
        actions.updateSalary(id, newSalary);
    }

    private void afficherTousLesProjets() {
        List<Project> projets = actions.getAllProjects();
        if (projets.isEmpty()) {
            System.out.println("Aucun projet trouvé.");
        } else {
            for (Project p : projets) {
                System.out.println(p);
            }
        }
    }

    private void afficherProgrammeursParProjet() {
        afficherTousLesProjets();
        System.out.print("Entrez l'ID du projet pour voir l'équipe : ");
        int idProjet = lireEntier();

        List<Programmer> team = actions.getProgrammersByProject(idProjet);
        if (team.isEmpty()) {
            System.out.println("Aucun programmeur sur ce projet (ou ID projet invalide).");
        } else {
            System.out.println("--- Équipe du projet " + idProjet + " ---");
            for (Programmer p : team) {
                System.out.println("- " + p.getFirstName() + " " + p.getLastName());
            }
        }
    }

    private int lireEntier() {
        try {
            int i = sc.nextInt();
            sc.nextLine();
            return i;
        } catch (Exception e) {
            sc.nextLine();
            return -1;
        }
    }

    private float lireFloat() {
        try {
            float f = sc.nextFloat();
            sc.nextLine();
            return f;
        } catch (Exception e) {
            sc.nextLine();
            return 0.0f;
        }
    }
}