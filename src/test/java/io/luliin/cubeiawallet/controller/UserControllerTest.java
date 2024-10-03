package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.request.CreateUserRequest;
import io.luliin.cubeiawallet.response.UserDTO;
import io.luliin.cubeiawallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @Test
    void createUserSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest("test@example.com");
        UserDTO userDTO = new UserDTO(1L, "test@example.com");
        when(userService.createUser(createUserRequest)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(createUserRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).createUser(createUserRequest);
    }
}