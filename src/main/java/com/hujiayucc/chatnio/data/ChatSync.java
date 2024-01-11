package com.hujiayucc.chatnio.data;

import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;
import com.hujiayucc.chatnio.utils.WsClientSync;

import java.util.concurrent.CompletableFuture;

public class ChatSync {
    private final WsClientSync wsClient;

    /**
     * 同步
     * @param token token
     */
    public ChatSync(Token token) {
        wsClient = new WsClientSync(token);
    }

    public CompletableFuture<MessageSegment> send(String message, String model, boolean enableWeb) {
        return wsClient.sendMessageAsync(message, model, enableWeb);
    }

    public String getMessage() {
        return wsClient.builder.toString();
    }
}
