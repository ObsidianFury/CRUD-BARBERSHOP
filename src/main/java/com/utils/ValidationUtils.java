/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import java.time.*;
import java.time.format.DateTimeParseException;
/**
 *
 * @author Mixalhs
 */
public class ValidationUtils {
    
    // 1. Έλεγχος Email (Πρέπει να έχει @ και .)
    public static boolean isValidEmail(String email){
    
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    // 2. Έλεγχος Κωδικού (Τουλάχιστον 5 χαρακτήρες)
    public static boolean isPasswordStrong(String password){
        if (password == null) return false;
        return password.length() >= 5;
    }
    
    // 3. Έλεγχος Username (Να μην είναι κενό και να έχει τουλάχιστον 3 γράμματα)
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) return false;
        return username.trim().length() >= 3;
    }
    
    // 4. Έλεγχος Ρόλου (Επιτρέπονται μόνο CUSTOMER, BARBER, ADMIN)
    public static boolean isValidRole(String role) {
        if (role == null) return false;
        String r = role.toUpperCase().trim();
        return r.equals("CUSTOMER") || r.equals("BARBER") || r.equals("ADMIN");
    }
    
    // 5. Έλεγχος σωστής μορφής Ημερομηνίας (yyyy-MM-dd για τη MySQL)
    public static boolean isValidDateFormat(String dateStr) {
        if (dateStr == null) return false;
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    // 6. Έλεγχος αν το ραντεβού είναι στο παρελθόν (Δεν πρέπει να κλείνουμε ραντεβού για χθες)
    public static boolean isFutureOrToday(String dateStr) {
        if (!isValidDateFormat(dateStr)) return false;
        LocalDate date = LocalDate.parse(dateStr);
        return !date.isBefore(LocalDate.now());
    }
    
}
