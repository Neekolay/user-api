package org.neekolay.userapi.service;

import org.neekolay.userapi.controller.dto.UserDTO;
import org.neekolay.userapi.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 27/02/2019.
 */
public interface UserService {

    void createUser(UserDTO userDTO);

    UserDTO getUser(String email);

    void deleteUser(String email);

    ArrayList<UserDTO> getUsers();
}
