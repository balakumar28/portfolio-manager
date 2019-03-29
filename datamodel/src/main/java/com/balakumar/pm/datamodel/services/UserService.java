package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserService extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
