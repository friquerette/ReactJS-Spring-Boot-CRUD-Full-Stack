package net.javaguides.springboot.service;

import org.springframework.ai.bedrock.jurassic2.BedrockAi21Jurassic2ChatModel;
import org.springframework.stereotype.Service;


@Service
public class AIService {

    private final BedrockAi21Jurassic2ChatModel chatModel;

    public AIService(BedrockAi21Jurassic2ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getOneQuestion(String message) {
        try {
            return chatModel.call(message);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
