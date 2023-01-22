package com.acme.test01.teddyngwenya.repository;

import com.acme.test01.teddyngwenya.model.Account;

import java.util.Optional;

//Improvement: AccountRepository to extends Spring Data CrudRepository to generate the implementation
// of this interface
public interface AccountRepository {
    Account save(Account entity);
    Optional<Account> findById(long id);
}

