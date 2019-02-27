package org.neekolay.userapi.controller;

import org.neekolay.userapi.controller.dto.UserDTO;
import org.neekolay.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by user on 27/02/2019.
 */

@RestController
@RequestMapping("service")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("createUser")
    public ResponseEntity createAccount(@RequestParam String name,
                                        @RequestParam String lastName,
                                        @RequestParam String email,
                                        @RequestParam String birthday,
                                        @RequestParam String password) {
        UserDTO userDTO = new UserDTO(name, lastName, email, birthday, password);
        service.createUser(userDTO);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("users")
    public ArrayList<UserDTO> getUsers() {
        return service.getUsers();
    }

    @GetMapping("getUser/{email}")
    public UserDTO getUser(@PathVariable String email) {
        return service.getUser(email);
    }

    @PutMapping("deleteUser/{email}")
    public ResponseEntity deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.ok("OK");
    }
}
