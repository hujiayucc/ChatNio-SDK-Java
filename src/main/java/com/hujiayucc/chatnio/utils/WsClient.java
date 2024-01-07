package com.hujiayucc.chatnio.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import static com.hujiayucc.chatnio.ChatNio.API;

public class WsClient {
    private final HttpClient client;
    private final String url;
    private final String key;

    public WsClient(String url, String key) {
        client = HttpClient.newHttpClient();
        this.url = API + url;
        this.key = key;
    }

    public WebSocket connect(Consumer<String> onMessageReceived) {
        return client.newWebSocketBuilder()
                .header("Authorization", key)
                .buildAsync(URI.create(url), new WebSocket.Listener() {
                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("WebSocket Connection Opened.");
                    }

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        onMessageReceived.accept(data.toString());
                        webSocket.request(1);
                        return webSocket.sendText(data.toString(), last);
                    }

                    @Override
                    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                        System.out.println("WebSocket Connection Closed. Status:" + statusCode + " Reason:" + reason);
                        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
                    }

                    @Override
                    public void onError(WebSocket webSocket, Throwable error) {
                        System.out.println("Error occurred: " + error.getMessage());
                    }
                }).join();
    }
}