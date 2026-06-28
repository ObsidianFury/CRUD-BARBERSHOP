/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import com.database.AppointmentDAO;
import java.util.List;
/**
 *
 * @author Mixalhs
 */
public class AppointmentService {
    
    private AppointmentDAO appointmentDAO;
    
    public AppointmentService(AppointmentDAO appointmentDAO){
        this.appointmentDAO = appointmentDAO;
    }
    
    public List<String> getBarbers(){
        return appointmentDAO.getBarbers();
    }
    
    public List<String> getServiceForBarber(String barberName){
        if(barberName == null || barberName.trim().isEmpty()){
            return java.util.Collections.emptyList();
        }
        return appointmentDAO.getServicesForBarber(barberName);
    }
    
    
    // Η λογική για το κλείσιμο του ραντεβού
    public String bookAppointment(String customerName, String barberName, String serviceName, String date){
        // 1. Έλεγχοι ασφαλείας
        if (barberName == null || serviceName == null) return "MISSING_SELECTION";
        if (date == null || date.trim().isEmpty()) return "MISSING_DATE";
        
        // Χρησιμοποιούμε τη μέθοδο που γράψαμε στην αρχή για να κόβουμε παλιές ημερομηνίες!
        if (!ValidationUtils.isFutureOrToday(date)) return "INVALID_DATE";
        
        // 2. Αποθήκευση στη βάση
        boolean success = appointmentDAO.createAppointment(customerName, barberName, serviceName, date);
        return success ? "SUCCESS" : "DB_ERROR";
    }
    
    // --- ΠΡΟΒΟΛΗ ΡΑΝΤΕΒΟΥ ---
    public List<String[]> getCustomerAppointments(String customerName) {
        if (customerName == null || customerName.isEmpty()) return new java.util.ArrayList<>();
        return appointmentDAO.getCustomerAppointments(customerName);
    }
    
    public List<String[]> getBarberAppointments(String barberName) {
        if (barberName == null || barberName.isEmpty()) return new java.util.ArrayList<>();
        return appointmentDAO.getBarberAppointments(barberName);
    }
    
    public List<String[]> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }
    
    
    // --- ΛΟΓΙΚΗ ΓΙΑ ΔΙΑΘΕΣΙΜΟΤΗΤΑ ---
    
    public List<String[]> getBarberAvailability(String barberName) {
        if (barberName == null || barberName.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return appointmentDAO.getBarberAvailability(barberName);
    }
    
    public String addAvailability(String barberName, boolean isChecked) {
        // Ελέγχουμε αν το κουτάκι είναι τσεκαρισμένο
        if (!isChecked) {
            return "NOT_CHECKED";
        }
        if (barberName == null || barberName.trim().isEmpty()) {
            return "ERROR";
        }

        boolean success = appointmentDAO.addAvailability(barberName);
        return success ? "SUCCESS" : "DB_ERROR";
    }
    
    // --- ΛΟΓΙΚΗ ΓΙΑ ADMIN (Τροποποίηση Ραντεβού) ---
    
    public java.util.List<String> getAllServices() {
        return appointmentDAO.getAllServices();
    }

    public String updateAppointment(int appointmentId, String date, String barberName, String serviceName) {
        if (appointmentId <= 0) return "INVALID_ID";
        if (barberName == null || barberName.trim().isEmpty() || 
            serviceName == null || serviceName.trim().isEmpty() || 
            date == null || date.trim().isEmpty()) {
            return "EMPTY_FIELDS";
        }
        
        // (Προαιρετικό αλλά καλό) Εμποδίζει τον Admin να βάλει ημερομηνία στο παρελθόν
        if (!ValidationUtils.isFutureOrToday(date)) return "INVALID_DATE";

        boolean success = appointmentDAO.updateAppointment(appointmentId, date, barberName, serviceName);
        return success ? "SUCCESS" : "DB_ERROR";
    }
    
    
}
