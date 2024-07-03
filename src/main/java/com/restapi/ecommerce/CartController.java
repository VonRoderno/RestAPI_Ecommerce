package com.restapi.ecommerce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
@RestController
@RequestMapping("/cart")
public class CartController {

    private final List<CartItem> cartItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    // Get all cartItems
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItems;
    }

    // Create new CartItem
    @PostMapping
    public ResponseEntity<CartItem> createUser(@RequestBody CartItem cartItem) {
        cartItem.setId(counter.incrementAndGet());
        cartItems.add(cartItem);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    // Delete cartItems
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<CartItem> itemOptional = cartItems.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (itemOptional.isPresent()) {
            cartItems.remove(itemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

