/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author pipe-
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;

    private static final String URL  = "jdbc:mysql://localhost:3306/computec_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "1234";

    private DatabaseConnection() {}

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) instance = new DatabaseConnection();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public boolean testConnection() {
        try (Connection cn = getConnection()) {
            return cn != null && !cn.isClosed();
        } catch (SQLException e) {
            System.err.println("[DB] Error conexión: " + e.getMessage());
            return false;
        }
    }
}
