package se.lexicon.airlinechatbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import se.lexicon.airlinechatbot.domain.dto.BookingRequest;
import se.lexicon.airlinechatbot.exception.BookingServiceException;

import java.time.LocalDate;
import java.util.function.Function;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class AssistantServiceImpl implements AssistantService {


    private final ChatClient chatClient;  // Correct client reference
    private final BookingService bookingService;
    private final ChatMemory chatMemory;

    @Autowired
    public AssistantServiceImpl(BookingService bookingService, ChatClient.Builder modelBuilder, ChatMemory chatMemory) {

        String systemInstruction = """
                    You are a customer chat support agent of an airline named "LexFunAirLine."
                    Respond in a friendly, helpful, and joyful manner.
                    You are interacting with customers through an online chat system.
                    Before providing information about a booking, reserving, or cancelling a booking, you MUST ensure the user provides the following:
                    - For **reserving a booking**, you MUST get the booking number, first name, and last name of the customer.
                    - For **cancelling a booking**, you MUST get the booking number, first name, last name, AND customer ID.
                    Ensure that this information is checked from the user input or message history before proceeding with a reservation or cancellation.
                    Today is {current_date}.
                """;


        this.bookingService = bookingService;
        this.chatMemory = chatMemory;

        this.chatClient = modelBuilder
                .defaultSystem(systemInstruction) // System Instructions: Provide context for the chatbot's role and behavior.
                .defaultFunctions("reserve", "cancel") // Functions: Enable specific function calls triggered by user prompts
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory) // Retrieves memory and incorporates it into the prompt’s system text.
                )  // Advisors: Manage chat memory, map function calls, and enhance conversational flow.
                .build();
    }

    @Override
    public Flux<String> reactiveChat(String chatId, String input) {
        return this.chatClient.prompt() // Initializes a new prompt session.
                .system(s -> s.param("current_date", LocalDate.now().toString())) // Injects dynamic system parameters into the conversation.
                .user(input) // Sets the user's input message or Adds the user's message (input) to the conversation.
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .stream()  // Executes the configured chat prompt and retrieves the AI response - Calls the AI service.

                .content();
    }



    // Functions Registration
    @Bean("reserve")
    @Description("Reserve booking")
    public Function<BookingRequest, String> reserve() {
        return request -> {
            try {
                bookingService.reserveBooking(
                        request.bookingNumber(),
                        request.firstName(),
                        request.lastName());
                return "Booking reserved successfully.";
            } catch (BookingServiceException ex) {
                return "Error during reservation: " + ex.getMessage();
            }
        };
    }

    @Bean("cancel")
    @Description("Cancel booking")
    public Function<BookingRequest, String> cancel() {
        return request -> {
            try {
                bookingService.cancelBooking(
                        request.bookingNumber(),
                        request.firstName(),
                        request.lastName(),
                        request.customerId());
                return "Booking canceled successfully.";
            } catch (BookingServiceException ex) {
                return "Error during cancellation: " + ex.getMessage();
            }
        };
    }


    public void clearChatMemory(String chatId) {
        chatMemory.clear(chatId);  // Calls the ChatMemory implementation to clear data for this chatId
        System.out.println("Cleared chat memory for chatId: " + chatId);

    }


}