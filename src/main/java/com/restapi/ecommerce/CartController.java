package com.restapi.ecommerce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
@RestController
@RequestMapping("/api/users")
public class CartController {

    private final List<CartItem> cartItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    // Get all cartItems
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItems;
    }
    // Get CartItem by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getUserById(@PathVariable Long id) {
        Optional<CartItem> cartItem = cartItems.stream().filter(u -> u.getId().equals(id)).findFirst();
        return cartItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Create new CartItem
    @PostMapping
    public ResponseEntity<CartItem> createUser(@RequestBody CartItem cartItem) {
        cartItem.setId(counter.incrementAndGet());
        cartItems.add(cartItem);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<CartItem> userOptional = cartItems.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (userOptional.isPresent()) {
            cartItems.remove(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

