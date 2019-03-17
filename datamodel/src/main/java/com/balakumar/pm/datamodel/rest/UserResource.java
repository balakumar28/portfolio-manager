package com.balakumar.pm.datamodel.rest;

import com.balakumar.pm.datamodel.exeptions.DataModelException;
import com.balakumar.pm.datamodel.objects.User;
import com.balakumar.pm.datamodel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) throws DataModelException {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            throw new DataModelException("User not found for id=" + id);
        }
        return user.get();
    }

    @GetMapping("/user")
    public ResponseEntity<Object> create(@RequestBody User user) {
        User createdUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(Response.SUCCESS);
    }

    private static class Response {
        private int code;
        private String message;
        private static final Response SUCCESS = new Response(0, "SUCCESS");
        private static final Response FAILED = new Response(1, "FAILED");

        private Response(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
