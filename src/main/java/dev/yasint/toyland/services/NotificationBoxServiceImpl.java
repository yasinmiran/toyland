package dev.yasint.toyland.services;

import dev.yasint.toyland.models.NotificationBox;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.NotificationBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationBoxServiceImpl implements NotificationBoxService{

    private final NotificationBoxRepository notificationBoxRepository;

    private final UserService userService;

    @Override
    public NotificationBox createNewNotification(Long userId, String message) {
        User user = userService.getUserReferenceById(userId);
        NotificationBox notification = new NotificationBox();
        notification.setUser(user);
        notification.setMessage(message);
        return notificationBoxRepository.save(notification);
    }
}
