package com.hujiayucc.chatnio.bean;

import com.alibaba.fastjson2.JSONObject;

/**
 * Token信息
 */
public class Token {
    /**
     * 匿名 Token
     * @param id 对话ID, -1 为新对话
     * @return 匿名Token
     */
    public static Token Anonymous(int id) {
        return new Token("anonymous", id);
    }

    /**
     * 匿名Token
     * @param task 对话
     * @return 匿名Token
     */
    public static Token Anonymous(TaskBean task) {
        return new Token("anonymous", task.id());
    }

    /**
     * 匿名 Token
     */
    public static final String Anonymous = "anonymous";
    /**
     * 新对话 ID
     */
    public static final int NewTaskId = -1;


    private final String token;
    private final int id;

    /**
     * 聊天验证 Token
     * @param token JwT Token/API Key
     * @param id 对话ID， -1 为新对话
     */
    public Token(String token, int id) {
        this.token = token;
        this.id = id;
    }

    /**
     * 聊天验证 Token
     * @param token JwT Token/API Key
     * @param task 对话
     */
    public Token(String token, TaskBean task) {
        this.token = token;
        this.id = task.id();
    }

    /**
     * 转换成JSON字符串
     */
    @Override
    public String toString() {
        return new JSONObject().fluentPut("token", token).fluentPut("id", id).toJSONString();
    }

    /**
     * 获取 Token
     * @return Token
     */
    public String getToken() {
        return token;
    }

    /**
     * 获取对话ID
     * @return 对话ID
     */
    public int id() {
        return id;
    }
}
