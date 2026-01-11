package fr.efrei.alsi54;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database instance;
    private Connection connection;
    private String url;
    private String user;
    private String password;

    private Database() {
    }

    /**
     * Retourne l'instance unique de la classe Database (Singleton).
     * @return
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Ouvre une connexion à la base de données.
     *
     * @throws SQLException en cas d'erreur lors de la connexion.
     */
    public void openConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC MySQL non trouve. Verifiez que le .jar est dans le classpath.");
            throw new SQLException(e);
        }
        connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Execute une requete sql preparee.
     * @param query La requete SQL a executer.
     * @param params Les parametres de la requete.
     * @return Le resultat de la requete.
     */



    /**
     * Ferme la connexion à la base de données.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    /**
     * Teste la connexion à la base de données.
     * @return true si la connexion est réussie, false sinon.
     */
    public boolean testConnection() {
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

}
