package com.hujiayucc.chatnio.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;

import java.net.http.WebSocket;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomListener implements WebSocket.Listener {
    public final StringBuilder builder = new StringBuilder();
    public final Queue<CompletableFuture<MessageSegment>> pendingMessages = new LinkedBlockingQueue<>();
    public final Token token;

    public CustomListener(Token token) {
        this.token = token;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        JSONObject body = new JSONObject().fluentPut("token", token.toString());
        webSocket.sendText(body.toJSONString(), true);
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        MessageSegment message = JSONObject.parseObject(data.toString(), MessageSegment.class);
        if (message.isEnd()) {
            CompletableFuture<MessageSegment> future = pendingMessages.poll();
            if (future != null) {
                future.complete(message);
            }
        }
        builder.append(message.getMessage());
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        while (!pendingMessages.isEmpty()) {
            CompletableFuture<MessageSegment> future = pendingMessages.poll();
            future.completeExceptionally(error);
        }
        WebSocket.Listener.super.onError(webSocket, error);
    }
}
