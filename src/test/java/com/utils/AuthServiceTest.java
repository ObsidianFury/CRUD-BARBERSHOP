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
public class AuthServiceTest {
    private UserDAO mockUserDAO;
    private AuthService authService;
    
    
    @BeforeEach
    public void setup() {
        // 1. Φτιάχνουμε τον "ψεύτικο" UserDAO (Mock)
        mockUserDAO = Mockito.mock(UserDAO.class);
        
        // 2. Δίνουμε τον ψεύτικο DAO στο AuthService
        authService = new AuthService(mockUserDAO);
    }
    
    @Test
    public void testSuccessfulLogin_UC01() {
        // --- ΕΚΠΑΙΔΕΥΣΗ ΤΟΥ MOCK ---
        // Λέμε στο Mock: "Όταν έρθει το σωστό email και ο σωστός κωδικός, 
        // επέστρεψε έναν πίνακα με το username (Mixalhs) και τον ρόλο (CUSTOMER)"
        String[] expectedData = new String[]{"Mixalhs", "CUSTOMER"};
        when(mockUserDAO.authenticateUser("mixalhs@gmail.com", "12345")).thenReturn(expectedData);

        // --- ΕΚΤΕΛΕΣΗ ΤΟΥ USE CASE ---
        String[] result = authService.login("mixalhs@gmail.com", "12345");

        // --- ΕΛΕΓΧΟΣ ---
        // Πρέπει το αποτέλεσμα να μην είναι null (δηλαδή πέτυχε η σύνδεση)
        assertNotNull(result, "Το login έπρεπε να είναι επιτυχές (όχι null)!");
        
        // Ελέγχουμε αν τα στοιχεία που γύρισε είναι σωστά
        assertEquals("Mixalhs", result[0], "Το Username δεν ταιριάζει!");
        assertEquals("CUSTOMER", result[1], "Ο Ρόλος δεν ταιριάζει!");
        
        // Επιβεβαιώνουμε ότι το AuthService κάλεσε όντως τη βάση 1 φορά
        verify(mockUserDAO, times(1)).authenticateUser("mixalhs@gmail.com", "12345");
    }
    
    @Test
    public void testFailedLoginWrongPassword_UC01() {
        // Εκπαίδευση του Mock: "Με λάθος κωδικό η βάση δεν βρίσκει τίποτα, άρα επιστρέφει null"
        when(mockUserDAO.authenticateUser("mixalhs@gmail.com", "WrongPass")).thenReturn(null);

        // Εκτέλεση
        String[] result = authService.login("mixalhs@gmail.com", "WrongPass");

        // Έλεγχος: Το αποτέλεσμα ΠΡΕΠΕΙ να είναι null
        assertNull(result, "Το login έπρεπε να αποτύχει και να επιστρέψει null.");
    }
    
    @Test
    public void testFailedLoginEmptyFields_UC01() {
        // Εκτέλεση με κενά πεδία (Η λογική μας στο AuthService κόβει τα κενά)
        String[] result = authService.login("", "");

        // Έλεγχος
        assertNull(result, "Τα κενά πεδία δεν πρέπει να επιτρέπουν login.");
        
        // ΕΠΙΒΕΒΑΙΩΣΗ: Κοιτάμε ότι το DAO (Βάση) ΔΕΝ κλήθηκε ΠΟΤΕ! 
        verify(mockUserDAO, never()).authenticateUser(anyString(), anyString());
    }
}
