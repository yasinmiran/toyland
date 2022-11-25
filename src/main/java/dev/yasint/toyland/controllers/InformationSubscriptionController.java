package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.SubscriptionDTO;
import org.springframework.http.ResponseEntity;

public interface InformationSubscriptionController {

    ResponseEntity<?> addSubscriber(SubscriptionDTO subscription);

    ResponseEntity<?> removeSubscriber(SubscriptionDTO subscription);
}
