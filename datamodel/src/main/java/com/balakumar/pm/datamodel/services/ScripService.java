package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.Scrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScripService extends JpaRepository<Scrip, Long> {
}
