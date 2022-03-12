package com.balakumar.pm.datamodel.rest;

import com.balakumar.pm.datamodel.exeptions.DataModelException;
import com.balakumar.pm.datamodel.objects.User;
import com.balakumar.pm.datamodel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.balakumar.pm.datamodel.Constants.REST_SAVE;
import static com.balakumar.pm.datamodel.Constants.REST_USER;

@RestController
@RequestMapping(REST_USER)
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws DataModelException {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            throw new DataModelException("User not found for id=" + id);
        }
        return user.get();
    }

    @GetMapping("/")
    public User findByMail(@RequestParam String email) throws DataModelException {
        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            throw new DataModelException("User not found for email=" + email);
        }
        return user.get();
    }

    @PostMapping("/" + REST_SAVE)
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @DeleteMapping("/")
    public void delete(@RequestParam String email) {
        userService.deleteByEmail(email);
    }
}
