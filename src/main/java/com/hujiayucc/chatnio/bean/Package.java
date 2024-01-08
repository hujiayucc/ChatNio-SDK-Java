package com.hujiayucc.chatnio.bean;


public class Package {
    private final boolean cert;
    private final boolean teenager;

    public Package(boolean cert, boolean teenager) {
        this.cert = cert;
        this.teenager = teenager;
    }

    /**
     * 实名认证即可获得 50 Nio 点数
     * @return [true, false]
     */
    public boolean isCert() {
        return cert;
    }

    /**
     * 未成年（学生）可额外获得 150 Nio 点数
     * @return [true, false]
     */
    public boolean isTeenager() {
        return teenager;
    }
}
