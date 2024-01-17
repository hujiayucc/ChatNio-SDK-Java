package com.hujiayucc.chatnio.bean;

/**
 * 查询礼包
 */
public record Package(boolean cert, boolean teenager) {

    /**
     * 实名认证即可获得 50 Nio 点数
     *
     * @return [true, false]
     */
    @Override
    public boolean cert() {
        return cert;
    }

    /**
     * 未成年（学生）可额外获得 150 Nio 点数
     *
     * @return [true, false]
     */
    @Override
    public boolean teenager() {
        return teenager;
    }
}
