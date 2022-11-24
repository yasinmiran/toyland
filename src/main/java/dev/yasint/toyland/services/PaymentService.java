package dev.yasint.toyland.services;

import dev.yasint.toyland.models.user.Payment;
import dev.yasint.toyland.models.user.User;

public interface PaymentService {

    Payment updatePayment(User user, Payment partial);

}
