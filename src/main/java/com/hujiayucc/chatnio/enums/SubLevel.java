package com.hujiayucc.chatnio.enums;

/**
 * 订阅等级
 */
public enum SubLevel {
    /** 普通用户 */
    Normal(0),
    /** 基础版 */
    Basic(1),
    /** 标准版 */
    Standard(2),
    /** 专业版 */
    Professional(3);

    private final int level;

    /**
     * 订阅等级
     * @param level level
     */
    SubLevel(int level) {
        this.level = level;
    }

    /**
     * 订阅等级
     * @return 订阅等级
     */
    public int getLevel() {
        return level;
    }
}