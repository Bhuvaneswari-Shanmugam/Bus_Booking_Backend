package com.app.booking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
@Entity
@Table(name = "user_credential")
@Getter
@Setter
@Builder
public class UserCredential implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name="role" ,nullable = false)
    private String role;

    @Column(name = "terms_accepted", nullable = false)
    private Boolean termsAccepted;

    public UserCredential(String email, String password, String admin) {
    }

    public UserCredential(Object o, String email, Object o1, Object o2, Object o3) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ("ADMIN".equals(this.role)) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserCredential(String id, String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String gender, String contactNumber, String address, String role, Boolean termsAccepted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;
        this.role = role;
        this.termsAccepted = termsAccepted;
    }
    public UserCredential(){

    }

}
