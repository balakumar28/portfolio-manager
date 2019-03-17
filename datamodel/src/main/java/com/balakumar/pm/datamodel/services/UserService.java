package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
