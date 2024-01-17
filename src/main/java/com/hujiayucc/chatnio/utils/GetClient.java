package com.hujiayucc.chatnio.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.hujiayucc.chatnio.ChatNio.API;

/**
 * GET请求
 */
public class GetClient {
    private final HttpResponse<String> response;

    /**
     * GET请求
     * @param url 请求url
     * @param key key
     * @throws URISyntaxException {@link URISyntaxException}
     * @throws IOException {@link IOException}
     * @throws InterruptedException {@link InterruptedException}
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

    /**
     * GET请求返回内容
     * @return GET数据
     */
    public String body() {
        return response.body();
    }

    /**
     * 请求状态码
     * @return 状态码
     */
    public int statusCode() {
        return response.statusCode();
    }
}