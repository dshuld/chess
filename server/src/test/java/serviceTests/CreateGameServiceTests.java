package serviceTests;

import dataAccess.*;
import model.*;
import org.junit.jupiter.api.*;
import request.CreateGameRequest;
import service.*;

import java.util.HashSet;

public class CreateGameServiceTests {
    @BeforeEach
    public void clear() {
        ClearService service = ClearService.getInstance();
        service.clear();
    }
    @Test
    public void testCreateGamePositive() {
        GameDao gameDao = MemoryGameDao.getInstance();
        AuthDao authDao = MemoryAuthDao.getInstance();
        String authToken = "auth";
        authDao.createAuth(new AuthData(authToken, "user"));

        Assertions.assertEquals(new HashSet<GameData>(), gameDao.listGames());

        CreateGameService createGameService = CreateGameService.getInstance();
        CreateGameRequest createGameRequest = new CreateGameRequest("name", authToken);
        createGameService.createGame(createGameRequest);

        Assertions.assertNotEquals(new HashSet<GameData>(), gameDao.listGames());
    }

    @Test
    public void testCreateGameNegative() {
        GameDao gameDao = MemoryGameDao.getInstance();
        AuthDao authDao = MemoryAuthDao.getInstance();
        String authToken = "auth";
        authDao.createAuth(new AuthData(authToken, "user"));

        Assertions.assertEquals(new HashSet<GameData>(), gameDao.listGames());

        CreateGameService createGameService = CreateGameService.getInstance();
        CreateGameRequest createGameRequest = new CreateGameRequest("name", "notAuth");
        createGameService.createGame(createGameRequest);

        Assertions.assertEquals(new HashSet<GameData>(), gameDao.listGames());
    }
}
