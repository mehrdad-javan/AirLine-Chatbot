package se.lexicon.airlinechatbot.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("se.lexicon.airlinechatbot")
public class AppConfig {


    // Use in-memory storage for development and testing
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory(); // Stores data in memory (non-persistent)
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }


    @Bean
    public CommandLineRunner ingestTermOfServiceToVectorStore(
            EmbeddingModel embeddingModel, VectorStore vectorStore,
            @Value("classpath:terms-of-service.txt") Resource termsOfServiceDocs) {

        return args -> {
            // Ingest the document into the vector store
            vectorStore.write(
                    new TokenTextSplitter().transform(
                            new TextReader(termsOfServiceDocs).read()));

            vectorStore.similaritySearch("Cancelling Bookings").forEach(doc -> {
                System.out.println("Similar Document: " + doc.getContent());
            });
        };
    }

}
