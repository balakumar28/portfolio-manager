package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.ScripHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScripHistoryService extends JpaRepository<ScripHistory, Long> {
}
