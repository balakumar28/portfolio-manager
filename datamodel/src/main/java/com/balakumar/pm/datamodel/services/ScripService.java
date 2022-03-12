package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.Scrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScripService extends JpaRepository<Scrip, Long> {

    Optional<Scrip> findByCode(String code);

    void deleteByCode(String code);
}
