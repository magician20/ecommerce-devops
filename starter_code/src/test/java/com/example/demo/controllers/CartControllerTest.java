package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

public class CartControllerTest {
    
    private static final String USERNAME = "Hello";
    private static final long ITEM_ID = 1L;
    private static final String PRICE = "21.45";

    private CartController cartController;
    private CartRepository cartRepository = mock(CartRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);

        when(userRepository.findByUsername(USERNAME)).thenReturn(getUser());
        when(itemRepository.findById(ITEM_ID)).thenReturn(getItem());
    }

    @Test
    public void testAddToCart () {
        int expectedQ = 2 + 1;
        BigDecimal expectedT = new BigDecimal(PRICE).multiply(BigDecimal.valueOf(expectedQ));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(USERNAME);
        modifyCartRequest.setItemId(ITEM_ID);
        modifyCartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Cart cart = response.getBody();

        assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
        assertNotNull(cart);
        assertEquals(cart.getUser().getUsername(), USERNAME);
        assertEquals(cart.getItems().size(), expectedQ);
        assertEquals(cart.getTotal(), expectedT);
    }

    @Test
    public void testAddToCartWithInvalidUsername () {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("");
        modifyCartRequest.setItemId(ITEM_ID);
        modifyCartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertEquals(response.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testAddToCartWithInvalidItem () {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(USERNAME);
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertEquals(response.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testRemoveFromCart () {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(USERNAME);
        modifyCartRequest.setItemId(ITEM_ID);
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Cart cart = response.getBody();

        assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
        assertNotNull(cart);
        assertEquals(cart.getUser().getUsername(), USERNAME);
        assertEquals(cart.getItems().size(), 0);
        assertEquals(cart.getTotal().intValue(), 0);
    }

    @Test
    public void testRemoveFromCartWithInvalidItem () {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("");
        modifyCartRequest.setItemId(ITEM_ID);
        modifyCartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertEquals(response.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testRemoveFromCartInvalidItem () {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(USERNAME);
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertEquals(response.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
    }

    private static User getUser() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setCart(getCart(user));
        return user;
    }

    private static Cart getCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.addItem(getItem().orElse(null));
        return cart;
    }

    private static Optional<Item> getItem() {
        Item item = new Item();
        item.setId(ITEM_ID);
        item.setPrice(new BigDecimal(PRICE));
        return Optional.of(item);
    }
}
