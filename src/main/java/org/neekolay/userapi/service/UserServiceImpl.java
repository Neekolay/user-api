package org.neekolay.userapi.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.neekolay.userapi.controller.dto.UserDTO;
import org.neekolay.userapi.exception.IncorrectRequestParameterException;
import org.neekolay.userapi.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.neekolay.userapi.model.User;
import org.neekolay.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by user on 27/02/2019.
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-M-yyyy");
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserDTO userDTO) {

        if (userDTO.getName().equals("") || userDTO.getLastName().equals("") ||
                userDTO.getEmail().equals("") || userDTO.getBirthday().equals("") ||
                userDTO.getPassword().equals("")) {
            throw new IncorrectRequestParameterException("Empty parameter");
        }

        LocalDate birthdayDate;
        try {
            birthdayDate = LocalDate.parse(userDTO.getBirthday(), dateTimeFormatter);
        } catch (RuntimeException e) {
            throw new IncorrectRequestParameterException(userDTO.getBirthday());
        }

        if (!EmailValidator.getInstance().isValid(userDTO.getEmail())) {
            throw new IncorrectRequestParameterException(userDTO.getEmail());
        }

        if (repository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("User with e-mail " + userDTO.getEmail() + " already exists");
        } else {
            User user;
            user = new User();
            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setBirthday(birthdayDate);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            LOGGER.info("creating account with e-mail {}", user.getEmail());
            repository.save(user);
        }
    }

    @Override
    @Transactional
    public UserDTO getUser(String email) {
        if (repository.findByEmail(email) == null) {
            throw new UserNotFoundException(email);
        } else {
            LOGGER.info("getting user with e-mail {}", email);
            return new UserDTO(repository.findByEmail(email));
        }
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if (repository.findByEmail(email) == null) {
            throw new UserNotFoundException(email);
        } else {
            LOGGER.info("deleting user with e-mail {}", email);
            repository.delete(repository.findByEmail(email));
        }
    }

    @Override
    @Transactional
    public ArrayList<UserDTO> getUsers() {
        Iterable<User> users = repository.findAll();
        ArrayList<UserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(new UserDTO(user)));
        return usersDTO;
    }
}