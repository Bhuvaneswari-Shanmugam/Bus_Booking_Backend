package com.app.booking_system.config;


import com.app.booking_system.entity.UserCredential;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthHelper {

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserCredential user = (UserCredential) authentication.getPrincipal();
            return user.getId();
        }
        return null;
    }

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserCredential user = (UserCredential) authentication.getPrincipal();
            return user.getUsername();
        }
        return null;
    }
}

