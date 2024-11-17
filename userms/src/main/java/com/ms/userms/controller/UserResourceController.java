package com.ms.userms.controller;

import com.ms.userms.entity.User;
import com.ms.userms.service.UserResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResourceController {

    @Autowired
    UserResourceService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResourceController.class);

    @GetMapping("/user")
    public List<User> getAllUser(){
        LOGGER.info("Getting all the users");
        return service.findAllUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user){
        LOGGER.info("Saving user to database");
        User savedUser = service.saveUser(user);
        return ResponseEntity.created(URI.create(savedUser.getId().toString())).body(user);
    }


    @GetMapping("/user/{id}") // return 404 as HTTP status code in case user is not present
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        LOGGER.info("Getting all the users");
        Optional<User> userFound = service.findUserById(id);

        if(userFound.isPresent()){
            LOGGER.info("User found from id {} from database", id);
            return ResponseEntity.ok(userFound.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/hello")
    public String getHelloWorld(){
        return "Hello World!";
    }
}
