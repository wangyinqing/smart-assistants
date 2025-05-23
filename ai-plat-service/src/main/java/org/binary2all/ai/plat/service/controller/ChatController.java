package org.binary2all.ai.plat.service.controller;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final OpenAiChatClient chatClient;

    @Autowired
    public ChatController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // Define a simple DTO for the request
    public static class ChatRequest {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Define a simple DTO for the response
    public static class ChatCompletionResponse {
        private String reply;

        public ChatCompletionResponse(String reply) {
            this.reply = reply;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }
    }

    @PostMapping("/completions")
    public ChatCompletionResponse getChatCompletion(@RequestBody ChatRequest chatRequest) {
        if (chatRequest == null || chatRequest.getMessage() == null || chatRequest.getMessage().isEmpty()) {
            // Consider throwing an appropriate HTTP error like 400 Bad Request
            return new ChatCompletionResponse("Error: Message cannot be empty.");
        }
        
        Prompt prompt = new Prompt(chatRequest.getMessage());
        ChatResponse aiResponse = chatClient.call(prompt);
        
        if (aiResponse != null && aiResponse.getResult() != null) {
            return new ChatCompletionResponse(aiResponse.getResult().getOutput().getContent());
        } else {
            // Handle cases where the response or its content might be null
            return new ChatCompletionResponse("Error: No response from AI model.");
        }
    }
}
