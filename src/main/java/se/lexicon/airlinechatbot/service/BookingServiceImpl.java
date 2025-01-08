package se.lexicon.airlinechatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.airlinechatbot.repository.BookingRepository;
import se.lexicon.airlinechatbot.domain.dto.BookingResponse;
import se.lexicon.airlinechatbot.domain.entity.Booking;
import se.lexicon.airlinechatbot.domain.entity.BookingStatus;
import se.lexicon.airlinechatbot.domain.entity.Customer;
import se.lexicon.airlinechatbot.exception.BookingServiceException;
import se.lexicon.airlinechatbot.repository.BookingRepository;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    @Override
    public List<BookingResponse> getBookings() {
        return bookingRepository.findAll().stream() // Use JPA to retrieve all bookings
                .map(this::toBookingResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getAvailableBookings() {
        List<BookingResponse> bookingResponses = bookingRepository.findByBookingStatus(BookingStatus.AVAILABLE).stream() // Use JPA to retrieve all bookings
                .map(this::toBookingResponse)
                .toList();
        System.out.println("bookingResponses = " + bookingResponses);
        return bookingResponses;
    }

    @Override
    public BookingResponse getBookingByBookingNumber(Long bookingNumber, String firstName, String lastName, Integer customerId) {
        Booking booking = findBooking(bookingNumber, firstName, lastName, customerId);
        return toBookingResponse(booking);
    }

    private Booking findBooking(Long bookingNumber, String firstName, String lastName, Integer customerId) {
        return bookingRepository.findByBookingNumberAndCustomer_FirstNameAndCustomer_LastNameAndCustomer_Id(
                bookingNumber, firstName, lastName, customerId
        ).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }


    @Override
    public void cancelBooking(Long bookingNumber, String firstName, String lastName, Integer customerId) {
        try {
            Booking booking = findBooking(bookingNumber, firstName, lastName, customerId);

            if (booking.getBookingStatus() == BookingStatus.RESERVED) {
                booking.setBookingStatus(BookingStatus.AVAILABLE);
                booking.setCustomer(null);
                bookingRepository.save(booking);
                System.out.println("Booking " + bookingNumber + " has been successfully canceled.");
            } else {
                throw new IllegalStateException("Booking is not reserved and cannot be canceled.");
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            throw new BookingServiceException("Cancellation failed: " + ex.getMessage());
        }
    }


    @Override
    public void reserveBooking(Long bookingNumber, String firstName, String lastName) {
        System.out.println("bookingNumber = " + bookingNumber);
        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        Booking booking = bookingRepository.findByBookingNumberAndBookingStatus(bookingNumber, BookingStatus.AVAILABLE)
                .orElseThrow(() -> new IllegalArgumentException("Booking not available for reservation"));

        // Reserve the booking
        booking.setCustomer(new Customer(firstName, lastName));
        booking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(booking);  // Persist the change
        System.out.println("Booking " + bookingNumber + " has been successfully reserved.");
    }

    private BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingNumber(),
                booking.getCustomer() != null ? booking.getCustomer().getId() : null,
                booking.getCustomer() != null ? booking.getCustomer().getFirstName() : null,
                booking.getCustomer() != null ? booking.getCustomer().getLastName() : null,
                booking.getDate(),
                booking.getBookingStatus(),
                booking.getFrom(),
                booking.getTo()
        );
    }
}
