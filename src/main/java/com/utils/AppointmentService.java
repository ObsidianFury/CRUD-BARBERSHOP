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
    
    
}
