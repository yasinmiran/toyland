package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.CartAddDTO;
import org.springframework.http.ResponseEntity;

public interface CartController {

    ResponseEntity<?> addItemToCart(CartAddDTO body);

    ResponseEntity<?> removeItemFromCart(long id);

    ResponseEntity<?> getCart();

    ResponseEntity<?> clearCart();

}
