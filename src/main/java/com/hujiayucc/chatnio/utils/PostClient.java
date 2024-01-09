package com.hujiayucc.chatnio.utils;

import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static com.hujiayucc.chatnio.ChatNio.API;

public class PostClient {
    private final HttpResponse<String> response;

    /**
     * POST请求
     * @param url 请求URL
     */
    public PostClient(String url, Map<String, Object> requestBody, String key) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(new URI(API + url))
                .header("Context-Type","application/json")
                .header("Authorization", key)
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(requestBody).toJSONString()));

        HttpRequest request = builder.build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String body() {
        return response.body();
    }

    public int statusCode() {
        return response.statusCode();
    }
}