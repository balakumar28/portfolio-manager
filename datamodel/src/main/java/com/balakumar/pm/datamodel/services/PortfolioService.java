package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.Portfolio;
import com.balakumar.pm.datamodel.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioService extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findAllByUser(User user);
}
