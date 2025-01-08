package se.lexicon.airlinechatbot.service;

import se.lexicon.airlinechatbot.domain.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    List<BookingResponse> getBookings();

    List<BookingResponse> getAvailableBookings();

    BookingResponse getBookingByBookingNumber(Long bookingNumber, String firstName, String lastName, Integer customerId);

    void cancelBooking(Long bookingNumber, String firstName, String lastName, Integer customerId);

    void reserveBooking(Long bookingNumber, String firstName, String lastName);
}
