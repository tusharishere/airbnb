package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.entity.User;
import com.airbnb.payload.ReviewDto;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import com.airbnb.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewService reviewService;
    private PropertyRepository propertyRepository;
    private ReviewRepository reviewRepository;


    public ReviewController(ReviewService reviewService, PropertyRepository propertyRepository, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }
    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(
            @RequestBody ReviewDto reviewDto,
            @RequestParam long propertyId,
            @AuthenticationPrincipal User user
    ){
        Property property = propertyRepository.findById(propertyId).get();
        if(reviewRepository.existsByUserAndProperty(user, property)){
            return new ResponseEntity<>("user had already given the review", HttpStatus.OK);
        }
        reviewDto.setProperty(property);
        reviewDto.setUser(user);
        ReviewDto dto = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(
            @AuthenticationPrincipal User user
    ){
        List<Review> reviews = reviewService.getAllReviews(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleted(
            @RequestParam long id
    ){
        reviewService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
