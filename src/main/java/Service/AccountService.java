package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.*;

public class AccountService {
    
    AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }


    public Account addAccount(Account account) {
        // will return null if entry is a blank username, or the username exists in the database, or password length inadequate

        return accountDAO.registerAccount(account);
    
    }


    public Account verifyLogin(Account account) {
        return accountDAO.verifyAccount(account);
    }
}
