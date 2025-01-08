package se.lexicon.airlinechatbot.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingServiceException.class)
    public String handleBookingServiceException(BookingServiceException ex) {
        return "Booking Service Error: " + ex.getMessage();
    }
}
