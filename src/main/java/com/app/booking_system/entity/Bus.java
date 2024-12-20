package com.app.booking_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="number" , nullable = false)
    private Long number;

    @Column(name="capacity" , nullable = false)
    private Long capacity;

    @Column(name="type" , nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name="trip_id" , nullable = false)
    private Trip trip;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;


}
