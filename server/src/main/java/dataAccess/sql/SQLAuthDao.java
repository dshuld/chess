package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDao;
import dataAccess.interfaces.UserDao;
import model.AuthData;

import java.util.HashSet;
import java.util.Set;

public class SQLAuthDao extends ConnectionManager implements AuthDao {
    private static SQLAuthDao instance;
    private Set<AuthData> auth = new HashSet<>();
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `id` int NOT NULL AUTO_INCREMENT,
              `authtoken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`id`),
              FOREIGN KEY (`username`) REFERENCES `user`(`username`) ON UPDATE CASCADE ON DELETE CASCADE
            )
            """
    };

    private SQLAuthDao() throws DataAccessException {
        UserDao dependency = SQLUserDao.getInstance();
        configureDatabase(createStatements);
    }

    public static SQLAuthDao getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new SQLAuthDao();
        }
        return instance;
    }

    @Override
    public void createAuth(AuthData data) {

    }

    @Override
    public AuthData getUser(AuthData data) {
        return null;
    }

    @Override
    public void deleteAuth(AuthData data) {

    }

    @Override
    public void clear() {

    }
}
