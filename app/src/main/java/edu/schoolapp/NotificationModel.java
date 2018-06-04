package edu.schoolapp;

public class NotificationModel {
    private String content;
    private String notificationTime;

    public NotificationModel(String content, String notificationTime) {
        this.content = content;
        this.notificationTime = notificationTime;
    }

    public String getContent() {
        return content;
    }

    public String getNotificationTime() {
        return notificationTime;
    }
}
