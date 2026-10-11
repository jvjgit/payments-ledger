package com.jvj.paymentsledger.repository;

import com.jvj.paymentsledger.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
