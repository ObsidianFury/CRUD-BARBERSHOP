/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import com.database.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Mixalhs
 */
public class AdminServiceTest {
    
    private UserDAO mockUserDAO;
    private AdminService adminService;
    
    @BeforeEach
    public void setup() {
        mockUserDAO = Mockito.mock(UserDAO.class);
        adminService = new AdminService(mockUserDAO);
    }
    
    @Test
    public void testSuccessfulAccountCreation_UC18() {
        // Εκπαίδευση Mock: Το email δεν υπάρχει και το INSERT πετυχαίνει
        when(mockUserDAO.isEmailTaken("newuser@gmail.com")).thenReturn(false);
        when(mockUserDAO.createUser("NewUser", "newuser@gmail.com", "12345", "CUSTOMER")).thenReturn(true);

        // Εκτέλεση
        String result = adminService.createAccount("NewUser", "newuser@gmail.com", "12345", "CUSTOMER");

        // Έλεγχος
        assertEquals("SUCCESS", result, "Η δημιουργία έπρεπε να πετύχει.");
        verify(mockUserDAO, times(1)).createUser("NewUser", "newuser@gmail.com", "12345", "CUSTOMER");
    }
    
    @Test
    public void testEmailAlreadyTaken_UC18() {
        // Εκπαίδευση Mock: Το email υπάρχει ήδη στη βάση!
        when(mockUserDAO.isEmailTaken("taken@gmail.com")).thenReturn(true);

        // Εκτέλεση
        String result = adminService.createAccount("John", "taken@gmail.com", "12345", "BARBER");

        // Έλεγχος
        assertEquals("EMAIL_TAKEN", result, "Έπρεπε να κοπεί λόγω διπλότυπου email.");
        
        // ΕΠΙΒΕΒΑΙΩΣΗ: Αφού το email υπήρχε, η μέθοδος createUser ΔΕΝ ΠΡΕΠΕΙ ΝΑ ΚΛΗΘΕΙ ΠΟΤΕ!
        verify(mockUserDAO, never()).createUser(anyString(), anyString(), anyString(), anyString());
    }
    
    @Test
    public void testInvalidEmailFailsBeforeDatabase_UC18() {
        // Εκτέλεση με λάθος email (π.χ. χωρίς @)
        String result = adminService.createAccount("John", "bad-email.com", "12345", "CUSTOMER");

        // Έλεγχος
        assertEquals("INVALID_EMAIL", result, "Έπρεπε να κοπεί από το ValidationUtils.");
        
        // ΕΠΙΒΕΒΑΙΩΣΗ: Δεν πρέπει καν να ρωτήσει τη βάση αν υπάρχει το email!
        verify(mockUserDAO, never()).isEmailTaken(anyString());
    }
    
    // --- TESTS ΓΙΑ ΤΗ ΔΙΑΓΡΑΦΗ (UC 19) ---

    @Test
    public void testSuccessfulAccountDeletion_UC19() {
        // Εκπαίδευση Mock: Όταν ζητηθεί διαγραφή αυτού του email, επέστρεψε TRUE
        when(mockUserDAO.deleteUser("delete_me@gmail.com")).thenReturn(true);

        // Εκτέλεση
        String result = adminService.deleteAccount("delete_me@gmail.com");

        // Έλεγχος
        assertEquals("SUCCESS", result, "Η διαγραφή έπρεπε να πετύχει.");
        verify(mockUserDAO, times(1)).deleteUser("delete_me@gmail.com");
    }

    @Test
    public void testDeleteAccountInvalidEmail_UC19() {
        // Εκτέλεση με λάθος email
        String result = adminService.deleteAccount("lathos-email");

        // Έλεγχος
        assertEquals("INVALID_EMAIL", result, "Το λάθος email έπρεπε να κοπεί.");
        
        // ΕΠΙΒΕΒΑΙΩΣΗ: Αφού το email ήταν λάθος, η βάση ΔΕΝ πρέπει να ενοχληθεί ποτέ!
        verify(mockUserDAO, never()).deleteUser(anyString());
    }
    
}
