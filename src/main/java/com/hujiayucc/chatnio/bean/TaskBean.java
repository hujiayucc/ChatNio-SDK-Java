package com.hujiayucc.chatnio.bean;

import java.util.List;

public record TaskBean(int id, int userId, String name, List<Message> messages, String model, boolean enableWeb) {

    /**
     * 对话ID
     *
     * @return id
     */
    @Override
    public int id() {
        return id;
    }

    /**
     * UserID
     *
     * @return userId
     */
    @Override
    public int userId() {
        return userId;
    }

    /**
     * 对话名称
     *
     * @return name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * 消息列表
     *
     * @return message
     */
    @Override
    public List<Message> messages() {
        return messages;
    }

    /**
     * AI模型
     *
     * @return model
     */
    @Override
    public String model() {
        return model;
    }

    /**
     * 是否开启联网搜索
     *
     * @return enableWeb
     */
    @Override
    public boolean enableWeb() {
        return enableWeb;
    }
}
