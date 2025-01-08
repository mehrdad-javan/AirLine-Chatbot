package se.lexicon.airlinechatbot.exception;

public class BookingServiceException extends RuntimeException {
    public BookingServiceException(String message) {
        super(message);
    }
}
