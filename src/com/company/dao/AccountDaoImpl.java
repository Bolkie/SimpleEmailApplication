package com.company.dao;

import com.company.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{
    private final List<Account> accounts;

    public AccountDaoImpl() {
        accounts = new ArrayList<>();
    }

    @Override
    public Account getAccount(int id) {
        Account account = null;

        for (Account a : accounts) {
            if (a.getId() == id) {
                account = a;
                break;
            }
        }

        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public void saveAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public void updateAccount(Account account) {
        for (Account a : accounts) {
            if (a.getId() == account.getId()) {
                a.setPassword(account.getPassword());
                a.setOwnerFirstName(account.getOwnerFirstName());
                a.setOwnerLastName(account.getOwnerLastName());
                a.setOwnerSex(account.getOwnerSex());
                a.setOwnerBirthday(account.getOwnerBirthday());
                a.setSecurityQuestion(account.getSecurityQuestion());
                a.setSecurityQuestionAnswer(account.getSecurityQuestionAnswer());
                a.setPassword(account.getPassword());
            }
        }
    }

    @Override
    public void deleteAccount(int id) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == id) {
                accounts.remove(i);
                break;
            }
        }
    }
}