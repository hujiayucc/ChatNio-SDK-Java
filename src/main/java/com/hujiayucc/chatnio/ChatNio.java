package com.hujiayucc.chatnio;

import com.hujiayucc.chatnio.data.Pets;

public class ChatNio {
    public static final String API = "https://api.chatnio.net";
    private final Pets pets;
    /**
     * 创建ChatNio实例
     * @param key 密钥
     */
    public ChatNio(String key) {
        pets = new Pets(key);
    }

    /** 余额 */
    public Pets Pets() {
        return pets;
    }
}
