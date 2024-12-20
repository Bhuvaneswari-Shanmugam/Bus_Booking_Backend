package com.app.booking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
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

//    @ManyToOne
//    @JoinColumn(name="organization_id" , nullable = false)
//    private Organization organization;
}
