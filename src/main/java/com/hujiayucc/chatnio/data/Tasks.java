package com.hujiayucc.chatnio.data;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hujiayucc.chatnio.bean.Message;
import com.hujiayucc.chatnio.bean.TaskBean;
import com.hujiayucc.chatnio.exception.AuthException;
import com.hujiayucc.chatnio.exception.FieldException;
import com.hujiayucc.chatnio.utils.GetClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.hujiayucc.chatnio.bean.Role.getRole;

public class Tasks {
    private final String key;

    /** 对话 */
    public Tasks(String key) {
        this.key = key;
    }

    private List<Message> getMessage(JSONArray jsonArray) {
        List<Message> list = new ArrayList<>();
        if (jsonArray == null) return list;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            list.add(new Message(
                    getRole(json.getString("role")),
                    json.getString("content")
            ));
        }
        return list;
    }

    /**
     * 获取对话列表
     * @return list
     * @throws AuthException 验证失败
     * @throws FieldException 字段异常
     */
    public List<TaskBean> getTaskList() throws AuthException, FieldException {
        GetClient client;
        try {
            client = new GetClient("/conversation/list", key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            JSONObject object = JSON.parseObject(client.body());
            if (!object.getBoolean("status")) throw new FieldException(object.getString("message"));
            List<TaskBean> list = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("data");
            if (jsonArray == null) return list;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                list.add(new TaskBean(
                        json.getInteger("id"),
                        json.getInteger("user_id"),
                        json.getString("name"),
                        getMessage(json.getJSONArray("message")),
                        json.getString("model"),
                        json.getBoolean("enable_web")
                ));
            }
            return list;
        }
        throw new FieldException("Get TaskList failed");
    }

    /**
     * 获取单个对话
     * @param id 对话id
     * @return 对话内容
     * @throws AuthException 验证失败
     * @throws FieldException 字段异常
     */
    public TaskBean getTask(int id) throws AuthException, FieldException {
        GetClient client;
        try {
            client = new GetClient("/conversation/load/?id=" + id, key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            JSONObject object = JSON.parseObject(client.body());
            if (!object.getBoolean("status")) throw new AuthException(object.getString("message"));
            JSONObject json = object.getJSONObject("data");
            return new TaskBean(
                    json.getInteger("id"),
                    json.getInteger("user_id"),
                    json.getString("name"),
                    getMessage(json.getJSONArray("message")),
                    json.getString("model"),
                    json.getBoolean("enable_web")
            );
        }
        throw new FieldException("Get Task failed");
    }

    /**
     * 删除对话
     * @param id 对话id
     * @return 删除是否成功
     * @throws AuthException 验证失败
     * @throws FieldException 字段异常
     */
    public boolean deleteTask(int id) throws AuthException, FieldException {
        GetClient client;
        try {
            client = new GetClient("/conversation/delete/?id=" + id, key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            JSONObject object = JSON.parseObject(client.body());
            if (!object.getBoolean("status")) throw new FieldException(object.getString("message"));
            return true;
        }
        throw new FieldException("Delete Task failed");
    }
}
