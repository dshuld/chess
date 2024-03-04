package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDao;
import dataAccess.interfaces.UserDao;
import dataAccess.sql.*;
import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import result.AuthResult;

import java.util.UUID;

public class RegisterService {
    private static RegisterService instance;

    private RegisterService() {}

    public static RegisterService getInstance() {
        if (instance == null) {
            instance = new RegisterService();
        }
        return instance;
    }

    public AuthResult register(RegisterRequest request) throws DataAccessException {
        UserDao userDao = SQLUserDao.getInstance();
        String username = request.username();
        String password = request.password();
        String email = request.email();
        UserData userData = new UserData(username, password, email);

        if(username == null || password == null || email == null) {
            return new AuthResult(null, null, "Error: bad request");
        }
        if(userDao.getUser(userData) != null) {
            return new AuthResult(null, null, "Error: already taken");
        }
        userDao.createUser(userData);

        AuthDao authDao = SQLAuthDao.getInstance();
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);

        authDao.createAuth(authData);

        return new AuthResult(username, authToken, null);
    }
}
