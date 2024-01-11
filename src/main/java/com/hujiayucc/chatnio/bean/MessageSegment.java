package com.hujiayucc.chatnio.bean;

import com.alibaba.fastjson2.JSONObject;

public class MessageSegment {
    public String message;
    public String keyword;
    public int quota;
    public boolean end;

    public String getMessage() {
        return message;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getQuota() {
        return quota;
    }

    public boolean isEnd() {
        return end;
    }

    public String toString() {
        return new JSONObject()
                .fluentPut("message", message)
                .fluentPut("keyword", keyword)
                .fluentPut("quota", quota)
                .fluentPut("end", end)
                .toJSONString();
    }
}