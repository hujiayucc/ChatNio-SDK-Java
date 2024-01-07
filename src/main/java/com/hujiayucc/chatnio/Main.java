package com.hujiayucc.chatnio;

import com.hujiayucc.chatnio.exception.AuthException;
import com.hujiayucc.chatnio.exception.FieldException;

public class Main {

    /**
     * 以下只是一个简单的示例
     */
    public static void main(String[] args) {
        ChatNio chatNio = new ChatNio("你的密钥");
        try {
            float quota = chatNio.Pets().getQuota();
            boolean buy = chatNio.Pets().buy(200);
            boolean cert = chatNio.Pets().getCert();
            boolean teenager = chatNio.Pets().getTeenager();
            System.out.println(quota);
            System.out.println(buy);
            System.out.println(cert);
            System.out.println(teenager);
        } catch (AuthException e) {
            System.out.println("验证失败");
        } catch (FieldException e) {
            System.out.println("字段错误");
        }
    }
}