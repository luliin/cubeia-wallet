package io.luliin.cubeiawallet.service;

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
    private final ValidationService validationService;

    public UserService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    public UserDTO createUser(CreateUserRequest createUserRequest) {
        validationService.validateEmailIsAvailable(createUserRequest.email());

        User user = new User(createUserRequest.email());
        return userRepository.save(user).toDTO();
    }
}
