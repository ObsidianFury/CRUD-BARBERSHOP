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
public class AdminService {
    
    private UserDAO userDAO;
    
    public AdminService(UserDAO userDAO){
        this.userDAO=userDAO;
    }
    
    // UC 18: Δημιουργία λογαριασμού (Επιστρέφει String με το μήνυμα αποτελέσματος)
    public String createAccount(String username, String email, String password, String role) {
        // 1. Έλεγχος εγκυρότητας (Validation)
        if (!ValidationUtils.isValidUsername(username)) return "INVALID_USERNAME";
        if (!ValidationUtils.isValidEmail(email)) return "INVALID_EMAIL";
        if (!ValidationUtils.isPasswordStrong(password)) return "WEAK_PASSWORD";
        if (!ValidationUtils.isValidRole(role)) return "INVALID_ROLE";

        // 2. Έλεγχος αν το email υπάρχει ήδη
        if (userDAO.isEmailTaken(email)) {
            return "EMAIL_TAKEN"; // Εδώ καλύπτουμε το Exception #2 του Use Case σου!
        }

        // 3. Αποθήκευση στη βάση
        boolean success = userDAO.createUser(username, email, password, role);
        return success ? "SUCCESS" : "DB_ERROR";
    }
}
