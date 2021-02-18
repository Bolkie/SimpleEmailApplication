package com.company.dao;

import com.company.model.Account;

import java.util.List;

public interface AccountDao {
    Account getAccount(int id);

    List<Account> getAllAccounts();

    void saveAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(int id);
}