package dev.yasint.toyland.services;

import dev.yasint.toyland.models.NotificationBox;

public interface NotificationBoxService {

    NotificationBox createNewNotification(Long userId, String message);
}
