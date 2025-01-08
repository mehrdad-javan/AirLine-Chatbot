package se.lexicon.airlinechatbot.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingNumber;
    private LocalDate date;
    private LocalDate bookingTo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "_from")
    private String from;
    @Column(name = "_to")
    private String to;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public Booking() {
    }

    public Booking(LocalDate date, Customer customer, BookingStatus bookingStatus, String from, String to) {
        this.date = date;
        this.customer = customer;
        this.bookingStatus = bookingStatus;
        this.from = from;
        this.to = to;
    }

    public Booking(Long bookingNumber, LocalDate date, LocalDate bookingTo, Customer customer, String from, String to) {
        this.bookingNumber = bookingNumber;
        this.date = date;
        this.bookingTo = bookingTo;
        this.customer = customer;
        this.from = from;
        this.to = to;
    }


}