package com.hujiayucc.chatnio;

import com.hujiayucc.chatnio.bean.*;
import com.hujiayucc.chatnio.data.Chat;
import com.hujiayucc.chatnio.enums.SubLevel;
import com.hujiayucc.chatnio.exception.AuthException;
import com.hujiayucc.chatnio.exception.BuyException;
import com.hujiayucc.chatnio.exception.FieldException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    /**
     * 以下只是一个简单的示例
     */
    public static void main(String[] args) {
        String key = "你的密钥";
        ChatNio chatNio = new ChatNio(key);
        try {
            float quota = chatNio.Pets().getQuota();
            boolean cert = chatNio.Pets().getCert();
            boolean teenager = chatNio.Pets().getTeenager();
            System.out.println("Quota: " + quota + " Cert: " + cert + " Teenager: " + teenager);

            boolean buy = chatNio.Pets().buy(200);
            System.out.println(buy);
        } catch (AuthException | FieldException | BuyException e) {
            System.out.println(e.getMessage());
        }

        try {
            // 对话数量
            int TaskList = chatNio.Tasks().getTaskList().size();
            for (int i = 0; i < TaskList; i++) {
                TaskBean taskBean = chatNio.Tasks().getTaskList().get(i);
                int id = taskBean.id();
                int userId = taskBean.userId();
                String name = taskBean.name();
                List<Message> messages = taskBean.messages();
                String model = taskBean.model();
                boolean enableWeb = taskBean.enableWeb();
                System.out.println("ID: " + id + ", UserID: " + userId + ", Name: " + name + ", Model, " + model + ", EnableWeb: " + enableWeb);
                for (Message message : messages) {
                    Role role = message.role();
                    String content = message.content();
                    System.out.println("Role: " + role.getRole() + ", Content: " + content);
                }
            }
        } catch (FieldException | AuthException e) {
            System.out.println(e.getMessage());
        }

        try {
            boolean isSubscribe = chatNio.Subscribe().isSubscribed();
            System.out.println("isSubscribe: " + isSubscribe);
            boolean subscribe = chatNio.Subscribe().subscribe(1, SubLevel.Standard);
            System.out.println("subscribe: " + subscribe);
        } catch (AuthException | FieldException | BuyException e) {
            System.out.println(e.getMessage());
        }

        Models models = chatNio.Models();

        for (String name : models.getAll()) {
            System.out.println(name);
        }

        System.out.println("There are a total of " + models.getSize() + " Models");
        System.out.println("Default Model: " + Models.getDefault());

        try {
            // Token token = new Token(Token.Anonymous, Token.NewTaskId);
            Token token = new Token(key, Token.NewTaskId);
            Chat chat = new Chat(token);
            CompletableFuture<MessageSegment> message = chat.send("Hello World", Models.getDefault(), false);
            message.thenApply(result -> {
                if (!result.end) System.out.println(result.message);
                else System.out.println(result.message);
                return null;
            });
            message.join();
            System.out.println("Message: " + chat.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}