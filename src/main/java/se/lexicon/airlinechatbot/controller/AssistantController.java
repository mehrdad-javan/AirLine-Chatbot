package se.lexicon.airlinechatbot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import se.lexicon.airlinechatbot.domain.dto.ChatRequest;
import se.lexicon.airlinechatbot.service.AssistantServiceImpl;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final AssistantServiceImpl agent;

    public AssistantController(AssistantServiceImpl agent) {
        this.agent = agent;
    }


    @PostMapping("/reactive/chat")
    public Flux<String> reactiveChat(@RequestBody ChatRequest request) {
        return agent.reactiveChat(request.chatId(), request.message());
    }
}

