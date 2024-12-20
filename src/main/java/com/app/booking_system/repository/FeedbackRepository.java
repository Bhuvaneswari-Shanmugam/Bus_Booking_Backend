package com.app.booking_system.repository;

import com.app.booking_system.entity.Feedback;
import com.app.booking_system.service.FeedbackService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository  extends JpaRepository<Feedback, String> {
}
