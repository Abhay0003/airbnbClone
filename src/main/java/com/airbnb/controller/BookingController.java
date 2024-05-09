package com.airbnb.controller;

import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingRepository bookingRepository;

    private PropertyRepository propertyRepository;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<Booking> createBooking(
            @RequestBody Booking booking,
            @AuthenticationPrincipal PropertyUser user,
            @PathVariable int propertyId
            ) {
        booking.setPropertyUser(user);
        Property property = propertyRepository.findById(propertyId).get();
        int propertyPrice = property.getNightlyPrice();
        int totalNights = booking.getTotalNights();
        int totalPrice = propertyPrice * totalNights;
        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);
        Booking createdBooking = bookingRepository.save(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
}
