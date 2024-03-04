package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDao;
import dataAccess.interfaces.GameDao;
import dataAccess.interfaces.UserDao;
import dataAccess.sql.*;
import result.Result;

public class ClearService {
    private static ClearService instance;

    private ClearService() {}

    public static ClearService getInstance() {
        if (instance == null) {
            instance = new ClearService();
        }
        return instance;
    }

    public Result clear() throws DataAccessException {
        GameDao gameDao = SQLGameDao.getInstance();
        AuthDao authDao = SQLAuthDao.getInstance();
        UserDao userDao = SQLUserDao.getInstance();
        userDao.clear();
        gameDao.clear();
        authDao.clear();
        return new Result(null);
    }
}
