package com.hujiayucc.chatnio.bean;

import com.alibaba.fastjson2.JSONObject;

/**
 * 消息段
 */
public class MessageSegment {
    /**
     * 消息内容
     */
    public String message;
    /**
     * keyword
     */
    public String keyword;
    /**
     * 使用金额
     */
    public int quota;
    /**
     * 是否已获取完全
     */
    public boolean end;

    /**
     * 获取消息内容
     * @return 消息内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取keyword
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 获取使用金额
     * @return 使用金额
     */
    public int getQuota() {
        return quota;
    }

    /**
     * 是否已获取完全
     * @return 是否已获取完全
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * @return Json字符串
     */
    public String toString() {
        return new JSONObject()
                .fluentPut("message", message)
                .fluentPut("keyword", keyword)
                .fluentPut("quota", quota)
                .fluentPut("end", end)
                .toJSONString();
    }
}