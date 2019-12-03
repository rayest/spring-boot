package com.rayest.ehcache;

import com.rayest.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{id}")
    private User getById(@PathVariable("id") Integer id){
        return userService.getUserById(id);
    }

    @PutMapping("/user")
    private void update(@RequestBody User user){
        userService.updateUserById(user);
    }

    @DeleteMapping("/user/{id}")
    private void delete(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
    }
}
