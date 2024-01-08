package com.hujiayucc.chatnio.bean;

public enum Role {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String role;
    Role(String role) {
        this.role = role;
    }

    /**
     * 角色名称
     * @return 角色名称
     */
    public String getRole() {
        return role;
    }

    public static Role getRole(String role) {
        return switch (role) {
            case "system" -> SYSTEM;
            case "user" -> USER;
            case "assistant" -> ASSISTANT;
            default -> null;
        };
    }
}