package app.identity;

import app.entity.identity.Account;

import java.util.NoSuchElementException;

public interface IdentityManager {
   IdentityResult save(Account account);
   Account findAccountById(Integer id) throws NoSuchElementException;
   Account findAccountByEmail(String email) throws NoSuchElementException;
   Account findAccountByPhone(String phone) throws NoSuchElementException;
   IdentityResult DeleteAccount(Account account);
   IdentityResult updateAccount(Account account);

}
