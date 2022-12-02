package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.ContactUpdateDTO;
import dev.yasint.toyland.dtos.request.PaymentUpdateDTO;
import dev.yasint.toyland.dtos.request.UserSignupReqDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {

    ResponseEntity<?> me();

    ResponseEntity<?> updateContact(ContactUpdateDTO body);

    ResponseEntity<?> updatePayment(PaymentUpdateDTO body);

    ResponseEntity<?> registerDriver(UserSignupReqDTO body);

}
