package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.SubscriptionDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.services.InformationSubscriptionService;
import dev.yasint.toyland.services.UserService;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
public class InformationSubscriptionControllerImpl implements InformationSubscriptionController {

    private final InformationSubscriptionService informationSubscriptionService;

    private final UserService userService;

    @Override
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<?> addSubscriber(@Valid @RequestBody SubscriptionDTO subscription) {
        User observerUser = Common.getUserDetailsFromContext().getUser();
        User subjectUser = userService.getUserReferenceById(subscription.getSubjectId());
        informationSubscriptionService.addSubscription(subjectUser, observerUser, subscription.getEvent());
        return ResponseEntity.ok()
                .body(new MessageResDTO("Subscription successfully added."));
    }

    @Override
    @DeleteMapping("/remove")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<?> removeSubscriber(@Valid @RequestBody SubscriptionDTO subscription) {
        User observerUser = Common.getUserDetailsFromContext().getUser();
        User subjectUser = userService.getUserReferenceById(subscription.getSubjectId());
        System.out.println(observerUser);
        System.out.println(subjectUser);
        informationSubscriptionService.removeSubscription(subjectUser, observerUser, subscription.getEvent());
        return ResponseEntity.ok()
                .body(new MessageResDTO("Subscription successfully removed."));
    }
}
