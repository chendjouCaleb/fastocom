package app.repository.contract;

import app.entity.identity.Account;

public interface IAccountRepository extends IRepository<Account, Integer>{
    Account findByEmail(String email);
    Account findByPhone(String phone);

    Account findByContactEmailOrContactPhone(String ql);

}
