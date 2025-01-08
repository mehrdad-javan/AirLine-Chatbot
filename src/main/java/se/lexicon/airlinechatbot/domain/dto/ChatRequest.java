package se.lexicon.airlinechatbot.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank String chatId,
        @NotBlank String message
) {
}
