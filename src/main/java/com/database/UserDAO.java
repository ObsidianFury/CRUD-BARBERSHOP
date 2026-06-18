/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.database;
import java.sql.*;

/**
 *
 * @author Mixalhs
 */
public class UserDAO {
    
    // Επιστρέφει έναν πίνακα String: [0] = username, [1] = role. Αν αποτύχει, επιστρέφει null.
    public String[] authenticateUser(String email, String password) {
        String sql = "SELECT username, role FROM users WHERE email = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Βρέθηκε! Επιστρέφουμε τα δεδομένα του
                    return new String[] { rs.getString("username"), rs.getString("role") };
                }
            }
        } catch (SQLException e) {
            System.out.println("Σφάλμα σύνδεσης: " + e.getMessage());
        }
        return null; // Δεν βρέθηκε χρήστης ή έγινε λάθος
    }
}