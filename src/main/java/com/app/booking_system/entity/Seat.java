package com.app.booking_system.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(name="number" ,nullable = false)
    private Long number;

    @ManyToOne
    @JoinColumn(name="bus_id" ,nullable = false)
    private Bus bus;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;



}
