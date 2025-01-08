package se.lexicon.airlinechatbot.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingRequest(
        @NotNull Long bookingNumber,
        @NotBlank String firstName,
        @NotBlank String lastName,
        Integer customerId) {
}