package com.a10.mejabelajar.course.model;

public enum NotificationType {
    CREATE("created"),
    UPDATE("updated");

    private String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
