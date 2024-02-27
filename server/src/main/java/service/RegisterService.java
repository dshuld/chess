package service;

import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import result.RegisterResult;
import dataAccess.*;

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

    public RegisterResult register(RegisterRequest request) {
        UserDao userDao = MemoryUserDao.getInstance();
        String username = request.username();
        String password = request.password();
        String email = request.email();
        UserData userData = new UserData(username, password, email);

        if(username == null || password == null || email == null) {
            return new RegisterResult(null, null, "Error: bad request");
        }
        if(userDao.getUser(new UserData(request.username(), null, null)) != null) {
            return new RegisterResult(null, null, "Error: already taken");
        }
        userDao.createUser(userData);

        AuthDao authDao = MemoryAuthDao.getInstance();
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);

        authDao.createAuth(authData);

        return new RegisterResult(username, authToken, null);
    }
}
