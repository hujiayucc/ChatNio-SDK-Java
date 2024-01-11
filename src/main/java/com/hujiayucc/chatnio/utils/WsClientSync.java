package com.hujiayucc.chatnio.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;

import static com.hujiayucc.chatnio.ChatNio.API;

public class WsClientSync {
    protected WebSocket webSocket;
    protected Token token;
    private final Queue<CompletableFuture<MessageSegment>> pendingMessages = new LinkedBlockingQueue<>();
    public final StringBuilder builder = new StringBuilder();

    private class CustomListener implements WebSocket.Listener {

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

    // 创建新的 WebSocket 连接
    public WsClientSync(Token token) {
        this.token = token;
        HttpClient client = HttpClient.newHttpClient();
        this.webSocket = client.newWebSocketBuilder()
                .buildAsync(URI.create(getPath()), new CustomListener()).join();
    }
    private String getBody(String message, String model, boolean enableWeb) {
        JSONObject body = new JSONObject()
               .fluentPut("type", "chat")
               .fluentPut("model", model)
               .fluentPut("message", message)
               .fluentPut("web", enableWeb);
        return body.toJSONString();
    }
    public CompletableFuture<MessageSegment> sendMessageAsync(String message, String model, boolean enableWeb) {
        String body = getBody(message, model, enableWeb);
        CompletableFuture<MessageSegment> futureResponse = new CompletableFuture<>();
        pendingMessages.add(futureResponse);
        webSocket.sendText(body, true);
        return futureResponse;
    }

    public Queue<CompletableFuture<MessageSegment>> sendMessage(String message, String model, boolean enableWeb) {
        String body = getBody(message, model, enableWeb);
        CompletableFuture<MessageSegment> futureResponse = new CompletableFuture<>();
        pendingMessages.add(futureResponse);
        webSocket.sendText(body,true);
        return pendingMessages;
    }

    private String getPath() {
        return API.replaceFirst("^http", "ws") + "/chat";
    }
}
