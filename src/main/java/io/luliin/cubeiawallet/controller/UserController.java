package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.repository.UserRepository;
import io.luliin.cubeiawallet.request.CreateUserRequest;
import io.luliin.cubeiawallet.response.UserDTO;
import io.luliin.cubeiawallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }
}
