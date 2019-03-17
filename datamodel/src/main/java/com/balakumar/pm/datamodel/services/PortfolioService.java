package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioService extends JpaRepository<Portfolio, Long> {
}
