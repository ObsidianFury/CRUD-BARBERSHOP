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
public class RegisterServiceTest {
    
    private UserDAO mockUserDAO;
    private RegisterService registerService;

    @BeforeEach
    public void setup() {
        mockUserDAO = Mockito.mock(UserDAO.class);
        registerService = new RegisterService(mockUserDAO);
    }

    @Test
    public void testSuccessfulRegistration() {
        // Εκπαίδευση Mock: Το email δεν υπάρχει και η δημιουργία πετυχαίνει
        when(mockUserDAO.isEmailTaken("newcust@gmail.com")).thenReturn(false);
        when(mockUserDAO.createUser("NewCust", "newcust@gmail.com", "12345", "CUSTOMER")).thenReturn(true);

        // Εκτέλεση
        String result = registerService.registerCustomer("NewCust", "newcust@gmail.com", "12345");

        // Έλεγχος
        assertEquals("SUCCESS", result);
        verify(mockUserDAO, times(1)).createUser("NewCust", "newcust@gmail.com", "12345", "CUSTOMER");
    }

    @Test
    public void testEmailAlreadyTaken() {
        // Εκπαίδευση Mock: Το email είναι πιασμένο
        when(mockUserDAO.isEmailTaken("taken@gmail.com")).thenReturn(true);

        // Εκτέλεση
        String result = registerService.registerCustomer("John", "taken@gmail.com", "12345");

        // Έλεγχος
        assertEquals("EMAIL_TAKEN", result);
        // Επιβεβαίωση: Δεν έγινε καμία εγγραφή στη βάση!
        verify(mockUserDAO, never()).createUser(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testWeakPasswordFailsEarly() {
        // Εκτέλεση με μικρό κωδικό
        String result = registerService.registerCustomer("John", "john@gmail.com", "12");

        // Έλεγχος
        assertEquals("WEAK_PASSWORD", result);
        // Επιβεβαίωση: Κόπηκε στον έλεγχο, οπότε δεν ρώτησε καν τη βάση!
        verify(mockUserDAO, never()).isEmailTaken(anyString());
    }
    
}
