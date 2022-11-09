// package com.example.demo.controllers;

// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;

// import com.example.demo.TestUtils;
// import com.example.demo.model.persistence.User;
// import com.example.demo.model.persistence.repositories.CartRepository;
// import com.example.demo.model.persistence.repositories.UserRepository;
// import com.example.demo.model.requests.CreateUserRequest;
// import com.example.demo.model.requests.LoginRequest;
// import com.fasterxml.jackson.databind.ObjectMapper;


//For LOGIN TEST

// @RunWith(SpringRunner.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// @AutoConfigureJsonTesters
// public class LoginTest {
//     @Autowired
//     private MockMvc mockMvc;

//     private UserController userController;
//     private UserRepository userRepository = mock(UserRepository.class);
//     private CartRepository cartRepository = mock(CartRepository.class);
//     private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

//     // private JWTAuthenticationFilter jwtAuthenticationFilter;
//     // private AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

//     @Before
//     public void setUp() {
//         userController = new UserController();
//         TestUtils.injectObjects(userController, "userRepository", userRepository);
//         TestUtils.injectObjects(userController, "cartRepository", cartRepository);
//         TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
//         // jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager);

//     }

//     @Test
//     public void testLoginWithValidUsers() throws Exception {
//         ObjectMapper objectMapper = new ObjectMapper();
//         CreateUserRequest r = new CreateUserRequest();
//         r.setUsername("Hello");
//         r.setPassword("Helloworld");
//         r.setConfirmPassword("Helloworld");
        
//         this.mockMvc.perform(post("/api/user/create")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(r)))
//                 .andExpect(status().is2xxSuccessful());

//         LoginRequest request = new LoginRequest();
//         request.setUsername("Hello");
//         request.setPassword("Helloworld");
//         this.mockMvc.perform(post("/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(request)))
//                 .andExpect(status().isOk());
//     }

//     @Test
//     public void testLoginWithInValidUsers() throws Exception {
//         ObjectMapper objectMapper = new ObjectMapper();
//         User user = new User("Hello", "Helloworld");
//         this.mockMvc.perform(post("/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(user)))
//                 .andExpect(status().isUnauthorized());
//     }
// }
