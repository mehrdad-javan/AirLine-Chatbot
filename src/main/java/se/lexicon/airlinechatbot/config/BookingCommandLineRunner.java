package se.lexicon.airlinechatbot.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.airlinechatbot.domain.entity.Booking;
import se.lexicon.airlinechatbot.repository.BookingRepository;

import java.util.List;

@Component
public class BookingCommandLineRunner implements CommandLineRunner {

    private final BookingRepository bookingRepository;

    public BookingCommandLineRunner(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {
        // Generate initial bookings using DataInitializer
        List<Booking> bookings = DataInitializer.generateBookings();

        // Save bookings into the database
        bookingRepository.saveAll(bookings);

        System.out.println("Bookings have been initialized and saved to the database.");
    }
}
