package com.hujiayucc.chatnio.enums;

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
    SubLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}