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
public class RegisterService {
    
    private UserDAO userDAO;
    
    public RegisterService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public String registerCustomer(String username, String email, String password) {
        // 1. Έλεγχος εγκυρότητας (Validation)
        if (!ValidationUtils.isValidUsername(username)) return "INVALID_USERNAME";
        if (!ValidationUtils.isValidEmail(email)) return "INVALID_EMAIL";
        if (!ValidationUtils.isPasswordStrong(password)) return "WEAK_PASSWORD";

        // 2. Έλεγχος αν το email χρησιμοποιείται ήδη
        if (userDAO.isEmailTaken(email)) {
            return "EMAIL_TAKEN";
        }

        // 3. Αποθήκευση στη βάση (Πάντα με ρόλο CUSTOMER από αυτή την οθόνη)
        boolean success = userDAO.createUser(username, email, password, "CUSTOMER");
        return success ? "SUCCESS" : "DB_ERROR";
    }
    
}
