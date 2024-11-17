package com.ms.userms.service;

import com.ms.userms.entity.User;
import com.ms.userms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserResourceService {

    @Autowired
    UserRepo repo;

    public List<User> findAllUsers(){
        return repo.findAll();
    }

    public User saveUser(User user){
        return repo.save(user);
    }

    public Optional<User> findUserById(Integer id){
        return repo.findById(id);
    }
}
