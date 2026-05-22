/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

/**
 *
 * @author Mixalhs
 */
public class ValidationUtils {
    
    // Ελέγχει αν το email έχει '@' και '.'
    public static boolean isValidEmail(String email){
    
        if(email == null || email.isEmpty()){
            return false;
        }
        else{
           return email.contains("@") && email.contains("."); 
        }
    }
    
    // Ελέγχει αν ο κωδικός έχει τουλάχιστον 4 χαρακτήρες
    public static boolean isPasswordStrong(String password){
        return password != null && password.length() >= 4;
    }
}
