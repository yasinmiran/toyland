package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.CartAddDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    @Override
    @PostMapping("/item")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> addItemToCart(@RequestBody CartAddDTO body) {
        try {
            Cart cart = cartService.addItemToCart(
                    body.getProductId(),
                    body.getQuantity()
            );
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnableToSatisfyException e) {
            return ResponseEntity.internalServerError().body(
                    new MessageResDTO(e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new MessageResDTO(e.getMessage())
            );
        }
    }

    @Override
    @DeleteMapping("/item/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("id") long id) {
        try {
            Cart cart = cartService.removeItemFromCart(id);
            return ResponseEntity.ok(cart);
        } catch (UnableToSatisfyException e) {
            return ResponseEntity.internalServerError().body(
                    new MessageResDTO(e.getMessage())
            );
        }
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> getCart() {
        try {
            Cart cart = cartService.getCart();
            return ResponseEntity.ok(cart);
        } catch (UnableToSatisfyException e) {
            return ResponseEntity.internalServerError().body(
                    new MessageResDTO(e.getMessage())
            );
        }
    }

    @Override
    @DeleteMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> clearCart() {
        try {
            Cart cart = cartService.emptyCart();
            return ResponseEntity.ok(cart);
        } catch (UnableToSatisfyException e) {
            return ResponseEntity.internalServerError().body(
                    new MessageResDTO(e.getMessage())
            );
        }
    }

}
