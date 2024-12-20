package com.app.booking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="name" , nullable = false)
    private String name;

    @Column(name="email" , nullable = false)
    private String email;

    @Column(name="license_number" , nullable = false)
    private String licenseNumber;

    @Column(name="phone_number" , nullable = false)
    private Long phoneNumber;

    @ManyToOne
    @JoinColumn(name="trip_id" , nullable = true)
    private Trip trip;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
}
