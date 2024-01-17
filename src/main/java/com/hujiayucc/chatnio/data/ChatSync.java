package com.hujiayucc.chatnio.data;

import com.hujiayucc.chatnio.bean.MessageSegment;
import com.hujiayucc.chatnio.bean.Token;
import com.hujiayucc.chatnio.utils.WsClientSync;

import java.util.concurrent.CompletableFuture;

/**
 * 聊天-同步
 */
public class ChatSync {
    private final WsClientSync wsClient;

    /**
     * 聊天-同步
     * @param token Token信息
     */
    public ChatSync(Token token) {
        wsClient = new WsClientSync(token);
    }

    /**
     * 发送消息
     * @param message 消息内容
     * @param model 模型
     * @param enableWeb 是否开启WEB
     * @return 同步数据 {@link CompletableFuture<MessageSegment>}
     */
    public CompletableFuture<MessageSegment> send(String message, String model, boolean enableWeb) {
        return wsClient.sendMessageAsync(message, model, enableWeb);
    }

    /**
     * 获取完整消息内容
     * @return 消息内容
     */
    public String getMessage() {
        return wsClient.builder.toString();
    }
}
