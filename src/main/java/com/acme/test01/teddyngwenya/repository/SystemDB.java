package com.acme.test01.teddyngwenya.repository;

import com.acme.test01.teddyngwenya.model.Account;
import com.acme.test01.teddyngwenya.model.CurrentAccount;
import com.acme.test01.teddyngwenya.model.SavingsAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SystemDB implements AccountRepository {
    private static SystemDB instance;
    private static final Map<Long, Account> accounts = new HashMap<>();

    private SystemDB() {
        accounts.put(1L, SavingsAccount.of(1L,"1",2_000L));
        accounts.put(2L, SavingsAccount.of(2L,"2",5_000L));
        accounts.put(3L, CurrentAccount.of(3L,"3",1_000L,10_000L));
        accounts.put(4L, CurrentAccount.of(4L,"4",-5_000L, 20_000L));
    }

    public static SystemDB getInstance() {
        if (instance == null) {
            instance = new SystemDB();
        }
        return instance;
    }


    @Override
    public Account save(Account entity) {
        return accounts.put(entity.getId(), entity);
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(accounts.get(id));
    }
}
