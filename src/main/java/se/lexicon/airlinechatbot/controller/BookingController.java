package se.lexicon.airlinechatbot.controller;

import org.springframework.web.bind.annotation.*;
import se.lexicon.airlinechatbot.domain.dto.BookingRequest;
import se.lexicon.airlinechatbot.domain.dto.BookingResponse;
import se.lexicon.airlinechatbot.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingResponse> getBookings() {
        return bookingService.getBookings();
    }


    // Reserve a booking
    @PostMapping("/reserve")
    public String reserveBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.reserveBooking(
                bookingRequest.bookingNumber(),
                bookingRequest.firstName(),
                bookingRequest.lastName()
        );
        return "Booking " + bookingRequest.bookingNumber() + " has been successfully reserved.";
    }

    // Cancel a booking
    @PostMapping("/cancel")
    public String cancelBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.cancelBooking(
                bookingRequest.bookingNumber(),
                bookingRequest.firstName(),
                bookingRequest.lastName(),
                bookingRequest.customerId()
        );
        return "Booking " + bookingRequest.bookingNumber() + " has been successfully canceled.";
    }
}
