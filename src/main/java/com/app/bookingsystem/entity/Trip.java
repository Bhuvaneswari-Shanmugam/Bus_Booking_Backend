package com.app.bookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(name = "trip_number", nullable = false, updatable = false, unique = true)
    private Long tripNumber;

    @Column(name="pickup_point" , nullable = false)
    private String pickupPoint;

    @Column(name="destination_point" , nullable = false)
    private String destinationPoint;

    @Column(name="pickup_time" , nullable = false)
    private Instant pickupTime;     //2024-09-26T10:00:00Z

    @Column(name="reaching_time" , nullable = false)
    private Instant reachingTime;

    @Column(name="expense" , nullable = false)
    private Long expense;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

}
