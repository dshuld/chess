package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.interfaces.GameDao;
import model.GameData;

import java.util.HashSet;
import java.util.Set;

public class SQLGameDao extends ConnectionManager implements GameDao {
    private static SQLGameDao instance;
    private Set<GameData> game = new HashSet<>();
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `game` varchar(2048),
              PRIMARY KEY (`gameID`),
              FOREIGN KEY (`whiteUsername`) REFERENCES `user`(`username`) ON UPDATE CASCADE ON DELETE CASCADE,
              FOREIGN KEY (`blackUsername`) REFERENCES `user`(`username`) ON UPDATE CASCADE ON DELETE CASCADE
            )
            """
    };

    private SQLGameDao() throws DataAccessException {
        configureDatabase(createStatements);
    }

    public static SQLGameDao getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new SQLGameDao();
        }
        return instance;
    }

    @Override
    public void createGame(GameData data) {

    }

    @Override
    public GameData getGame(GameData data) {
        return null;
    }

    @Override
    public Set<GameData> listGames() {
        return null;
    }

    @Override
    public void updateGame(GameData data) {

    }

    @Override
    public void clear() {

    }
}
