package com.app.booking_system.service;

import com.app.booking_system.config.UserAuthHelper;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Customer;
import com.app.booking_system.entity.Feedback;
import com.app.booking_system.exception.badRequestServiceAlartException;
import com.app.booking_system.repository.CustomerRepository;
import com.app.booking_system.repository.FeedbackRepository;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CustomerRepository customerRepository;
    public FeedbackService(FeedbackRepository feedbackRepository , CustomerRepository customerRepository){
        this.feedbackRepository=feedbackRepository;
        this.customerRepository=customerRepository;
    }

    public ResponseDTO createFeedback(Feedback feedback){

        Customer existCustomer=customerRepository.findById(feedback.getCustomer().getId())
                .orElseThrow(()->new badRequestServiceAlartException("Customer doesn't exist"));

        Feedback obj=Feedback.builder()
                .feedback(feedback.getFeedback())
                .customer(existCustomer)
                .build();

        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.feedbackRepository.save(obj))
                .statusCode(200)
                .build();
    }

    public ResponseDTO getAllFeedback(){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.feedbackRepository.findAll())
                .statusCode(200)
                .build();
    }
}
