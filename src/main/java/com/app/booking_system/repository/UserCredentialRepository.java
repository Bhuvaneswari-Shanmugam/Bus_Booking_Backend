package com.app.booking_system.repository;

import com.app.booking_system.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
     Optional<UserCredential> findByEmail(String email);


    UserCredential findUserById(String customerId);
}
