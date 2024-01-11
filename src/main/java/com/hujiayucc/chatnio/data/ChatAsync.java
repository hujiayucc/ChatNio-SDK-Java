package com.hujiayucc.chatnio.data;

import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;
import com.hujiayucc.chatnio.utils.CustomListener;
import com.hujiayucc.chatnio.utils.WsClientAsync;

import java.util.concurrent.CompletableFuture;

public class ChatAsync extends CustomListener {
    private final WsClientAsync wsClientAsync;

    public ChatAsync(Token token) {
        super(token);
        wsClientAsync = new WsClientAsync(token,this);
    }

    public CompletableFuture<MessageSegment> send(String message, String model, boolean enableWeb) {
        return wsClientAsync.sendMessage(message, model, enableWeb);
    }

    public String getMessage() {
        return builder.toString();
    }
}
