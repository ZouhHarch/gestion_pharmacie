package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/pharmacie_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private Database() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base de données réussie !");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Pilote JDBC MySQL introuvable dans le dossier lib.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : Vérifie que WampServer est lancé (icône verte) et que le nom de la base est correct.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Connexion fermée proprement.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}