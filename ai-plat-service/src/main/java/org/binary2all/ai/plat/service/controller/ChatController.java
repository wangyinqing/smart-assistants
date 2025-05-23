package org.binary2all.ai.plat.service.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder aiBuilder) {
        this.chatClient = aiBuilder.build();
    }

    @GetMapping("/{user}/assistant")
    public String inquire(@PathVariable String user, @RequestParam String question){
        return  chatClient.prompt().user(question).call().content();
    }

}
