package service;

import dataAccess.*;
import model.*;
import request.LoginRequest;
import result.AuthResult;

import java.util.UUID;

public class LoginService {
    private static LoginService instance;

    private LoginService() {}

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public AuthResult login(LoginRequest request) {
        UserDao userDao = MemoryUserDao.getInstance();
        String username = request.username();
        String password = request.password();
        UserData userData = new UserData(username, password, null);

        if(userDao.getUser(userData) == null || !userDao.getUser(userData).password().equals(password)) {
            return new AuthResult(null, null, "Error: unauthorized");
        }

        AuthDao authDao = MemoryAuthDao.getInstance();
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);

        authDao.createAuth(authData);

        return new AuthResult(username, authToken, null);
    }
}
