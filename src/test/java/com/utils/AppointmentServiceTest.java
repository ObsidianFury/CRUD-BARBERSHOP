/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import com.database.AppointmentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Mixalhs
 */
public class AppointmentServiceTest {
    private AppointmentDAO mockDAO;
    private AppointmentService appointmentService;

    @BeforeEach
    public void setup() {
        // Φτιάχνουμε το Mock (την "ψεύτικη" βάση)
        mockDAO = Mockito.mock(AppointmentDAO.class);
        // Το δίνουμε στο Service
        appointmentService = new AppointmentService(mockDAO);
    }

    // --- TEST 1: Επιτυχές Κλείσιμο Ραντεβού ---
    @Test
    public void testSuccessfulBooking() {
        // Φτιάχνουμε μια σωστή, μελλοντική ημερομηνία
        String futureDate = LocalDate.now().plusDays(2).toString(); 

        // Εκπαίδευση του Mock
        when(mockDAO.createAppointment("Mixalhs", "Barber1", "Haircut", futureDate)).thenReturn(true);

        // Εκτέλεση
        String result = appointmentService.bookAppointment("Mixalhs", "Barber1", "Haircut", futureDate);

        // Έλεγχος
        assertEquals("SUCCESS", result, "Το ραντεβού έπρεπε να κλειστεί επιτυχώς!");
        verify(mockDAO, times(1)).createAppointment("Mixalhs", "Barber1", "Haircut", futureDate);
    }

    // --- TEST 2: Προσπάθεια χωρίς να επιλεχθεί Κουρέας ---
    @Test
    public void testBookingWithoutBarber() {
        String futureDate = LocalDate.now().plusDays(2).toString(); 

        // Εκτέλεση (Στέλνουμε null στον Barber)
        String result = appointmentService.bookAppointment("Mixalhs", null, "Haircut", futureDate);

        // Έλεγχος
        assertEquals("MISSING_SELECTION", result, "Έπρεπε να κοπεί λόγω έλλειψης Κουρέα.");
        
        // Επιβεβαίωση: Δεν έγινε καμία κλήση στη βάση!
        verify(mockDAO, never()).createAppointment(anyString(), anyString(), anyString(), anyString());
    }

    // --- TEST 3: Προσπάθεια για ραντεβού στο Παρελθόν ---
    @Test
    public void testBookingWithPastDate() {
        // Εκτέλεση με ημερομηνία που έχει περάσει (π.χ. 2020)
        String result = appointmentService.bookAppointment("Mixalhs", "Barber1", "Haircut", "2020-05-10");

        // Έλεγχος
        assertEquals("INVALID_DATE", result, "Το σύστημα έπρεπε να μπλοκάρει τις παλιές ημερομηνίες.");
        verify(mockDAO, never()).createAppointment(anyString(), anyString(), anyString(), anyString());
    }

    // --- TEST 4: Φόρτωση λίστας με Κουρείς ---
    @Test
    public void testGetBarbers() {
        // Εκπαίδευση: Όταν ζητηθούν οι κουρείς, δώσε μια λίστα με 2 ονόματα
        List<String> expectedBarbers = Arrays.asList("John", "Mike");
        when(mockDAO.getBarbers()).thenReturn(expectedBarbers);

        // Εκτέλεση
        List<String> result = appointmentService.getBarbers();

        // Έλεγχος
        assertEquals(2, result.size());
        assertTrue(result.contains("John"));
        verify(mockDAO, times(1)).getBarbers();
    }
    
    @Test
    public void testGetCustomerAppointments() {
        // Εκπαίδευση Mock: Φτιάχνουμε μια ψεύτικη λίστα με 1 ραντεβού
        List<String[]> fakeList = java.util.Collections.singletonList(
            new String[]{"JohnTheBarber", "Haircut", "2026-05-20", "PENDING"}
        );
        when(mockDAO.getCustomerAppointments("Mixalhs")).thenReturn(fakeList);

        // Εκτέλεση
        List<String[]> result = appointmentService.getCustomerAppointments("Mixalhs");

        // Έλεγχος
        assertEquals(1, result.size(), "Έπρεπε να γυρίσει 1 ραντεβού.");
        assertEquals("JohnTheBarber", result.get(0)[0], "Το όνομα του κουρέα δεν ταιριάζει.");
        
        // Επιβεβαίωση
        verify(mockDAO, times(1)).getCustomerAppointments("Mixalhs");
    }
    
    // --- TESTS ΓΙΑ ΤΗ ΔΙΑΘΕΣΙΜΟΤΗΤΑ ---

    @Test
    public void testAddAvailabilitySuccess() {
        // Εκπαίδευση: Όταν σταλεί σωστό όνομα, η βάση λέει TRUE
        when(mockDAO.addAvailability("John")).thenReturn(true);

        // Εκτέλεση (Το checkbox είναι τσεκαρισμένο = true)
        String result = appointmentService.addAvailability("John", true);

        // Έλεγχος
        assertEquals("SUCCESS", result);
        verify(mockDAO, times(1)).addAvailability("John");
    }

    @Test
    public void testAddAvailabilityNotChecked() {
        // Εκτέλεση (Το checkbox ΔΕΝ είναι τσεκαρισμένο = false)
        String result = appointmentService.addAvailability("John", false);

        // Έλεγχος
        assertEquals("NOT_CHECKED", result, "Έπρεπε να κοπεί γιατί δεν τσέκαρε το κουτάκι.");
        // Επιβεβαίωση: Δεν καλέσαμε ποτέ τη βάση
        verify(mockDAO, never()).addAvailability(anyString());
    }
}
