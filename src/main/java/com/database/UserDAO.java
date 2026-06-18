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


    public boolean isEmailTaken(String email) {
        String sql = "SELECT email FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Αν βρει αποτέλεσμα, το email υπάρχει ήδη (true)
            }
        } catch (SQLException e) {
            System.out.println("Σφάλμα ελέγχου email: " + e.getMessage());
            return true; // Σε περίπτωση λάθους, επιστρέφουμε true για ασφάλεια (να μην προχωρήσει)
        }
    }
    
    
    // 2. Δημιουργία νέου χρήστη
    public boolean createUser(String username, String email, String password, String role) {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, role.toUpperCase());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Αν επηρεάστηκε έστω 1 γραμμή, η δημιουργία πέτυχε!
        } catch (SQLException e) {
            System.out.println("Σφάλμα δημιουργίας χρήστη: " + e.getMessage());
            return false;
        }
    }
    
}