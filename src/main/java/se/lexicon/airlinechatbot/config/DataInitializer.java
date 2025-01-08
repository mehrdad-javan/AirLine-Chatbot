package se.lexicon.airlinechatbot.config;

import se.lexicon.airlinechatbot.domain.entity.Booking;
import se.lexicon.airlinechatbot.domain.entity.BookingStatus;
import se.lexicon.airlinechatbot.domain.entity.Customer;
import se.lexicon.airlinechatbot.domain.entity.Booking;
import se.lexicon.airlinechatbot.domain.entity.BookingStatus;
import se.lexicon.airlinechatbot.domain.entity.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {

    // Method to generate a list of initial bookings
    public static List<Booking> generateBookings() {
        List<Booking> bookings = new ArrayList<>();

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John", "Doe"));
        customers.add(new Customer("Jane", "Smith"));
        customers.add(new Customer("Michael", "Johnson"));
        customers.add(new Customer("Emily", "Williams"));

        bookings.add(new Booking( LocalDate.now(), null, BookingStatus.AVAILABLE, "LAX", "JFK"));
        bookings.add(new Booking( LocalDate.now().plusDays(1), customers.get(0), BookingStatus.RESERVED, "SFO", "LHR"));
        bookings.add(new Booking( LocalDate.now().plusDays(2), null, BookingStatus.AVAILABLE, "CDG", "FRA"));
        bookings.add(new Booking( LocalDate.now().plusDays(3), customers.get(1), BookingStatus.RESERVED, "HEL", "TXL"));
        bookings.add(new Booking( LocalDate.now().plusDays(4), null, BookingStatus.AVAILABLE, "ARN", "MAD"));
        bookings.add(new Booking( LocalDate.now().plusDays(5), customers.get(2), BookingStatus.RESERVED, "JFK", "SFO"));
        bookings.add(new Booking( LocalDate.now().plusDays(6), null, BookingStatus.AVAILABLE, "LHR", "CDG"));
        bookings.add(new Booking( LocalDate.now().plusDays(7), customers.get(3), BookingStatus.RESERVED, "TXL", "ARN"));
        bookings.add(new Booking( LocalDate.now().plusDays(8), null, BookingStatus.AVAILABLE, "FRA", "HEL"));
        bookings.add(new Booking( LocalDate.now().plusDays(9), null, BookingStatus.AVAILABLE, "MAD", "LAX"));

        return bookings;
    }
}
