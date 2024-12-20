package com.app.bookingsystem.config;


import com.app.bookingsystem.entity.UserCredential;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ExtractUserIdEmail {

    public static String getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final UserCredential user = (UserCredential) authentication.getPrincipal();
            return user.getId();
        }
        return null;
    }

    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final UserCredential user = (UserCredential) authentication.getPrincipal();
            return user.getUsername();
        }
        return null;
    }
}

