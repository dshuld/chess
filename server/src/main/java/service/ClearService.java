package service;

import result.Result;
import dataAccess.*;

public class ClearService {
    private static ClearService instance;

    private ClearService() {}

    public static ClearService getInstance() {
        if (instance == null) {
            instance = new ClearService();
        }
        return instance;
    }

    public Result clear() {
        UserDao userDao = MemoryUserDao.getInstance();
        GameDao gameDao = MemoryGameDao.getInstance();
        AuthDao authDao = MemoryAuthDao.getInstance();
        userDao.clear();
        gameDao.clear();
        authDao.clear();
        return new Result(null);
    }
}
