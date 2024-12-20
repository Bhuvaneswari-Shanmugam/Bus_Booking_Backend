package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Customer;
import com.app.bookingsystem.entity.Feedback;
import com.app.bookingsystem.exception.BadRequestServiceAlertException;
import com.app.bookingsystem.repository.CustomerRepository;
import com.app.bookingsystem.repository.FeedbackRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CustomerRepository customerRepository;
    public FeedbackService(FeedbackRepository feedbackRepository ,CustomerRepository customerRepository){
        this.feedbackRepository=feedbackRepository;
        this.customerRepository=customerRepository;
    }

    public ResponseDTO createFeedback(final Feedback feedback){

       final  Customer existCustomer=customerRepository.findById(feedback.getCustomer().getId())
                .orElseThrow(()->new BadRequestServiceAlertException("Customer doesn't exist"));
        final Feedback obj=Feedback.builder()
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
