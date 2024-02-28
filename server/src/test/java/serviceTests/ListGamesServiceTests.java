package serviceTests;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.MemoryAuthDao;
import dataAccess.MemoryGameDao;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import request.ListGamesRequest;
import service.ClearService;
import service.ListGamesService;
import spark.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListGamesServiceTests {
    @BeforeAll
    public static void setup() {
        GameDao gameDao = MemoryGameDao.getInstance();
        gameDao.createGame(new GameData(null, null,null, null, null));
        AuthDao authDao = MemoryAuthDao.getInstance();
        authDao.createAuth(new AuthData("auth", "user"));
    }

    @Test
    public void testListGamesPositive() {
        ListGamesService listGamesService = ListGamesService.getInstance();
        ListGamesRequest listGamesRequest = new ListGamesRequest("auth");

        Set<GameData> set = new HashSet<>();
        set.add(new GameData(null, null,null, null, null));

        Assertions.assertEquals(set, listGamesService.listGames(listGamesRequest).games());
    }

    @Test
    public void testListGamesNegative() {
        ListGamesService listGamesService = ListGamesService.getInstance();
        ListGamesRequest listGamesRequest = new ListGamesRequest("notAuth");

        Assertions.assertNull(listGamesService.listGames(listGamesRequest).games());
    }
}
