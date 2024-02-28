package serviceTests;

import dataAccess.*;
import model.*;
import org.junit.jupiter.api.*;
import request.JoinGameRequest;
import service.*;

public class JoinGameServiceTests {
    private GameDao gameDao = MemoryGameDao.getInstance();
    private int gameID = 1000;
    private GameData gameData = new GameData(gameID, null, null, "name", null);

    @BeforeEach
    public void setup() {
        ClearService clearService = ClearService.getInstance();
        clearService.clear();

        AuthDao authDao = MemoryAuthDao.getInstance();
        gameDao.createGame(gameData);
        authDao.createAuth(new AuthData("auth", "user"));
    }

    @Test
    public void testJoinGamePositive() {
        Assertions.assertNull(gameDao.getGame(gameData).whiteUsername());

        JoinGameService joinGameService = JoinGameService.getInstance();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameID, "auth");
        joinGameService.joinGame(joinGameRequest);

        Assertions.assertNotNull(gameDao.getGame(gameData).whiteUsername());
    }

    @Test
    public void testJoinGameNegative() {
        Assertions.assertNull(gameDao.getGame(gameData).whiteUsername());

        JoinGameService joinGameService = JoinGameService.getInstance();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameID, "notAuth");
        joinGameService.joinGame(joinGameRequest);

        Assertions.assertNull(gameDao.getGame(gameData).whiteUsername());
    }
}
