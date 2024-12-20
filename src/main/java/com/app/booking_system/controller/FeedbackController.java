package com.app.booking_system.controller;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Feedback;
import com.app.booking_system.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService=feedbackService;
    }

    @PostMapping("/create")
    public ResponseDTO createFeedback(@RequestBody Feedback feedback){
        return this.feedbackService.createFeedback(feedback);
    }

    @GetMapping("/fetch")
    public ResponseDTO getAllFeedback(){
        return this.feedbackService.getAllFeedback();

    }
}
