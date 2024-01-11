package com.hujiayucc.chatnio.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;

import static com.hujiayucc.chatnio.ChatNio.API;

public class WsClientAsync {
    protected WebSocket webSocket;
    protected Token token;
    protected CustomListener listener;

    // 创建新的 WebSocket 连接
    public WsClientAsync(Token token, CustomListener listener) {
        this.token = token;
        this.listener = listener;
        HttpClient client = HttpClient.newHttpClient();
        this.webSocket = client.newWebSocketBuilder().buildAsync(URI.create(getPath()), listener).join();
    }

    private String getBody(String message, String model, boolean enableWeb) {
        JSONObject body = new JSONObject()
                .fluentPut("type", "chat")
                .fluentPut("model", model)
                .fluentPut("message", message)
                .fluentPut("web", enableWeb);
        return body.toJSONString();
    }

    public CompletableFuture<MessageSegment> sendMessage(String message, String model, boolean enableWeb) {
        String body = getBody(message, model, enableWeb);
        CompletableFuture<MessageSegment> futureResponse = new CompletableFuture<>();
        listener.pendingMessages.add(futureResponse);
        webSocket.sendText(body, true);
        return futureResponse;
    }

    private String getPath() {
        return API.replaceFirst("^http", "ws") + "/chat";
    }
}