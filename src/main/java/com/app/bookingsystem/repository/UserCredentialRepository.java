package com.app.bookingsystem.repository;

import com.app.bookingsystem.entity.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
     Optional<UserCredential> findByEmail(String email);
    UserCredential findUserById(String customerId);
}
