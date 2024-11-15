package com.example.BE.chatgpt.controller;

import com.example.BE.chatgpt.dto.ChatGPTRequest;
import com.example.BE.chatgpt.dto.ChatGPTResponse;
import com.example.BE.chatgpt.dto.KeywordDto;
import com.example.BE.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class CustomBotController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{userId}/chat")
    public ResponseEntity chat(@PathVariable("userId") String userId) {
        String prompt = reviewService.getPreference(userId);
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
        String responseContent = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        // JSON 문자열을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KeywordDto keywordDto = null;
        try {
            keywordDto = objectMapper.readValue(responseContent, KeywordDto.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(keywordDto);
    }

}