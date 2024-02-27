package service;

import dataAccess.*;
import model.AuthData;
import request.LogoutRequest;
import result.Result;

public class LogoutService {
    private static LogoutService instance;

    private LogoutService() {}

    public static LogoutService getInstance() {
        if (instance == null) {
            instance = new LogoutService();
        }
        return instance;
    }

    public Result logout(LogoutRequest request) {
        AuthDao authDao = MemoryAuthDao.getInstance();
        String authToken = request.authToken();
        AuthData authData = new AuthData(authToken, null);
        authData = authDao.getUser(authData);
        if(authData == null) {
            return new Result("Error: unauthorized");
        }

        authDao.deleteAuth(authData);

        return new Result(null);
    }
}
