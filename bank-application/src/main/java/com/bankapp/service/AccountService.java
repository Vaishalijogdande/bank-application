package com.bankapp.service;

import com.bankapp.dto.AccountDto;
import com.bankapp.entity.Account;
import com.bankapp.mapper.AccountMapper;
import com.bankapp.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

public AccountDto addAccount(AccountDto accountDto){
    Account account = AccountMapper.mapToAccount(accountDto);
    Account savedAccount = accountRepository.save(account);
    return AccountMapper.mapToAccountDto(savedAccount);
}

public AccountDto getAccountById(Long id){
    Account account = accountRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Account does not exists") );
    return AccountMapper.mapToAccountDto(account);
}

public AccountDto deposit(Long id, double amount){
    Account account = accountRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Account does not exists") );

    double total = account.getBalance() + amount;
    account.setBalance(total);
    Account savedAccount = accountRepository.save(account);
    return AccountMapper.mapToAccountDto(savedAccount);

}

public AccountDto withdraw(Long id, double amount){
    Account account = accountRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Account does not exists") );

    double total = account.getBalance() - amount;
    account.setBalance(total);
    Account savedAccount = accountRepository.save(account);
    return AccountMapper.mapToAccountDto(savedAccount);

}

public List<AccountDto> getAllAccounts(){
    List<Account> accounts= accountRepository.findAll();
    return accounts.stream().map( (account) ->
            AccountMapper.mapToAccountDto(account) )
            .collect(Collectors.toList());


}

public void deleteAccount(Long id){

    Account account = accountRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Account does not exists") );

    accountRepository.deleteById(id);
}


}
