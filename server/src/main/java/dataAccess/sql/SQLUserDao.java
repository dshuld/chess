package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.interfaces.UserDao;
import model.UserData;

import java.util.HashSet;
import java.util.Set;

public class SQLUserDao extends ConnectionManager implements UserDao {
    private static SQLUserDao instance;
    private Set<UserData> user = new HashSet<>();
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };

    private SQLUserDao() throws DataAccessException {
        configureDatabase(createStatements);
    }

    public static SQLUserDao getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new SQLUserDao();
        }
        return instance;
    }

    @Override
    public void createUser(UserData data) {

    }

    @Override
    public UserData getUser(UserData data) {
        return null;
    }

    @Override
    public void clear() {

    }
}
