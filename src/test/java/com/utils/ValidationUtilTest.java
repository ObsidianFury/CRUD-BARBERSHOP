/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;

/**
 *
 * @author Mixalhs
 */
public class ValidationUtilTest {
    
    // --- TESTS ΓΙΑ EMAIL ---

    @Test
    public void testValidEmail() {
        // Ένα σωστό email πρέπει να επιστρέφει true
        assertTrue(ValidationUtils.isValidEmail("mixalhs@gmail.com"), "Το σωστό email έπρεπε να περάσει.");
    }
        
        
    @Test
    public void testInvalidEmailWithoutAtSymbol() {
        // Ένα λάθος email χωρίς @ πρέπει να επιστρέφει false
        assertFalse(ValidationUtils.isValidEmail("mixalhs.gmail.com"), "Το email χωρίς @ έπρεπε να κοπεί.");
    }
    
    @Test
    public void testNullOrEmptyEmail() {
        // Κενά ή null emails πρέπει να επιστρέφουν false
        assertFalse(ValidationUtils.isValidEmail(""), "Το κενό email έπρεπε να κοπεί.");
        assertFalse(ValidationUtils.isValidEmail(null), "Το null email έπρεπε να κοπεί.");
    }
    
    
    // --- TESTS ΓΙΑ ΚΩΔΙΚΟ ---
    
    @Test
    public void testStrongPassword() {
        // Κωδικός με 5+ χαρακτήρες
        assertTrue(ValidationUtils.isPasswordStrong("123456"), "Ο ισχυρός κωδικός έπρεπε να περάσει.");
    }
    
    @Test
    public void testWeakPassword() {
        // Κωδικός με λιγότερους από 5 χαρακτήρες
        assertFalse(ValidationUtils.isPasswordStrong("1234"), "Ο αδύναμος κωδικός έπρεπε να κοπεί.");
    }
    
    // --- TESTS ΓΙΑ USERNAME ---
    
    @Test
    public void testValidUsername() {
        assertTrue(ValidationUtils.isValidUsername("Mixalhs"), "Το έγκυρο username έπρεπε να περάσει.");
    }
    
    @Test
    public void testInvalidUsernameTooShort() {
        // Username με 2 γράμματα δεν επιτρέπεται
        assertFalse(ValidationUtils.isValidUsername("Mi"), "Το πολύ μικρό username έπρεπε να κοπεί.");
        assertFalse(ValidationUtils.isValidUsername("   "), "Τα κενά διαστήματα έπρεπε να κοπούν.");
    }
    
    // --- TESTS ΓΙΑ ΡΟΛΟΥΣ (ROLES) ---
    @Test
    public void testValidRoles() {
        // Πρέπει να δέχεται και τους 3 ρόλους (ακόμα και με μικρά γράμματα)
        assertTrue(ValidationUtils.isValidRole("CUSTOMER"));
        assertTrue(ValidationUtils.isValidRole("barber"));
        assertTrue(ValidationUtils.isValidRole("Admin"));
    }
    
    @Test
    public void testInvalidRole() {
        // Τυχαίοι ρόλοι πρέπει να κόβονται
        assertFalse(ValidationUtils.isValidRole("MANAGER"), "Ο ρόλος MANAGER δεν υπάρχει στη βάση.");
    }
    
    // --- TESTS ΓΙΑ ΗΜΕΡΟΜΗΝΙΕΣ ΡΑΝΤΕΒΟΥ ---
    @Test
    public void testValidDateFormat() {
        // Σωστή μορφή για MySQL (yyyy-MM-dd)
        assertTrue(ValidationUtils.isValidDateFormat("2026-05-20"), "Η σωστή μορφή ημερομηνίας έπρεπε να περάσει.");
    }
    
    @Test
    public void testInvalidDateFormat() {
        // Λάθος μορφή (π.χ. dd-MM-yyyy ή τυχαίο κείμενο)
        assertFalse(ValidationUtils.isValidDateFormat("20-05-2026"), "Η ανάποδη ημερομηνία έπρεπε να κοπεί.");
        assertFalse(ValidationUtils.isValidDateFormat("Αύριο"), "Το απλό κείμενο έπρεπε να κοπεί.");
    }
    
    @Test
    public void testPastDateIsInvalid() {
        // Δεν μπορούμε να κλείσουμε ραντεβού στο παρελθόν
        assertFalse(ValidationUtils.isFutureOrToday("2020-01-01"), "Οι παλιές ημερομηνίες πρέπει να κόβονται.");
        
        // Μια μελλοντική ημερομηνία (π.χ. του χρόνου) πρέπει να περνάει
        String futureDate = LocalDate.now().plusYears(1).toString();
        assertTrue(ValidationUtils.isFutureOrToday(futureDate), "Οι μελλοντικές ημερομηνίες πρέπει να περνάνε.");
    }
    
}
