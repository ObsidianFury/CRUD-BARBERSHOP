/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import com.database.UserDAO;

/**
 *
 * @author Mixalhs
 */
public class AuthService {
    
    private UserDAO userDAO;
    
    public AuthService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    
    // Η μέθοδος του Use Case 01: Customer Login
    public String[] login(String email, String password) {
        // 1. Έλεγχος αν τα πεδία είναι κενά
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }
        
    // 2. Επικοινωνία με τη Βάση (μέσω του DAO)
    return userDAO.authenticateUser(email, password);
    }
}
    
