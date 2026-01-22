# PROJET DE GESTION DES PROGRAMMEURS (2025-2026)

## Description

Ce projet est une application de gestion de programmeurs et de projets. Il permet d'ajouter, modifier, supprimer et afficher des programmeurs, ainsi que de voir sur quels projets ils travaillent.

Réalisation d'une interface graphique complète (JavaFX) pour rendre l'utilisation plus simple et plus visuelle qu'une console classique.

## Pré-requis techniques

- Java 21 (JDK 21)
- Maven
- MySQL

## Installation et Base de données

1. Assurez-vous d'avoir un serveur MySQL lancé.
2. Exécutez le script SQL fourni pour créer la base de données `PROG_BD` et les tables.

**Important - Configuration de la connexion :**
Par défaut, le projet essaie de se connecter avec l'utilisateur "root" et le mot de passe "root".
Si votre mot de passe MySQL est différent, vous devez le changer dans le code ici :

Fichier : `src/main/java/fr/efrei/alsi54/Database.java`
Lignes : modifiez les variables USER et PASSWORD.

## Comment lancer l'application

### Option 1 : Via un IDE (VS Code, IntelliJ, Eclipse)

1. Ouvrez le dossier du projet.
2. Attendez que Maven charge les dépendances.
3. Ouvrez le fichier `src/main/java/fr/efrei/alsi54/JavaFXApp.java`.
4. Cliquez sur "Run" ou "Exécuter".

### Option 2 : Via le terminal

Placez-vous à la racine du projet (là où est le fichier pom.xml) et tapez :
`mvn clean javafx:run`

## Choix du SGBD

Utilisation de MySQL avec le connecteur officiel (mysql-connector-j). C'est le système vu en cours et il est fiable pour gérer les relations entre les programmeurs et les projets.

## Fonctionnalités réalisées

Toutes les fonctionnalités demandées dans le sujet sont présentes :

- Afficher la liste des programmeurs.
- Afficher le détail d'un programmeur.
- Supprimer un programmeur.
- Ajouter un nouveau programmeur.
- Modifier le salaire.
- Afficher les projets.
- Voir l'équipe d'un projet.

## Améliorations (Bonus)

Au lieu de l'affichage console demandé, nous avons développé une interface graphique avec JavaFX :

- Tableau de bord moderne avec menu à gauche.
- Fenêtres de dialogue pour ajouter ou modifier les données (évite les erreurs de saisie).
- Vue partagée pour les projets : cliquer sur un projet affiche directement son équipe en bas de la fenêtre.
