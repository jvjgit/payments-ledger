package com.jvj.paymentsledger.service;

import com.jvj.paymentsledger.dto.TransferRequest;
import com.jvj.paymentsledger.dto.TransferResponse;
import com.jvj.paymentsledger.entity.Account;
import com.jvj.paymentsledger.entity.Transaction;
import com.jvj.paymentsledger.entity.TransactionStatus;
import com.jvj.paymentsledger.exception.AccountNotFoundException;
import com.jvj.paymentsledger.exception.InsufficientFundsException;
import com.jvj.paymentsledger.exception.InvalidTransferException;
import com.jvj.paymentsledger.repository.AccountRepository;
import com.jvj.paymentsledger.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        if (request.fromAccountId().equals(request.toAccountId())) {
            throw new InvalidTransferException("Cannot transfer to the same account");
        }

        Account from = findAccount(request.fromAccountId());
        Account to = findAccount(request.toAccountId());
        BigDecimal amount = request.amount();

        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(from.getId());
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        Transaction transaction = transactionRepository.save(
                new Transaction(from, to, amount, TransactionStatus.COMPLETED));

        return TransferResponse.from(transaction);
    }

    private Account findAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}
