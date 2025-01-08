package se.lexicon.airlinechatbot.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.lexicon.airlinechatbot.domain.entity.BookingStatus;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingResponse(Long bookingNumber,
                              Integer id,
                              String firstName,
                              String lastName,
                              LocalDate date,
                              BookingStatus bookingStatus,
                              String from,
                              String to
) {


}