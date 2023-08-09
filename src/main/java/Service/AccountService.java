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

        if (!account.getUsername().isBlank() && accountDAO.getAccountByUsername(account.getUsername()) == null && account.getPassword().length() >= 4) {
            return accountDAO.registerAccount(account);
        } 

        // will return null if entry is a blank username, or the username exists in the database

        return null;
    
    }
}
