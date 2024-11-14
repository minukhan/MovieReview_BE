package com.example.BE.chatgpt.controller;

import com.example.BE.chatgpt.dto.ChatGPTRequest;
import com.example.BE.chatgpt.dto.ChatGPTResponse;
import com.example.BE.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

//    @GetMapping("/chat")
//    public String chat(@RequestParam(name = "prompt")String prompt){
//        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
//        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
//        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
//    }

    @GetMapping("/{userId}/chat")
    public String chat(@PathVariable("userId") String userId) {
        String prompt = reviewService.getPreference(userId);
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

}