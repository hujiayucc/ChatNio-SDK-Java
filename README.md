# ChatNio SDK 使用文档

## 一、简介
为 [ChatNio](https://chatnio.net/) 提供的Java平台SDK

## 二、快速开始
在开始使用 SDK 之前，你需要首先生成一个`ChatNio`实例，这需要提供一个由`ChatNio`服务提供的密钥：

```java
String key = "你的密钥";
ChatNio chatNio = new ChatNio(key);
```

## 三、余额
[相关API 文档](https://docs.chatnio.net/kai-fa-zhe-zi-yuan/api-reference/pets]

```java
float quota = chatNio.Pets().getQuota();
boolean cert = chatNio.Pets().getCert();
boolean teenager = chatNio.Pets().getTeenager();
```

通过调用以下方法购买余额：

```java
boolean buy = chatNio.Pets().buy(200);
```

## 四、对话
SDK 提供了一个便捷的对话查询方法：

```java
int taskListSize = chatNio.Tasks().getTaskList().size();
```

这将返回一个`TaskBean`对象的列表，你可以通过这些`TaskBean`对象获取例如任务的`id`、`userId`、`name`、`model`、`enableWeb`等信息，并且获取任务的`Message`消息列表。

## 五、订阅和礼包
你可以使用SDK提供的方法查询订阅状态，购买订阅和续费等操作

```java
boolean isSubscribe = chatNio.Subscribe().isSubscribed();
boolean subscribe = chatNio.Subscribe().subscribe(1, SubLevel.Standard);
```

## 六、模型
SDK 提供了批量获取所有模型的方法：

```java
Models models = chatNio.Models();
for (String name : models.getAll()) {
    System.out.println(name);
}
```

## 七、聊天
使用 SDK，你可以选择同步方式或异步方式进行消息发送。其中，`Token`对象用于指定用户或密钥和对话id，也可以设置为匿名或新对话。

### 1. 同步方式

```java
ChatSync chat = new ChatSync(token);
CompletableFuture<MessageSegment> message = chat.send("你的消息内容", Models.getDefault(), false);
```
### 2. 异步方式

```java
ChatAsync chatAsync = new ChatAsync(token) {
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        return super.onText(webSocket, data, last);
    }
};
chatAsync.send("你的消息", Models.getDefault(), false).join();
```
在异步方式下，你可以重写 `onText`方法以自定义处理返回的消息内容。

## 八、错误处理
调用SDK的方法可能会抛出`AuthException`，`FieldException` 或 `BuyException`等异常，你可以通过捕获并处理这些异常来优化你的程序：

```java
catch (AuthException | FieldException | BuyException e) {
    System.out.println(e.getMessage());
}
```

## 九、调用实例
```java
package com.hujiayucc.chatnio;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.bean.*;
import com.hujiayucc.chatnio.data.ChatAsync;
import com.hujiayucc.chatnio.data.ChatSync;
import com.hujiayucc.chatnio.enums.SubLevel;
import com.hujiayucc.chatnio.exception.AuthException;
import com.hujiayucc.chatnio.exception.BuyException;
import com.hujiayucc.chatnio.exception.FieldException;

import java.net.http.WebSocket;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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

        Token token = new Token(Token.Anonymous, Token.NewTaskId);
        // Token token = new Token(key, Token.NewTaskId);

        // 同步
        try {
            ChatSync chat = new ChatSync(token);
            CompletableFuture<MessageSegment> message = chat.send("SpringBoot如何使用切片，请给出相关文档以及注释",
                    Models.getDefault(), false);
            message.join();
            System.out.println("Message: " + chat.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 异步
        try {
            ChatAsync chatAsync = new ChatAsync(token) {
                @Override
                public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                    MessageSegment message = JSONObject.parseObject(data.toString(), MessageSegment.class);
                    if (!message.isEnd()) {
                        System.out.print(message.getMessage());
                    } else {
                        System.out.println(message.getMessage());
                    }
                    return super.onText(webSocket, data, last);
                }
            };
            chatAsync.send("Android Handler Looper 工作原理", Models.getDefault(), false).join();
            System.out.println("Message: " + chatAsync.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```