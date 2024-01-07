package com.hujiayucc.chatnio.data;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hujiayucc.chatnio.exception.AuthException;
import com.hujiayucc.chatnio.exception.FieldException;
import com.hujiayucc.chatnio.utils.GetClient;
import com.hujiayucc.chatnio.utils.PostClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class Pets {
    private final String key;
    public Pets(String key) {
        this.key = key;
    }

    /**
     * 查询余额
     * @return 余额
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public float getQuota() throws AuthException, FieldException {
        GetClient quota;
        try {
            quota = new GetClient("/quota", key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (quota.statusCode() == 401) throw new AuthException("Unauthorized");
        if (quota.statusCode() == 200) {
            JSONObject quotaJson = JSON.parseObject(quota.body());
            return quotaJson.getFloat("quota");
        }
        throw new FieldException("Quota select failed.");
    }

    /**
     * 购买余额
     * @param quota 介于 1 ~ 99999 之间的整数
     * @return 购买是否成功
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public boolean buy(int quota) throws AuthException, FieldException {
        if (quota < 0 || quota > 99999) throw new FieldException("购买金额在 1-99999 之间");
        PostClient buy;
        try {
            buy = new PostClient("/buy", "quota=" + quota, key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FieldException("Buy failed.", e);
        }
        if (buy.statusCode() == 401) throw new AuthException("Unauthorized");
        if (buy.statusCode() == 200) {
            JSONObject buyJson = JSON.parseObject(buy.body());
            return buyJson.getBoolean("status");
        }
        throw new FieldException("Buy failed.");
    }

    private GetClient getPackage() {
        try {
            return new GetClient("/package", key);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 实名认证即可获得 50 Nio 点数
     * @return 查询实名认证即可获得 50 Nio 点数
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public boolean getCert() throws AuthException, FieldException {
        GetClient cert = getPackage();
        if (cert.statusCode() == 401) throw new AuthException("Unauthorized");
        if (cert.statusCode() == 200) {
            JSONObject certJson = JSON.parseObject(cert.body());
            return certJson.getJSONObject("data").getBoolean("cert");
        }
        throw new FieldException("Get cert failed.");
    }

    /**
     * 未成年（学生）可额外获得 150 Nio 点数
     * @return 未成年（学生）可额外获得 150 Nio 点数
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public boolean getTeenager() throws AuthException, FieldException {
        GetClient teenager = getPackage();
        if (teenager.statusCode() == 401) throw new AuthException("Unauthorized");
        if (teenager.statusCode() == 200) {
            JSONObject certJson = JSON.parseObject(teenager.body());
            return certJson.getJSONObject("data").getBoolean("teenager");
        }
        throw new FieldException("Get cert failed.");
    }
}
