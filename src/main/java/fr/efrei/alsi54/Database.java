package fr.efrei.alsi54;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/PROG_BD";
    private final String USER = "root";
    private final String PASSWORD = "root";

    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé !");
            e.printStackTrace();
        }
    }

    /**
     * Retourne l'instance unique de la classe Database (Singleton).
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Retourne une connexion active. Si elle est fermée ou nulle, on la rouvre.
     */
    public Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return this.connection;
    }

    /**
     * Ferme la connexion proprement.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erreur à la fermeture : " + e.getMessage());
            }
        }
    }
}