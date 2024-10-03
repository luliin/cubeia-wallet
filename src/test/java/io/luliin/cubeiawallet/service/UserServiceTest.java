package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.EmailAlreadyInUseException;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.UserRepository;
import io.luliin.cubeiawallet.request.CreateUserRequest;
import io.luliin.cubeiawallet.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserService userService;


    @Test
    void createUserSuccess() {
        String email = "test@example.com";
        CreateUserRequest request = new CreateUserRequest(email);
        User user = new User(email);

        doNothing().when(validationService).validateEmailIsAvailable(email);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = userService.createUser(request);

        assertNotNull(userDTO);
        assertEquals(email, userDTO.email());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUserEmailAlreadyInUse() {
        String email = "test@example.com";
        CreateUserRequest request = new CreateUserRequest(email);

        doThrow(new EmailAlreadyInUseException("Email is already in use"))
                .when(validationService).validateEmailIsAvailable(email);

        assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(request));
    }
}
