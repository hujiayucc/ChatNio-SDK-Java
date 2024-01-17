package com.hujiayucc.chatnio.bean;

/**
 * 消息
 * @param role 角色 {@link Role}
 * @param content 对话内容 {@link String}
 */
public record Message(Role role, String content) {

    /**
     * 角色
     *
     * @return 角色
     */
    @Override
    public Role role() {
        return role;
    }

    /**
     * 对话内容
     *
     * @return 对话内容
     */
    @Override
    public String content() {
        return content;
    }
}