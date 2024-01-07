package com.hujiayucc.chatnio.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.hujiayucc.chatnio.ChatNio.API;

public class PostClient {
    private final HttpResponse<String> response;

    /**
     * POST请求
     * @param url 请求URL
     */
    public PostClient(String url,String requestBody, String key) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(new URI(API + url))
                .header("Context-Type","application/x-www-form-urlencoded")
                .header("Authorization", key)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody));

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