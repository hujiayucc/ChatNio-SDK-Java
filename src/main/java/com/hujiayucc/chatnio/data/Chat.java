package com.hujiayucc.chatnio.data;

import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;
import com.hujiayucc.chatnio.utils.WsClient;

import java.util.concurrent.CompletableFuture;

public class Chat {
    private final WsClient wsClient;

    public Chat(Token token) {
        wsClient = new WsClient(token);
    }

    public CompletableFuture<MessageSegment> send(String message, String model, boolean enableWeb) {
        return wsClient.sendMessage(message, model, enableWeb);
    }

    public String getMessage() {
        return wsClient.builder.toString();
    }
}
