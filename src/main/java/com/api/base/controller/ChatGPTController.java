package com.api.base.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatGPTController {

//    @GetMapping(value = "/completions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> sendMessage(@RequestHeader("prompt") String prompt, @RequestParam("userId") String userId, @RequestHeader(name = "Authorization") String accessToken) {
//        var input = ChatGptInputDTO.builder().prompt(MbUtils.decodeURI(prompt)).userId(userId).build();
//        log.info("CHATGPT-LOG: token - {}", accessToken);
//        if (Objects.isNull(input) || input.getMessageArray() == null || input.getMessageArray().size() == 0) {
//            throw new BadRequestException();
//        }
//        return chatGptService.sendMessage(input, accessToken);
//    }
}
