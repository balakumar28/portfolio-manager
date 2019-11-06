package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.objects.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionService extends JpaRepository<Transaction, Long> {
}
