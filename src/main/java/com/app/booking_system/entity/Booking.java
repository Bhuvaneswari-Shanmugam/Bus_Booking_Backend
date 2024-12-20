package com.app.booking_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="bus_id",nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name="trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserCredential user;

    @CurrentTimestamp
    @Column(name="booking_date_time")
    private Date bookingDateTime;

    @Column(name="booked_no_of_seats")
    private List<Long> bookedNoOfSeats;

    @Column(name="per_seat_amount")
    private Long perSeatAmount;

    @Column(name="total_price")
    private Long totalPrice;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

}
