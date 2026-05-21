/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

/**
 *
 * @author Mixalhs
 */
public class DBConnectionTest {
    
    @Test
    public void testConnectionNotNull(){
        // Καλούμε τη μέθοδο της κλάσης μας
        Connection conn = DBConnection.getConnection();
        // Ελέγχουμε (Assert) αν το αποτέλεσμα είναι αυτό που περιμένουμε
        assertNotNull(conn,"Connection with database failled");
        
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch(Exception e){
            fail("Failled to close connection");
        }
    }
    
}
