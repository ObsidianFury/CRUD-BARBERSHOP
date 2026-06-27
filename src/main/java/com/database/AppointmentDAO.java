/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mixalhs
 */
public class AppointmentDAO {
    
    // 1. Φόρτωση όλων των Κουρέων
    public List<String> getBarbers(){
        List<String> barbers = new ArrayList<>();
        
        String sql = "SELECT username FROM Users WHERE role = 'BARBER'";
        
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){
            
            while (rs.next()) {
                barbers.add(rs.getString("username"));
            }
            
        }catch(SQLException e){
            System.out.println("Σφάλμα φόρτωσης κουρέων: " + e.getMessage());
        }
        return barbers;
    }
    
    // 2. Φόρτωση υπηρεσιών για συγκεκριμένο κουρέα
    public List<String> getServicesForBarber(String barberName){
        List<String> services = new ArrayList<>();
        String sql = "SELECT s.service_name FROM Services s JOIN Users u ON s.barber_id = u.user_id WHERE u.username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, barberName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    services.add(rs.getString("service_name"));
                }
            }
        }catch(SQLException e){
        
            System.out.println("Σφάλμα φόρτωσης υπηρεσιών: " + e.getMessage());
        }
        return services;
    }
    
    // 3. Δημιουργία του ραντεβού στη βάση
    public boolean createAppointment(String customerName, String barberName, String serviceName, String date){
    
        String sql = "INSERT INTO appointments (customer_id, barber_id, service_id, appointment_date, status) " +
                     "VALUES (" +
                     "(SELECT user_id FROM users WHERE username = ? AND role = 'CUSTOMER'), " +
                     "(SELECT user_id FROM users WHERE username = ? AND role = 'BARBER'), " +
                     "(SELECT service_id FROM services WHERE service_name = ? AND barber_id = (SELECT user_id FROM users WHERE username = ? AND role = 'BARBER') LIMIT 1), " +
                     "?, 'PENDING')";
        
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, customerName);
            pstmt.setString(2, barberName);
            pstmt.setString(3, serviceName);
            pstmt.setString(4, barberName);
            pstmt.setString(5, date);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
            
        }catch(SQLException e){
            System.out.println("Σφάλμα δημιουργίας ραντεβού: " + e.getMessage());
            return false;
        }
    }
    
}
