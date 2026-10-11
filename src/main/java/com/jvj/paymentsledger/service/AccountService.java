package com.jvj.paymentsledger.service;

import com.jvj.paymentsledger.dto.AccountResponse;
import com.jvj.paymentsledger.exception.AccountNotFoundException;
import com.jvj.paymentsledger.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(UUID id) {
        return accountRepository.findById(id)
                .map(AccountResponse::from)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}
