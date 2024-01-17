package com.hujiayucc.chatnio;

import com.hujiayucc.chatnio.bean.Models;
import com.hujiayucc.chatnio.data.Pets;
import com.hujiayucc.chatnio.data.Subscribe;
import com.hujiayucc.chatnio.data.Tasks;

/**
 * ChatNio
 */
public class ChatNio {
    /** 默认API地址，后期会考虑自行更改节点 */
    public static final String API = "https://api.chatnio.net";
    private final Pets pets;
    private final Tasks tasks;
    private final Models models;
    private final Subscribe subscribe;
    /**
     * 创建ChatNio实例
     * @param key 密钥
     */
    public ChatNio(String key) {
        pets = new Pets(key);
        tasks = new Tasks(key);
        models = new Models();
        subscribe = new Subscribe(key);
    }

    /**
     * 余额
     * @return {@link com.hujiayucc.chatnio.data.Pets}
     */
    public Pets Pets() {
        return pets;
    }

    /**
     * 对话
     * @return {@link com.hujiayucc.chatnio.data.Tasks}
     */
    public Tasks Tasks() {
        return tasks;
    }

    /**
     * 订阅和礼包
     * @return {@link com.hujiayucc.chatnio.data.Subscribe}
     */
    public Subscribe Subscribe() {
        return subscribe;
    }

    /**
     * 模型
     * @return {@link com.hujiayucc.chatnio.bean.Models}
     */
    public Models Models() {
        return models;
    }
}
