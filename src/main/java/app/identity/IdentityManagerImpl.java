package app.identity;

import app.entity.identity.Account;
import app.repository.contract.IAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service("identityManager")
@Qualifier
public class IdentityManagerImpl implements IdentityManager{
    private IAccountRepository accountRepository;

    public IdentityManagerImpl(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public IdentityResult save(Account account) {
        IdentityResult result = new IdentityResult();
        System.out.println("0");
        try{
            findAccountByEmail(account.getDetails().getEmail());
            result.addIdentityError(new IdentityError("email", account.getDetails().getEmail() + " est déjà utilisée"));

            return result;
        }catch (NoSuchElementException ignore){}
        try{
            findAccountByPhone(account.getDetails().getPhone());
            result.addIdentityError(new IdentityError("phone", account.getDetails().getPhone() + " est déjà utilisée"));
            return result;
        }catch (NoSuchElementException ignore){}

        accountRepository.save(account);
        return result;
    }

    @Override
    public Account findAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account findAccountByPhone(String phone) {
        return accountRepository.findByPhone(phone);
    }

    @Override
    public IdentityResult DeleteAccount(Account account) {
        IdentityResult identityResult = new IdentityResult();
        accountRepository.delete(account);
        return identityResult;
    }

    @Override
    public IdentityResult updateAccount(Account account) {
        IdentityResult identityResult = new IdentityResult();
        accountRepository.update(account);
        return identityResult;
    }
}
