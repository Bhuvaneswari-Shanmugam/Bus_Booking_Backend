package com.app.booking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@Table(name = "organization")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable=true)
    private String name;

    @Column(name = "phone_number" , nullable=true)
    private Long phoneNumber;

    @Column(name = "email" , nullable=true , unique = true )
    private String email;

    @Column(name = "address1" , nullable=true)
    private String address1;

    @Column(name = "address2" , nullable=true)
    private String address2;

    @Column(name = "city" , nullable=true)
    private String city;

    @Column(name = "state" , nullable=true)
    private String state;

    @Column(name = "is_active" , nullable=true)
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;


}
