package com.hujiayucc.chatnio.bean;

import com.hujiayucc.chatnio.utils.GetClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class Models {
    private final String[] MODELS;

    /** AI模型 */
    public Models() {
        String input;
        try {
            input = new GetClient("/v1/models", "").body().replace("\"","");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        MODELS = input.substring(1, input.length() - 1).split("\\s*,\\s*");
    }

    /**
     * 获取默认模型
     * @return 默认模型
     */
    public static String getDefault() {
        return "gpt-3.5-turbo-0613";
    }

    /**
     * 获取模型总数
     * @return 模型总数
     */
    public int getSize() {
        return MODELS.length;
    }

    /**
     * 获取所有模型
     * @return 所有模型
     */
    public String[] getAll() {
        return MODELS;
    }

    /**
     * 获取指定模型
     * @param index 模型序号
     * @return 模型
     */
    public String getModel(int index) {
        return MODELS[index];
    }

    private String[] getModels(String name) {
        String[] models = new String[MODELS.length];
        for (int i = 0; i < MODELS.length; i++) {
            if (MODELS[i].contains(name)) {
                models[i] = MODELS[i];
            }
        }
        return models;
    }

    /**
     * 获取全部GPT模型
     * @return GPT模型
     */
    public String[] GPT() {
        return getModels("gpt");
    }

    /**
     * 获取全部Claude模型
     * @return Claude模型
     */
    public String[] Claude() {
        return getModels("claude");
    }

    /**
     * 获取全部Azure模型
     * @return Azure模型
     */
    public String[] Azure() {
        return getModels("azure");
    }

    /**
     * 获取全部Bing模型
     * @return Bing模型
     */
    public String[] Bing() {
        return getModels("bing");
    }

    /**
     * 获取全部ZhiPu模型
     * @return ZhiPu模型
     */
    public String[] ZhiPu() {
        return getModels("zhipu");
    }

    /**
     * 获取全部Skylark模型
     * @return Skylark模型
     */
    public String[] Skylark() {
        return getModels("skylark");
    }

    /**
     * 获取全部Spark模型
     * @return Spark模型
     */
    public String[] Spark() {
        return getModels("spark");
    }

    /**
     * 获取全部Gemini模型
     * @return Gemini模型
     */
    public String[] Gemini() {
        return getModels("gemini");
    }
}