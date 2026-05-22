/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mixalhs
 */
public class ValidationUtilTest {
    
    @Test
    public void testEmailValidation(){
        // Περιμένουμε να είναι TRUE
        assertTrue(ValidationUtils.isValidEmail("mixalhs@gmail.com"),"Email is invalid");
        
        //Περιμένουμε να είναι FALSE
        assertFalse(ValidationUtils.isValidEmail("mixalhs.com"),"Wrong email");
        assertFalse(ValidationUtils.isValidEmail(""),"Empty email pass as valid");
        
        
    
    }
    
    
    @Test
    public void testPasswordStrength(){
        assertTrue(ValidationUtils.isPasswordStrong("12345"));
        assertFalse(ValidationUtils.isPasswordStrong("123"));
    }
}
