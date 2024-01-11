package com.hujiayucc.chatnio.bean;

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
}