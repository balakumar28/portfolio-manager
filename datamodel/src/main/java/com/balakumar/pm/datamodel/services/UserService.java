package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserService extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
