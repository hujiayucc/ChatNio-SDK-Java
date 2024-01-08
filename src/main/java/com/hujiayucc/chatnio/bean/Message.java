package com.hujiayucc.chatnio.bean;

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