package org.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.app.service.IChatService;
import org.example.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IChatService chatService;

    /**
     * 发送消息, 简单文本输入，直接输出
     *
     * @param msg msg
     * @return {@link Result }<{@link String }>
     */
    @GetMapping("/sendMsg")
    public Result<String> sendMsg(@RequestParam String msg) {
        String chatRes = chatService.sendMsg(msg);
        return Result.success(chatRes);
    }
}
