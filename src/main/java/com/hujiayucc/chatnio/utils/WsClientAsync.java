package com.hujiayucc.chatnio.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;

import static com.hujiayucc.chatnio.ChatNio.API;

/**
 * WS
 */
public class WsClientAsync {
    /**
     * WS
     */
    protected WebSocket webSocket;
    /**
     * Token信息
     */
    protected Token token;
    /**
     * 自定义监听器
     */
    protected CustomListener listener;

    /**
     * 创建WS连接
     * @param token Token信息
     * @param listener 监听器
     */
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

    /**
     * 发送消息
     * @param message 消息内容
     * @param model 模型
     * @param enableWeb 是否开启WEB
     * @return 异步回调 {@link CompletableFuture}
     */
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