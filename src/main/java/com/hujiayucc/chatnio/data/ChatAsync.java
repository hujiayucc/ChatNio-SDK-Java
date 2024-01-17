package com.hujiayucc.chatnio.data;

import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;
import com.hujiayucc.chatnio.utils.CustomListener;
import com.hujiayucc.chatnio.utils.WsClientAsync;

import java.util.concurrent.CompletableFuture;

/**
 * 聊天-异步
 */
public class ChatAsync extends CustomListener {
    private final WsClientAsync wsClientAsync;

    /**
     * 聊天-异步
     * @param token Token信息
     */
    public ChatAsync(Token token) {
        super(token);
        wsClientAsync = new WsClientAsync(token,this);
    }

    /**
     * 发送消息
     * @param message 消息内容
     * @param model 模型
     * @param enableWeb 是否开启WEB
     * @return 异步数据 {@link CompletableFuture<MessageSegment>}
     */
    public CompletableFuture<MessageSegment> send(String message, String model, boolean enableWeb) {
        return wsClientAsync.sendMessage(message, model, enableWeb);
    }

    /**
     * 获取完整消息内容
     * @return 消息内容
     */
    public String getMessage() {
        return builder.toString();
    }
}
