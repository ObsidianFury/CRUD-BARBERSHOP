/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.database;
import java.sql.*;

/**
 *
 * @author Mixalhs
 */
public class DBConnection {
    
    //Credentials for db connection
    private static final String URL = "jdbc:mysql://localhost:3306/barbershopdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mike2005";
    
    //Method to return the connection
    public static Connection getConnection(){
        Connection connection = null;
        
        //load the driver
        try{
            Class.forName("com.mysql.cj.jbdc.Driver");
            
            //Perform connection
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("DB connection succesful");
            
            
        }catch(ClassNotFoundException e){
            System.out.println("No jdbc driver found " + e.getMessage());
        }catch(SQLException e){
            System.out.println("Error connecting to db " + e.getMessage());
        }
        
        return connection;
    }
    
}
