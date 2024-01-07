package com.hujiayucc.chatnio.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.hujiayucc.chatnio.ChatNio.API;

public class GetClient {
    private final HttpResponse<String> response;

    /**
     * GET请求
     * @param url 请求URL
     */
    public GetClient(String url, String key) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .header("Authorization", key)
                .uri(new URI(API + url))
                .GET();

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