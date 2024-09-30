package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.EmailAlreadyInUseException;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.UserRepository;
import io.luliin.cubeiawallet.request.CreateUserRequest;
import io.luliin.cubeiawallet.response.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(CreateUserRequest createUserRequest) {
        validateEmailIsAvailable(createUserRequest.email());

        User user = new User(createUserRequest.email());
        return userRepository.save(user).toDTO();
    }

    private void validateEmailIsAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("Email is already in use");
        }
    }
}
