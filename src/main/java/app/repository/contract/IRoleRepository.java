package app.repository.contract;

import app.entity.identity.Account;
import app.entity.identity.Role;

import java.util.List;

public interface IRoleRepository extends IRepository<Role, Integer> {
    Role findByName(String name);
    List<Account> listAccountByRole(String name);
}
