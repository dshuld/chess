package serviceTests;

import dataAccess.interfaces.AuthDao;
import dataAccess.interfaces.GameDao;
import dataAccess.memory.MemoryAuthDao;
import dataAccess.memory.MemoryGameDao;
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
        try {
            clearService.clear();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        AuthDao authDao = MemoryAuthDao.getInstance();
        gameDao.createGame(gameData);
        authDao.createAuth(new AuthData("auth", "user"));
    }

    @Test
    public void testJoinGamePositive() {
        Assertions.assertNull(gameDao.getGame(gameData).whiteUsername());

        JoinGameService joinGameService = JoinGameService.getInstance();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameID, "auth");
        try {
            joinGameService.joinGame(joinGameRequest);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Assertions.assertNotNull(gameDao.getGame(gameData).whiteUsername());
    }

    @Test
    public void testJoinGameNegative() {
        Assertions.assertNull(gameDao.getGame(gameData).whiteUsername());

        JoinGameService joinGameService = JoinGameService.getInstance();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameID, "notAuth");
        try {
            joinGameService.joinGame(joinGameRequest);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Assertions.assertNull(gameDao.getGame(gameData).whiteUsername());
    }
}
