package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable int propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user
    ){
        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        Optional<Review> existingReview = reviewRepository.findByPropertyIdAndPropertyUserId(propertyId, user.getId());
        if (existingReview.isPresent()) {
            return new ResponseEntity<>("You have already reviewed this property", HttpStatus.BAD_REQUEST);
        }
        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);
        return new ResponseEntity<>("review added successfully", HttpStatus.OK);

    }

    @GetMapping("/userReview")
    public ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal PropertyUser user) {
        List<Review> reviews = reviewRepository.findByPropertyUser(user);
        return  new ResponseEntity<>(reviews,HttpStatus.OK);
    }

}
