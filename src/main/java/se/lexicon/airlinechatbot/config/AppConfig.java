package se.lexicon.airlinechatbot.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("se.lexicon.airlinechatbot")
public class AppConfig {


    // Use in-memory storage for development and testing
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory(); // Stores data in memory (non-persistent)
    }


}
