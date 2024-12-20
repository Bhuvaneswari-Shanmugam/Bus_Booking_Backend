package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Feedback;
import com.app.bookingsystem.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService=feedbackService;
    }

    @PostMapping("/create-feedback")
    public ResponseDTO createFeedback(@RequestBody final Feedback feedback){
        return this.feedbackService.createFeedback(feedback);
    }

    @GetMapping("/retrieve-all-feedbacks")
    public ResponseDTO retrieveAllFeedbacks(){
        return this.feedbackService.getAllFeedback();

    }
}
