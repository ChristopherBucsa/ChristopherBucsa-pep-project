package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();
    
    public Account registerUser(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.length() < 4) {
            return null;
        }
        if (accountDAO.getAccountByUsername(username) != null) {
            return null; // Username already exists
        }
        return accountDAO.createAccount(new Account(0, username, password));
    }
    
    public Account loginUser(String username, String password) {
        Account account = accountDAO.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }
}