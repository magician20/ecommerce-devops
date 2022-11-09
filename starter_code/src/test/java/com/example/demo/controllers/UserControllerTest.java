package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

    }

    @Test
    public void create_user() throws Exception {
        when(encoder.encode("Helloworld")).thenReturn("thisishashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Hello");
        r.setPassword("Helloworld");
        r.setConfirmPassword("Helloworld");

         final ResponseEntity<User> responseEntity = userController.createUser(r);
         assertNotNull(responseEntity);
         assertEquals(200, responseEntity.getStatusCodeValue());
         User u = responseEntity.getBody();
         assertNotNull(u);
         assertEquals(0, u.getId());
         assertEquals("Hello", u.getUsername());
         assertEquals("thisishashed", u.getPassword());
    }

    
    @Test
    public void testInvalidPassword () {
        when(encoder.encode("Helloworld")).thenReturn("thisishashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Hello");
        createUserRequest.setPassword("Helloworld");

        ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }
    @Test
    public void testFindUserByUsername () {
        User mockUser = new User();
        mockUser.setUsername("Hello");
        when(userRepository.findByUsername("Hello")).thenReturn(mockUser);

        ResponseEntity<User> response = userController.findByUserName("Hello");
        System.out.println(response.getBody() + "user");
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
        assertEquals(user.getUsername(), "Hello");
    }

    @Test
    public void testFindUserbyUsernameWithNonExistenceUser () {
        User mockUser = new User();
        mockUser.setUsername("Hello");
        when(userRepository.findByUsername("Hello")).thenReturn(mockUser);

        ResponseEntity<User> response = userController.findByUserName("");
        assertEquals(response.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenCreationRequestHasInvalidPassword () {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Hello");
        createUserRequest.setPassword("Hello");

        ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }


}
