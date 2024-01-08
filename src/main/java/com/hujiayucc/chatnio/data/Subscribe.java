package com.hujiayucc.chatnio.data;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hujiayucc.chatnio.enums.SubLevel;
import com.hujiayucc.chatnio.exception.AuthException;
import com.hujiayucc.chatnio.exception.BuyException;
import com.hujiayucc.chatnio.exception.FieldException;
import com.hujiayucc.chatnio.utils.GetClient;
import com.hujiayucc.chatnio.utils.PostClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class Subscribe {
    private final String key;

    public Subscribe(String key) {
        this.key = key;
    }

    /**
     * 查询是否订阅
     * @return 订阅情况
     * @throws AuthException 认证失败
     * @throws FieldException 字段错误
     */
    public boolean isSubscribed() throws AuthException, FieldException {
        GetClient client;
        try {
            client = new GetClient("/subscription", key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            return JSON.parseObject(client.body()).getBoolean("is_subscribed");
        }
        throw new FieldException("Subscription select failed.");
    }

    /**
     * 查询订阅剩余时间
     * @return 剩余时间
     * @throws AuthException 认证失败
     * @throws FieldException 字段错误
     */
    public int expired() throws AuthException, FieldException {
        GetClient client;
        try {
            client = new GetClient("/subscription", key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            return JSON.parseObject(client.body()).getInteger("expired");
        }
        throw new FieldException("Subscription select failed.");
    }

    /**
     * 订阅
     * @param month 订阅月数,介于 0 ~ 999 之间的整数
     * @param level 订阅等级
     * @return 是否订阅成功
     * @throws AuthException 认证失败
     * @throws FieldException 字段错误
     * @throws BuyException 订阅失败
     */
    public boolean subscribe(int month, SubLevel level) throws AuthException, FieldException, BuyException {
        if (month < 1 || month > 999) throw new FieldException("购买月数 介于 0 ~ 999 之间的整数");
        if (level == SubLevel.Normal) throw new FieldException("订阅级别不能为 普通用户");
        PostClient client;
        try {
            client = new PostClient("/subscribe", "month=" + month + "&level=" + level.getLevel(), key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            JSONObject json = JSON.parseObject(client.body());
            if (json.getBoolean("status")) return true;
            throw new BuyException(json.getString("error"));
        }
        throw new FieldException("Subscription buy failed.");
    }
}
