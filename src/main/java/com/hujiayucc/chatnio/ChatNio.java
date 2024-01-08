package com.hujiayucc.chatnio;

import com.hujiayucc.chatnio.data.Pets;
import com.hujiayucc.chatnio.data.Tasks;

public class ChatNio {
    public static final String API = "https://api.chatnio.net";
    private final Pets pets;
    private final Tasks tasks;
    /**
     * 创建ChatNio实例
     * @param key 密钥
     */
    public ChatNio(String key) {
        pets = new Pets(key);
        tasks = new Tasks(key);
    }

    /** 余额 */
    public Pets Pets() {
        return pets;
    }
    /** 对话 */
    public Tasks Tasks() {
        return tasks;
    }
}
